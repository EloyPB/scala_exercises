def from (n: Int): LazyList[Int] = n #:: from(n + 1)

val nats = from(0)
nats.take(10).toList

// the Sieve of Eratosthenes
def sieve(s: LazyList[Int]): LazyList[Int] =
  s.head #:: sieve(s.tail.filter(_ % s.head != 0))

val primes = sieve(from(2))
primes.take(100).toList


def sqrtSeq(x:Double): LazyList[Double] =
  def improve(guess: Double) = (guess + x / guess) / 2
  lazy val guesses: LazyList[Double] = 1 #:: guesses.map(improve)
  guesses

def isGoodEnough(guess: Double, x: Double) =
  math.abs((guess * guess - x) / x) < 0.0001

sqrtSeq(2).filter(isGoodEnough(_, 2)).head