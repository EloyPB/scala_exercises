extension [T](xs: List[T])
  def splitAt(n: Int) = (xs.take(n), xs.drop(n))

val xs = List('a', 'b', 'c', 'd')
xs.splitAt(2)

val pair = ("answer", 42)
pair._1

def merge[T](xs: List[T], ys: List[T])(lt: (T, T) => Boolean): List[T] =
  (xs, ys) match
  case (Nil, ys) => ys
  case (xs, Nil) => xs
  case (x :: xs1, y :: ys1) =>
    if lt(x, y) then x :: merge(xs1, ys)(lt)
    else y :: merge(xs, ys1)(lt)

def mSort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
  val n = xs.length / 2
  if n == 0 then xs
  else
    val (fst, snd) = xs.splitAt(n)
    merge(mSort(fst)(lt), mSort(snd)(lt))(lt)
}

val ns = List(-5, 6, 3, 2, 7)
mSort(ns)((x, y) => x < y)