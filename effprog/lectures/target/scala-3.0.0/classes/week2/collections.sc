import scala.collection.mutable

// For lists, prepending an element is really quickly, but accessing elements depends on the index
// For array buffers, accessing an element at any index takes the same time, but adding an element takes longer

val buffer = mutable.ArrayBuffer()

List.empty[Int]
mutable.ArrayBuffer.empty[Double]
Map.empty[String, Boolean]

mutable.ArrayBuffer("a", "b", "c")
Map("a" -> true, "b" -> false)
Map(("a", true), ("b", false))

val pair1: (String, Int) = "alice" -> 42
val pair2: (String, Int) = ("alice", 42)
val tuple: (Int, String, Boolean) = (42, "foo", true)

(10.0, "Hello") match
  case (number, greeting) => s"$greeting! The number is $number"

val (x, y) = (10, 20)

pair1(0)
pair1(1)

0 +: List(1, 2, 3)
0 :: List(1, 2, 3)
mutable.ArrayBuffer("a", "b") :+ "c"

Map("a" -> true) + ("b" -> false)
Map("a" -> true) ++ Map("b" -> false)


val data = List("alice" -> 42, "bob" -> 30, "werner" -> 77)

data.sortBy((_, age) => age)

val map = Map("a" -> 0, "b" -> 1, "c" -> 2)
map.get("b")
map("b")