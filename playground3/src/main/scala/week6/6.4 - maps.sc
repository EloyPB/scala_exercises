import java.awt.Polygon

val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")
val countryOfCapital = capitalOfCountry.map((x, y) => (y, x))

capitalOfCountry("US")
capitalOfCountry.get("Andorra")  // doesn't throw exception if not in the map

def showCapital(country: String) = capitalOfCountry.get(country) match {
  case Some(capital) => capital
  case None => "missing data"
}

showCapital("US")
showCapital("Andorra")

capitalOfCountry + ("Spain" -> "Madrid")
capitalOfCountry ++ Map("Spain" -> "Madrid", "France" -> "Paris")

val fruits = List("apple", "pear", "orange", "pineapple")
fruits.sortWith(_.length < _.length)
fruits.sorted

fruits.groupBy(_.head)

val capitals = capitalOfCountry.withDefaultValue("<unknown>")
capitals("Andorra")


class Polynom(nonZeroTerms: Map[Int, Double]):

//  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = nonZeroTerms.withDefaultValue(0.0)

  def + (other: Polynom): Polynom =
    Polynom(terms ++ other.terms.map((exp, coeff) => (exp, terms(exp) + coeff)))

  def plus(other: Polynom): Polynom =
    Polynom(other.terms.foldLeft(terms)(addTerm))

  def addTerm(terms: Map[Int, Double], term: (Int, Double)) =
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))

  override def toString =
    val termStrings =
      for (exp, coeff) <- terms.toList.sorted.reverse
      yield
        val exponent = if exp == 0 then "" else s"x^$exp"
        val sign = if coeff < 0 then " - " else " + "
        val coeffAbs = coeff.abs
        s"$sign$coeffAbs$exponent"
    if terms.isEmpty then "0"
    else termStrings.mkString("")

val x = Polynom(Map(0 -> 2, 1 -> -3, 2 ->1))
val y = Polynom(Map(0 -> 3, 1 -> 2))
val z = Polynom(Map())

x + x + z

x.terms ++ y.terms  // if repeated element, takes the one from the right

x plus y
y plus x
