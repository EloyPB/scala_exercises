val l = (1 to 3).toList
l.map(_*2)

def myMap[A, B](f: B => A, list: List[B]): List[A] =
  list match
    case x::xs => f(x) :: myMap(f, xs)
    case _ => Nil

def mapTR[A, B](f: B => A, list: List[B]): List[A] =
  def loop(rem: List[B], acc: List[A]): List[A] =
    rem match
      case x::xs => loop(xs, f(x) :: acc)
      case _ => acc.reverse
  loop(list, Nil)

myMap((v: Int) => v*2, l)
mapTR((v: Int) => v*2, l)