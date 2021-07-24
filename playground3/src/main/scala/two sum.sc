import scala.collection.Searching._

val random = util.Random(System.currentTimeMillis)
val nums = Array.fill(10)(random.nextInt(100))
val target = nums(3) + nums(6)


def twoSum(nums: Array[Int], target: Int): Array[Int] = {
  val (indices, numbers) = (0 to nums.size).zip(nums).sortBy({case (i, n) => n}).unzip

  def findIndices(index: Int, num: Int, rest: IndexedSeq[Int], target: Int): Array[Int] = {
    rest.search(target - num) match {
      case Found(indexRight) => Array(indices(index), indices(indexRight + index + 1))
      case _ => findIndices(index + 1, rest(0), rest.tail, target)
    }
  }

  findIndices(0, numbers(0), numbers.tail, target)
}
val numbers = Array(3, 3)

twoSum(nums, target)
