package observatory

trait VisualizationTest extends MilestoneSuite:
  private val milestoneTest = namedMilestoneTest("raw data display", 2) _

  // Implement tests for the methods of the `Visualization` object

  test("Great-circle distance for antipodes is correct"){
    import scala.math.Pi
    val l1 = Location(10, 20)
    val l2 = Location(-10, -160)
    assertEquals(Visualization.greatCircleDistance(l1, l2), 6371*Pi)
  }

  test("Great-circle distance for the same location is 0") {
    import scala.math.Pi
    val l1 = Location(10, 20)
    assertEquals(Visualization.greatCircleDistance(l1, l1), 0.0)
  }