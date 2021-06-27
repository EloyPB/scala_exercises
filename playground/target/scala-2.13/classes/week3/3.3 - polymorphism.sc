import java.util.NoSuchElementException

// TYPE PARAMETRIZATION

trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty: Boolean = false
}

// Nothing is a subtype of all other types including T
class Nil[T] extends List[T] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.head")
}

def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])

singleton[Int](1)

// type can be inferred
singleton(1)

def myNth[T](n: Int, list: List[T]): T = {
  def loop(count: Int, list: List[T]): T = {
    if (count == n) list.head
    else if (list.isEmpty) throw new IndexOutOfBoundsException
    else loop(count + 1, list.tail)
  }
  if (n < 0) throw new IndexOutOfBoundsException
  else loop(0, list)
}

def nth[T](n: Int, list: List[T]): T = {
  if (list.isEmpty) throw new IndexOutOfBoundsException
  else if (n == 0) list.head
  else nth(n - 1, list.tail)
}

val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))

myNth(2, list)
nth(2, list)

//myNth(-1, list)
//nth(-1, list)