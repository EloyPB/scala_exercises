def countChange(money: Int, coins: List[Int]): Int =
  def countSorted(money: Int, coins: List[Int]): Int =
    if coins.isEmpty || money < 0 then 0
    else if money == 0 then 1
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  countSorted(money, coins.sorted)

countChange(10, List(4, 2, 1))
