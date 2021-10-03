LazyList(1, 2, 3)
(1 to 1000).to(LazyList)

def lazyRange(lo: Int, hi: Int): LazyList[Int] =
  if lo >= hi then LazyList.empty
  else LazyList.cons(lo, lazyRange(lo + 1, hi))

val range = lazyRange(1, 10)

val x = 1
val xs = LazyList(2, 3)
x #:: xs == LazyList.cons(x, xs)