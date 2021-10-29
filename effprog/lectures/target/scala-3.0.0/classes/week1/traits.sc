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
