import reductions.*

val coins = List(1, 3, 2)
coins.sorted.reverse

//ParallelCountChange.countChange(4, List(1, 2))

1.toFloat * 2 / 3

(1 until 3).toList

Array('(', 'b', ')', 'd').last


ParallelParenthesesBalancing.parBalance(Array('(', 'd', 'd', ')', '(', ')'), 2)

val inArray = Array(0f, 1, 8, 9)
val outArray = new Array[Float](4)
//LineOfSight.downsweepSequential(inArray, outArray, 0, 0, 4)
//outArray

val t = LineOfSight.upsweep(inArray, 0, 4, 2)
LineOfSight.downsweep(inArray, outArray, 0, t)
outArray