1 << 8
(-1 to 1).toList
Array(0,0,0)
(1 until 3).toList
val points = (1 until 6 by 2).toList
val splits = points.zip(points.tail)

for (s <- splits) {println(s)}