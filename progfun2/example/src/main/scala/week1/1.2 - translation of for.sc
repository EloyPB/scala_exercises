def isPrime(x: Int): Boolean = x match
  case 0 => false
  case 1 => true
  case 2 => true
  case _ => !(2 until x).exists(d => x % d == 0)

isPrime(3)
isPrime(4)

val n = 6

for
  i <- (1 until n)
  j <- (1 until i)
  if isPrime(i + j)
yield (i, j)

(1 until n).flatMap(i =>
  (1 until i)
    .withFilter(j => isPrime(i+j))
    .map(j => (i, j)))