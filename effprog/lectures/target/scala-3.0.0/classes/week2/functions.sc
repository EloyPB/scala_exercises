// These are functions, can be passed and returned

val increment: Int => Int =
  (x: Int) =>
    val result = x + 1
    result

val increment2: Int => Int = x => x + 1

val add: (Int, Int) => Int =
  (x: Int, y: Int) => x + y

val add2 = (x: Int, y: Int) => x + y

add(1, increment(2))
increment2(1)

// This is rewritten to
increment2.apply(1)


// This is a method. The compiler can transform methods into functions.

def increment3(a: Int): Int = a + 1
increment3(1)