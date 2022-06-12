sealed trait Tree[+T]

case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
case class Leaf[T](elem: T) extends Tree[T]
case object Empty extends Tree[Nothing]

