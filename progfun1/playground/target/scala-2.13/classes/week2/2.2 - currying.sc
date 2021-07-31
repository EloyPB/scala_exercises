def sum(f: Int => Int): (Int, Int) => Int = {
  def sumF(a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a) + sumF(a + 1, b)
  }
  sumF
}

def sumInts = sum(x => x)
sumInts(2, 4)

def sumSquares = sum(x => x * x)
sumSquares(3, 4)

def square(a: Int): Int = a * a
sum(square)(3, 4)

def sum(f: Int => Int)(a: Int, b: Int): Int =
  if (a > b) 0 else f(a) + sum(f)(a + 1, b)

def sumSquares = sum(square)(_, _)
sumSquares(3, 4)

def product(f: Int => Int)(a: Int, b: Int): Int = {
  if (a > b) 1 else f(a) * product(f)(a + 1, b)
}
product(x => x)(1, 3)

def fact(n: Int): Int = product(x => x)(1, n)
fact(5)

def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
  if (a > b) zero
  else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))
}

def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a, b)
product(x => x)(1, 3)
