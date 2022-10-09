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

enum LayerName:
  case Temperatures, Deviations
  def id: String =
    this match
      case Temperatures => "temperatures"
      case Deviations => "deviations"
      
val d = LayerName.Temperatures
d.id

Range(1, 3).end