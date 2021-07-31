// lists, vectors and range are Seq(uences)

// vectors are stored in nested trees of 32 elements so they are faster to access and modify than lists

val nums = Vector(1, 2, 3)
nums(1)


// instead of x :: xs there is
0 +: nums
nums :+ 4


val r: Range = 1 until 5
val s: Range = 1 to 5
1 to 10 by 3
6 to 1 by -2


nums.exists(_ == 1)
nums.forall(_ < 3)
val zipped: Vector[(Int, Int)] = nums.zip(List(4, 5, 6))  // can zip different types of seq
val unzipped = zipped.unzip
unzipped(0)
nums.sum
nums.min

(1 to 2).flatMap(x => (1 to 3).map(y => (x, y)))

def scalarProduct1(xs: Vector[Int], ys: Vector[Int]): Int =
  xs.zip(ys).map((x, y) => x * y).sum

scalarProduct1(nums, nums)

def scalarProduct2(xs: Vector[Int], ys: Vector[Int]): Int =
  xs.zip(ys).map(_ * _).sum

def isPrime(n: Int): Boolean = (2 until n).forall(n % _ != 0)

isPrime(3)
isPrime(4)