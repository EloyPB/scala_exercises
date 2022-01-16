package calculator

object Polynomial extends PolynomialInterface:
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] =
    Signal {
      b() * b() - 4 * a() * c()
    }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] =
    Signal {
      val currentDelta = delta()
      if currentDelta < 0 then Set()
      else if currentDelta == 0 then Set(-b() / (2 * a()))
      else Set((-b() + math.sqrt(currentDelta)) / (2 * a()), (-b() - math.sqrt(currentDelta)) / (2 * a()))
    }
