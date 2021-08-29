package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:

  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      i <- arbitrary[Int]
      h <- frequency((1, const(empty)), (5, genHeap))
    } yield insert(i, h)
  )
  given Arbitrary[H] = Arbitrary(genHeap)


  property("gen1") = forAll { (h: H) =>
    val m = if isEmpty(h) then 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { (a: Int) =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min2") = forAll { (a: Int, b: Int) =>
    val h = insert(b, insert(a, empty))
    findMin(h) == (a min b)
  }

  property("empty") = forAll { (a: Int) =>
    val h = insert(a, empty)
    isEmpty(deleteMin(h))
  }

//  property("sorted") = forAll { (h: H) =>
//    def checkOrder(h: H, min: Int): Boolean =
//      if isEmpty(h) then true
//      else
//        val newMin = findMin(h)
//        if newMin < min then false
//        else checkOrder(deleteMin(h), newMin)
//    if isEmpty(h) then true
//    else checkOrder(h, findMin(h))
//  }

  property("sorted") = forAll { (h1: H) =>
    val h = insert(3, insert(2, insert(1, empty)))
    def sort(h: H, acc: List[Int]): List[Int] =
      if isEmpty(h) then acc
      else sort(deleteMin(h), acc ++ List(findMin(h)))
    val list = sort(h, List())
    list == list.sorted
  }

  property("min2heaps") = forAll { (h1: H, h2: H) =>
    if (isEmpty(h1) && isEmpty(h2)) then true
    else
      val min = findMin(meld(h1, h2))
      if isEmpty(h1) then min == findMin(h2)
      else if isEmpty(h2) then min == findMin(h1)
      else min == findMin(h1) || min == findMin(h2)
  }

  property("123") = forAll { (i: Int) =>
    val h = insert(3, insert(2, insert(1, empty)))
    findMin(deleteMin(deleteMin(h))) == 3
  }
