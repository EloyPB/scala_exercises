def max(xs: List[Int]): Int = if xs.isEmpty then throw new NoSuchElementException() else xs reduce(_ max _)

max(List())