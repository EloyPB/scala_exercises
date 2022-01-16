val increment: Int => Int =
  x =>
    val result = x + 1
    result

val add =
  (x: Int, y: Int) => x + y

add(1, increment(2))