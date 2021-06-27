List(1, 2) ++ List(3, 4)
List(1, 2) ::: List(3, 4)

val xs = List('a', 'b', 'c', 'd')

def removeAt[T](n: Int, xs: List[T]): List[T] = xs match {
  case Nil => Nil
  case y :: ys =>
    if (n == 0) ys
    else y :: removeAt(n - 1, ys)
}

removeAt(2, xs)

def flatten(xs: Any): List[Any] = xs match {
  case Nil => Nil
  case y :: ys => flatten(y) ++ flatten(ys)
  case _ => xs :: Nil
}

val ys = List(List(1, 1), 2, List(3, List(5, 8)))

flatten(ys)