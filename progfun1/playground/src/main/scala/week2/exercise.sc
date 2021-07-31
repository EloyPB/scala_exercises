type FunSet = Int => Boolean

def contains(s: FunSet, elem: Int): Boolean = s(elem)

def singletonSet(elem: Int): FunSet = x => x == elem

contains(singletonSet(4), 5)

def union(s: FunSet, t: FunSet): FunSet =
  x => contains(s, x) || contains(t, x)

contains(union(singletonSet(1), singletonSet(2)), 2)

def intersect(s: FunSet, t: FunSet): FunSet =
  x => contains(s, x) && contains(t, x)

val setA = union(singletonSet(1), singletonSet(2))
val setB = union(singletonSet(2), singletonSet(3))

contains(intersect(setA, setB), 2)

def diff(s: FunSet, t: FunSet): FunSet =
  x => contains(s, x) && !contains(t, x)

contains(diff(setA, setB), 1)

def filter(s: FunSet, p: Int => Boolean): FunSet =
  x => contains(s, x) && contains(p, x)

val setC = union(setA, setB)

contains(filter(setC, x => x % 2 == 0), 1)

def forall(s: FunSet, p: Int => Boolean): Boolean = {
  def iter(a: Int): Boolean = {
    if (a > 1000) true
    else if (contains(s, a) && !p(a)) false
    else iter(a + 1)
  }
  iter(-1000)
}

forall(setC, x => x < 10)

def exists(s: FunSet, p: Int => Boolean): Boolean = {
  def iter(a: Int): Boolean = {
    if (a > 1000) false
    else if (contains(s, a) && p(a)) true
    else iter(a + 1)
  }
  iter(-1000)
}

exists(setC, x => x > 10)

def map(s: FunSet, f: Int => Int): FunSet =
  x => exists(s, y => f(y) == x)

contains(map(setA, x => x*x), 4)

def map(s: FunSet, f: Int => Int): FunSet =
  x => !forall(s, y => f(y) != x)

contains(map(setA, x => x*x), 4)



