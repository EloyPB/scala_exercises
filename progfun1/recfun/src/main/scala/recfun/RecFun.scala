package recfun

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if (r == 0 || c == 0 || c == r) 1
    else pascal(c-1, r-1) + pascal(c, r-1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean =
    def goOn(chars: List[Char], opened: Int): Boolean = {
      if (opened < 0) false
      else if (chars.isEmpty) opened == 0
      else if (chars.head == '(') goOn(chars.tail, opened + 1)
      else if (chars.head == ')') goOn(chars.tail, opened - 1)
      else goOn(chars.tail, opened)
    }
    goOn(chars, 0)

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int =
    def countSorted(money: Int, coins: List[Int]): Int =
      if coins.isEmpty || money < 0 then 0
      else if money == 0 then 1
      else countChange(money - coins.head, coins) + countChange(money, coins.tail)
    countSorted(money, coins.sorted.reverse)
