enum PrimaryColor:
  case Red, Blue, Green


def problematicForColorBlind(color: PrimaryColor): Boolean =
  color match
    case PrimaryColor.Red => true
    case PrimaryColor.Blue => false
    case PrimaryColor.Green => true


PrimaryColor.values

PrimaryColor.valueOf("Green")


enum Planet(mass: Double, radius: Double):
  private final val G = 6.67300E-11
  def surfaceGravity = G * mass / (radius * radius)
  def surfaceWeight(otherMass: Double) =
    otherMass * surfaceGravity

  case Mercury extends Planet(3.303e+23, 2.4397e6)
  case Earth   extends Planet(5.976e+24, 6.37814e6)


val planet = Planet.Mercury
planet.surfaceWeight(65)