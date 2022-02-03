val abc = Map('a' -> 1) + ('b' -> 2) ++ Map('c' -> 3, 'd' -> 4)

abc.size
abc.isEmpty
abc.nonEmpty
abc.contains('b')

val data = List(1, 2, 3, 4)

// find the first element that matches a predicate
data.find(x => x % 2 == 0)
data.find(x => x == 5)

// or all of them
data.filter(x => x % 2 == 0)