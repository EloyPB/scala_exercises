def scanLeft[A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]): Unit =
  out(0) = a0
  var i = 0
  while (i < inp.length)
  {
    out(i+1) = f(out(i), inp(i))
    i += 1
  }

val a = Array(1, 2, 3)
val o = Array[Int](4)
scanLeft(a, 100, _+_, o)