def gcd(a: Int, b: Int): Int =
  if (b == 0) a else gcd(b, a % b)

gcd(14, 21)


// not tail recursive
def factorial(n: Int): Int =
  if (n == 1) 1 else n * factorial(n - 1)

factorial(5)


// my tail recursive factorial
def trFactorial(n: Int): Int = {
  def loop(n: Int, f: Int): Int =
    if (n == 1) f else loop(n-1, f*(n-1))
  loop(n, n)
}

trFactorial(3)

// the official solution
def oFactorial(n: Int): Int = {
  def loop(acc: Int, n: Int): Int =
    if (n == 0) acc else loop(acc * n, n - 1)
  loop(1, n)
}

oFactorial(3)
