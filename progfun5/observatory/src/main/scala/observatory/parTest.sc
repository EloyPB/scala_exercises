import scala.collection.parallel.CollectionConverters._
import observatory.Interaction
import observatory._

val pv = Vector(1,2,3,4,5,6,7,8,9).par
pv.map(_ * 2)

2.toDouble / (1 << 3)

(0 until 1).toList

def foo(): Int => Int =
  val a = 1
  def bar(v: Int): Int = 
    a + v
  bar  
  
val b = foo()
b(1)

val numbers = Array(1, 2, 3, 4)
numbers(0)

for x <- (0 until 5).par
  y <- (0 until 3)
do println((x, y))

-1.8.toInt
-1.8.ceil
-1.8.floor

-87.0 % 1.0

math.asin(math.Pi)

Interaction.tileLocation(Tile(0, 0, 1))