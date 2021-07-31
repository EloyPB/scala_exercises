val xs = List(1, 2, 3, 4, 5, 6)

val (even, odd) = xs.partition(p => p % 2 == 0)
val small = xs.takeWhile(p => p < 4)
val big = xs.dropWhile(p => p < 4)
val (s, b) = xs.span(p => p < 4)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (same, other) = xs.span(p => p == x)
    same :: pack(other)
}

pack(List('a', 'a', 'a', 'b', 'c', 'c', 'a'))

def encode[T](xs: List[T]): List[(T, Int)] =
  pack(xs).map(x => (x.head, x.length))

encode(List('a', 'a', 'a', 'b', 'c', 'c', 'a'))