val a: Array[Int] = Array(1, 2, 3, 4)
a.slice(2, 4)

def sumSegment(a: Array[Int], p: Double, s: Int, t: Int) =
  a.slice(s, t).foldLeft(0)((acc, e) => acc + scala.math.pow(e.abs, p).floor.toInt)

sumSegment(a, p = 2, s = 1, t = 3)