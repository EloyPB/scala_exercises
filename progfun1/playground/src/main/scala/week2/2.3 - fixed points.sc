import scala.math.abs

val tolerance = 0.0001
def isCloseEnough(x: Double, y: Double) =
  abs((x - y) / x) < tolerance

def fixedPoint(f: Double => Double)(firstGuess: Double) = {
  def iterate(guess: Double): Double = {
    // println("guess = " + guess)
    val next = f(guess)
    if (isCloseEnough(guess, next)) next
    else iterate(next)
  }
  iterate(firstGuess)
}
fixedPoint(x => 1+ x/2)(1)

// y is the square root of x if y * y = x, or if y = x/y
// square root is the fixed point of y = x / y
def sqrt(x: Double) = fixedPoint(y => x/y)(1)
// oscillates, so we introduce average damping
def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1)
sqrt(2)

def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

def sqrt(x: Double) = fixedPoint(averageDamp(y => x/y))(1)
sqrt(2)