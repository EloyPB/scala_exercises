class Rational(x: Int, y: Int) {
  def numer = x
  def denom = y
}

val x = new Rational(1, 2)
x.numer

def addRational(r: Rational, s: Rational): Rational =
  new Rational(r.numer*s.denom + s.numer*r.denom, r.denom*s.denom)

def makeString(r: Rational) = r.numer + "/" + r.denom

makeString(addRational(new Rational(1, 2), new Rational(2, 3)))

class Rational(x: Int, y: Int) {
  def numer = x
  def denom = y

  def add(that: Rational) =
    new Rational(
      numer*that.denom + that.numer*denom,
      denom*that.denom)

  def neg: Rational = new Rational(-numer, denom)

  def sub(that: Rational) = add(that.neg)

  override def toString = numer + "/" + denom
}

val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

x.sub(y).sub(z)