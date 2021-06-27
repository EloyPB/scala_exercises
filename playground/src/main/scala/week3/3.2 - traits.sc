trait Planar {
  def height: Int
  def width: Int
  def surface = height * width
}

// class Square extends Shape with Planar with Movable ...