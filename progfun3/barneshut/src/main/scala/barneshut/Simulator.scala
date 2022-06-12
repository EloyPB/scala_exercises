package barneshut

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.event.*
import scala.{collection => coll}
import scala.collection.parallel.{TaskSupport, Combiner}
import scala.collection.parallel.mutable.ParHashSet
import scala.collection.parallel.CollectionConverters.*

class Simulator(val taskSupport: TaskSupport, val timeStats: TimeStatistics):

  def updateBoundaries(boundaries: Boundaries, body: Body): Boundaries =
    val newB = new Boundaries()
    if body.x < boundaries.minX then newB.minX = body.x else newB.minX = boundaries.minX
    if body.x > boundaries.maxX then newB.maxX = body.x else newB.maxX = boundaries.maxX
    if body.y < boundaries.minY then newB.minY = body.y else newB.minY = boundaries.minY
    if body.y > boundaries.maxY then newB.maxY = body.y else newB.maxY = boundaries.maxY
    newB

  def mergeBoundaries(a: Boundaries, b: Boundaries): Boundaries =
    val boundaries = new Boundaries()
    boundaries.minX = a.minX.min(b.minX)
    boundaries.maxX = a.maxX.max(b.maxX)
    boundaries.minY = a.minY.min(b.minY)
    boundaries.maxY = a.maxY.max(b.maxY)
    boundaries

  def computeBoundaries(bodies: coll.Seq[Body]): Boundaries = timeStats.timed("boundaries") {
    val parBodies = bodies.par
    parBodies.tasksupport = taskSupport
    parBodies.aggregate(Boundaries())(updateBoundaries, mergeBoundaries)
  }

  def computeSectorMatrix(bodies: coll.Seq[Body], boundaries: Boundaries): SectorMatrix = timeStats.timed("matrix") {
    val parBodies = bodies.par
    parBodies.tasksupport = taskSupport
    parBodies.aggregate(SectorMatrix(boundaries, SECTOR_PRECISION))((m, b) => m+=b, (m1, m2) => m1.combine(m2))
  }

  def computeQuad(sectorMatrix: SectorMatrix): Quad = timeStats.timed("quad") {
    sectorMatrix.toQuad(taskSupport.parallelismLevel)
  }

  def updateBodies(bodies: coll.Seq[Body], quad: Quad): coll.Seq[Body] = timeStats.timed("update") {
    def updateBody(bodies: coll.Seq[Body], body: Body) = bodies :+ body.updated(quad)
    val parBodies = bodies.par
    parBodies.tasksupport = taskSupport
    parBodies.aggregate(Seq())(updateBody, (s1, s2) => s1 ++ s2)
  }

  def eliminateOutliers(bodies: coll.Seq[Body], sectorMatrix: SectorMatrix, quad: Quad): coll.Seq[Body] = timeStats.timed("eliminate") {
    def isOutlier(b: Body): Boolean =
      val dx = quad.massX - b.x
      val dy = quad.massY - b.y
      val d = math.sqrt(dx * dx + dy * dy)
      // object is far away from the center of the mass
      if d > eliminationThreshold * sectorMatrix.boundaries.size then
        val nx = dx / d
        val ny = dy / d
        val relativeSpeed = b.xspeed * nx + b.yspeed * ny
        // object is moving away from the center of the mass
        if relativeSpeed < 0 then
          val escapeSpeed = math.sqrt(2 * gee * quad.mass / d)
          // object has the espace velocity
          -relativeSpeed > 2 * escapeSpeed
        else false
      else false

    def outliersInSector(x: Int, y: Int): Combiner[Body, ParHashSet[Body]] =
      val combiner = ParHashSet.newCombiner[Body]
      combiner ++= sectorMatrix(x, y).filter(isOutlier)
      combiner

    val sectorPrecision = sectorMatrix.sectorPrecision
    val horizontalBorder = for x <- 0 until sectorPrecision; y <- Seq(0, sectorPrecision - 1) yield (x, y)
    val verticalBorder = for y <- 1 until sectorPrecision - 1; x <- Seq(0, sectorPrecision - 1) yield (x, y)
    val borderSectors = horizontalBorder ++ verticalBorder

    // compute the set of outliers
    val parBorderSectors = borderSectors.par
    parBorderSectors.tasksupport = taskSupport
    val outliers = parBorderSectors.map({ case (x, y) => outliersInSector(x, y) }).reduce(_ combine _).result()

    // filter the bodies that are outliers
    val parBodies = bodies.par
    parBodies.filter(!outliers(_)).seq
  }

  def step(bodies: coll.Seq[Body]): (coll.Seq[Body], Quad) =
    // 1. compute boundaries
    val boundaries = computeBoundaries(bodies)

    // 2. compute sector matrix
    val sectorMatrix = computeSectorMatrix(bodies, boundaries)

    // 3. compute quad tree
    val quad = computeQuad(sectorMatrix)

    // 4. eliminate outliers
    val filteredBodies = eliminateOutliers(bodies, sectorMatrix, quad)

    // 5. update body velocities and positions
    val newBodies = updateBodies(filteredBodies, quad)

    (newBodies, quad)

