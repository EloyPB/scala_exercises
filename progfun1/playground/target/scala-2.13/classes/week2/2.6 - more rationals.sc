class Rational(x: Int, y: Int) {
  require(y != 0, "denominator must be different from 0!")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)
  val numer = x / g
  val denom = y / g

  def less(that: Rational) = numer * that.denom < that.numer * denom
  // or optionally: this.numer...

  def max(that: Rational) = if (less(that)) that else this
  // or optionally: this.less(that)...

  def add(that: Rational) =
    new Rational(numer*that.denom + that.numer*denom, denom*that.denom)

  def neg: Rational = new Rational(-numer, denom)

  def sub(that: Rational) = add(that.neg)

  override def toString = numer + "/" + denom
}

val x = new Rational(1, 3)
val y = new Rational(5, 7)

y.add(y)
x.less(y)
x.max(y)

// also possible:
y add y

