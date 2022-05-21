def scanLeft[A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]): Unit =
  out(0) = a0
  var i = 0
  while (i < inp.length)
  {
    out(i+1) = f(out(i), inp(i))
    i += 1
  }

val a = Array(1, 2, 3)
val o = new Array[Int](4)
scanLeft(a, 100, _+_, o)
o

sealed abstract class Tree[A]
case class Leaf[A](a: A) extends Tree[A]
case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A]

sealed abstract class TreeRes[A] {val res: A}
case class LeafRes[A](override val res: A) extends TreeRes[A]
case class NodeRes[A](l: TreeRes[A], override val res: A, r: TreeRes[A]) extends TreeRes[A]

val t = Node(Node(Leaf(1), Leaf(2)), Node(Leaf(3), Leaf(4)))

def reduceRes[A](t: Tree[A], f: (A, A) => A): TreeRes[A] = t match {
  case Leaf(a) => LeafRes(a)
  case Node(l, r) =>
    val treeL = reduceRes(l, f)
    val treeR = reduceRes(r, f)
    NodeRes(treeL, f(treeL.res, treeR.res), treeR)
}

reduceRes(t, _+_)
