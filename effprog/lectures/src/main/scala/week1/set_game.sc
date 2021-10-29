enum Shape:
  case Diamond, Squiggle, Oval

enum Num:
  case One, Two, Three

enum Color:
  case Red, Green, Purple

enum Shading:
  case Open, Stripped, Solid

case class Card(shape: Shape, number: Num, color: Color, shading: Shading)

val deck = List(
  Card(Shape.Diamond, Num.One, Color.Purple, Shading.Stripped),
  Card(Shape.Squiggle, Num.Two, Color.Red, Shading.Open),
  Card(Shape.Oval, Num.Three, Color.Green, Shading.Solid)
)

def checkProperty(deck: List[Card], property: Card => Any): Boolean =
  val numDifferent = deck.map(property).toSet.size
  numDifferent == 1 || numDifferent == deck.size


def isValidSet(deck: List[Card]) =
    checkProperty(deck, c => c.shape)  &&
    checkProperty(deck, c => c.number) &&
    checkProperty(deck, c => c.color)  &&
    checkProperty(deck, c => c.shading)

isValidSet(deck)
