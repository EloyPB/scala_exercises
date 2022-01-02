trait HasLegs:
  def numLegs: Int
  def walk(): Unit
  def stop() = println("Stopped walking")

trait HasTail:
  def tailColor: String
  def wagTail() = println("Tail is wagging")
  def stopTail() = println("Tail is stopped")

class IrishSetter(name: String) extends HasLegs, HasTail:
  val numLegs = 4
  val tailColor = "Red"
  def walk() = println("I’m walking")
  override def toString = s"$name is a Dog"

val d = IrishSetter("Big Red")
d.wagTail()


// traits can have parameters:
trait Pet(name: String):
  def greeting: String
  def age: Int
  override def toString = s"My name is $name, I say $greeting, and I’m $age"

class Dog(name: String, var age: Int) extends Pet(name):
  val greeting = "Woof"

val f = Dog("Fido", 1)


sealed trait Shape
case class Rectangle(width: Int, height: Int) extends Shape
case class Circle(radius: Int) extends Shape

def shapeArea(shape: Shape): Double =
  shape match
    case Rectangle(width, height) => width*height
    case Circle(radius) => radius * radius * 3.14

shapeArea(Circle(1))

val someShape: Shape = Circle(2)

someShape match
  case shape: Circle => s"This is a circle with radius ${shape.radius}"
  case _ => "This is not a circle"
