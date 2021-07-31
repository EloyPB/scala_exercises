// summation operator

def sum(f: Int => Int, a: Int, b: Int): Int = {
  if (a > b) 0
  else f(a) + sum(f, a + 1, b)
}

def id(x: Int): Int = x
def cube(x: Int): Int = x * x * x

def sumInts(a: Int, b: Int) = sum(id, a, b)
sumInts(1, 4)

def sumCubes(a: Int, b: Int) = sum(cube, a, b)
sumCubes(1, 3)

def fact(x: Int): Int = if (x == 0) 1 else x * fact(x - 1)
fact(4)


// with anonymous functions
def sumInts(a: Int, b: Int) = sum(x => x, a, b)
sumInts(1, 4)

def sumCubes(a: Int, b: Int) = sum(x => x*x*x, a, b)
sumCubes(1, 3)

// tail recursive version
def sum(f: Int => Int, a: Int, b: Int): Int = {
  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop(a + 1, acc+f(a))
  }
  loop(a, 0)
}
def sumInts(a: Int, b: Int) = sum(x => x, a, b)
sumInts(1, 4)