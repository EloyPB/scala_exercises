def sum1(xs: List[Int]) = (0 :: xs).reduceLeft((x, y) => x + y)
sum1(List(1, 2, 3))

// shorter way
def sum2(xs: List[Int]) = (0 :: xs).reduceLeft(_ + _)
sum2(List(1, 2, 3))

def sum3(xs: List[Int]) = xs.foldLeft(0)(_ + _)
sum3(List(1, 2, 3))

def concat[T](xs: List[T], ys: List[T]): List[T] =
  xs.foldRight(ys)(_ :: _)

concat(List(1,2,3), List(4,5,6))

def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  xs.foldRight(List[U]())((y, ys) => f(y) :: ys)

mapFun(List(1,2,3), x => x*x)

def lengthFun[T](xs: List[T]): Int =
  xs.foldRight(0)((y, n) => n + 1)

lengthFun(List(1,2,3))