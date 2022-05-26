import java.lang.Character.isLowerCase
import scala.collection.parallel.CollectionConverters.*

def initializeArray(xs: Array[Int])(v: Int): Unit = {
  for (i <- xs.indices.par) {
    xs(i) = v
  }
}

val a = new Array[Int](10)
initializeArray(a)(1)
a


(1 to 1000).par
  .filter(n => n % 3 == 0)
  .filter(n => n.toString == (n.toString).reverse)

// aggregates foldLefts using folds. It takes the function that goes to the
// foldLeft and to fold
Array('e', 'P', 'f', 'L').par.aggregate(0)(
  (count, c) => if isLowerCase(c) then count + 1 else count,
  _ + _
)
