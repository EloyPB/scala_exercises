
def sqrt(x: Double) = {
  def abs(y: Double) = if (y < 0) -y else y

  def isGoodEnough(guess: Double) =
    abs(guess * guess - x)/x < 0.001

  def improve(guess: Double) =
    (guess + x / guess) / 2

  def sqrtIter(guess: Double): Double =
    if (isGoodEnough(guess)) guess
    else sqrtIter(improve(guess))

  sqrtIter(1.0)
}

sqrt(2)
sqrt(1e-20)
sqrt(1e50)