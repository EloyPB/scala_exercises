case class Rational(numer: Int, denom: Int)

//trait Ordering[A] with
//  def compare(x: A, y: A): Boolean

given RationalOrdering: Ordering[Rational] with
  def compare(x: Rational, y: Rational) =
    val xn = x.numer * y.denom
    val yn = y.numer * x.denom
    if xn < yn then -1 else if xn > yn then 1 else 0

def sort[A](xs: List[A])(implicit ord: Ordering[A]): List[A] = ???


