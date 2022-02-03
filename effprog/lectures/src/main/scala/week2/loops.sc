def _factorial(n: Int, acc: Int): Int =
  if n == 0 then acc
  else _factorial(n - 1, acc * n)

def factorial(n: Int): Int = _factorial(n, 1)
factorial(4)

def factorial_2(n: Int): Int =
  if n == 0 then 1
  else n * factorial(n - 1)

(1 to 4).foldLeft(1)((n, acc) => n * acc)

def factorial_imp(n: Int): Int =
  var acc = 1
  var i = 1
  while i < n do
    i = i + 1
    acc = acc *i
  acc

factorial_imp(4)

def reverse[T](xs: List[T]): List[T] = xs.foldLeft(Nil)(_ :: _)