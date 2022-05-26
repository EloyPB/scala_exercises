import kmeans.{KMeans, Point}
import scala.collection.parallel.ParSeq
import java.util.concurrent.*
import scala.collection.parallel.CollectionConverters.*


val kMeans = KMeans()
//val points: ParSeq[Point] = IndexedSeq(Point(1, 0, 1), Point(0, 0, 0)).par
//val means: ParSeq[Point] = IndexedSeq(Point(2, 2, 2), Point(0, 0, 0), Point(5, 5, 5)).par
//val classified = kMeans.classify(points, means)
//kMeans.update(classified, means)

val p: ParSeq[Point] = IndexedSeq(Point(0.0, 0.0, 1.0), Point(0.0, 0.0, -1.0), Point(0.0, 1.0, 0.0), Point(0.0, 10.0, 0.0)).par
val m: ParSeq[Point] = IndexedSeq(Point(0, 0, 0), Point(0, 5.5, 0)).par

//val c = kMeans.classify(p, m)
//val nm = kMeans.update(c, m)
//kMeans.converged(12.25, m, nm)
val fm = kMeans.kMeans(p, m, 0.00001)
