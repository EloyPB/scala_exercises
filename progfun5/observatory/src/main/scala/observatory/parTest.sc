import scala.collection.parallel.CollectionConverters._

val pv = Vector(1,2,3,4,5,6,7,8,9).par
pv.map(_ * 2)

import scala.math.sin
sin(3.14)