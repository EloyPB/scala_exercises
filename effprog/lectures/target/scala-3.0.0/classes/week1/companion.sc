// Companion objects are used for methods and values that are not specific to instances of the companion class.
// For instance, in the following example the class Circle has a member named area which is specific to each instance,
// and its companion object has a method named calculateArea that’s (a) not specific to an instance,
// and (b) is available to every instance:


import scala.math.*

case class Circle(radius: Double):
  def area: Double = Circle.calculateArea(radius)

object Circle:
  private def calculateArea(radius: Double): Double = Pi * pow(radius, 2.0)

val circle1 = Circle(5.0)
circle1.area