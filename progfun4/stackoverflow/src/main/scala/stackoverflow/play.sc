val langs = List(
    "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
    "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

val x = List((1, 4), (1, 2), (2, 4), (3, 1), (1, 10), (2, 2))

val y = x.map(_._1).groupBy(l => l).map((l, ll) => (l, ll.size)).toList.sortBy(_._2).reverse.head
langs(y._1)
y._2 * 100.0 / x.length
x.map(_._2).toList.sorted.drop(x.size/2).head


val l = List(1, 2, 3, 4, 5)
l.drop(l.size/2).head