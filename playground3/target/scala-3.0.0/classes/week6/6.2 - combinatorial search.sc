val xss = (1 until 7).map(i => (1 until i).map(j => (i, j)))
val xs = (1 until 7).map(i => (1 until i).map(j => (i, j))).flatten
val ys = (1 until 7).flatMap(i => (1 until i).map(j => (i, j)))

def isPrime(n: Int): Boolean = (2 until n).forall(n % _ != 0)

xs.filter((x, y) => isPrime(x + y))

def primeSums(n: Int) =
  for
    i <- 1 until n
    j <- 1 until i
    if isPrime(i + j)
  yield (i, j)

primeSums(7)

//def scalarProduct(xs: List[Double], ys: List[Double]): Double =
//  xs.zip(ys).map((x, y) => x * y).sum

def scalarProduct(xs: List[Double], ys: List[Double]): Double =
  (for xy <- xs.zip(ys) yield xy._1 * xy._2).max

scalarProduct(List(1.0, 1.0), List(3.0, 4.0))