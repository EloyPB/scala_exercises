trait Generator[+T]:
  def generate(): T
  def map[S](f: T => S) = new Generator[S]:
    def generate() = f(Generator.this.generate())  // Generator.this refers to the "outer" object of class Generator
  def flatMap[S](f: T => Generator[S]) = new Generator[S]:
    def generate() = f(Generator.this.generate()).generate()

//extension [T, S](g: Generator[T])
//  def map(f: T => S) = new Generator[S]:
//    def generate() = f(g.generate())
//  def flatMap(f: T => Generator[S]) = new Generator[S]:
//    def generate() = f(g.generate()).generate()

val integers = new Generator[Int]:
  val rand = java.util.Random()
  def generate() = rand.nextInt()

integers.generate()

val booleans = new Generator[Boolean]:
  def generate() = integers.generate() > 0

booleans.generate()

val pairs = new Generator[(Int, Int)]:
  def generate() = (integers.generate(), integers.generate())

pairs.generate()

val booleans2 = for x <- integers yield x > 0
booleans2.generate()

def range(lo: Int, hi: Int): Generator[Int] =
  for x <- integers yield lo + x.abs % (hi - lo)

range(1, 5).generate()

def oneOf[T](xs: T*): Generator[T] =
  for idx <- range(0, xs.length) yield xs(idx)

val choice = oneOf("red", "green", "blue")
choice.generate()

def single[T](x: T): Generator[T] = new Generator[T] {
  override def generate() = x
}

def lists: Generator[List[Int]] =
  def emptyLists = single(Nil)

  def nonEmptyLists =
    for
      head <- integers
      tail <- lists
    yield head :: tail

  for
    kind <- range(0, 5)
    list <- if kind == 0 then emptyLists else nonEmptyLists
  yield list

lists.generate()
lists.generate()
lists.generate()
lists.generate()

enum Tree:
  case Inner(left: Tree, right: Tree)
  case Leaf(x: Int)

def trees: Generator[Tree] =
  def leafs = for x <- integers yield Tree.Leaf(x)
  def inners = for x <- trees; y <- trees yield Tree.Inner(x, y)
  for
    isLeaf <- booleans
    tree <- if isLeaf then leafs else inners
  yield tree

trees.generate()


def test[T](g: Generator[T], numTimes: Int = 100)(test: T => Boolean): Unit =
  for i <- 0 until numTimes do
    val value = g.generate()
    assert(test(value), s"test failed for $value")
  println(s"passed $numTimes tests")

