enum PrimaryColor:
  case Red, Blue, Green


def problematicForColorBlind(color: PrimaryColor): Boolean =
  color match
    case PrimaryColor.Red => true
    case PrimaryColor.Blue => false
    case PrimaryColor.Green => true


PrimaryColor.values

PrimaryColor.valueOf("Green")