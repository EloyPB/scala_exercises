def fruits: List[String] = "apples" :: ("oranges" :: ("pears" :: Nil))
fruits.toString

def nums = 1 :: 2 :: 3 :: 4 :: Nil
nums.toString

fruits.head

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => if (x < y) x :: xs else y :: insert(x, ys)
}

def iSort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case y :: ys => insert(y, iSort(ys))
}

// List patterns for pattern matching:
// 1 :: 2 :: xs    Lists that start with 1 and then 2
// x :: Nil        Lists of length 1
// List(x)         Same as x :: Nil
// List()          Empty list
// List(2 :: xs)   A list that contains as only element another list starting with 2
