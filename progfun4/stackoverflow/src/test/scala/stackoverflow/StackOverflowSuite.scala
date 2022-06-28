package stackoverflow

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext.*
import org.apache.spark.rdd.RDD
import java.io.File
import scala.io.{ Codec, Source }
import scala.util.Properties.isWin

object StackOverflowSuite:
  val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("StackOverflow")
  val sc: SparkContext = new SparkContext(conf)

class StackOverflowSuite extends munit.FunSuite:
  import StackOverflowSuite.*


  lazy val testObject = new StackOverflow {
    override val langs =
      List(
        "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
        "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")
    override def langSpread = 50000
    override def kmeansKernels = 45
    override def kmeansEta: Double = 20.0D
    override def kmeansMaxIterations = 120
  }

  test("testObject can be instantiated") {
    val instantiatable = try {
      testObject
      true
    } catch {
      case _: Throwable => false
    }
    assert(instantiatable, "Can't instantiate a StackOverflow object")
  }

  test("compute the maximum score for each question") {
    val q1: Posting = Posting(postingType = 1, id = 1, acceptedAnswer = Some(3), parentId = None, score = 10, tags = Some("Scala"))
    val a1a: Posting = Posting(postingType = 2, id = 2, acceptedAnswer = None, parentId = Some(1), score = 3, tags = Some("Scala"))
    val a1b: Posting = Posting(postingType = 2, id = 3, acceptedAnswer = None, parentId = Some(1), score = 6, tags = Some("Scala"))
    val q2: Posting = Posting(postingType = 1, id = 4, acceptedAnswer = Some(5), parentId = None, score = 2, tags = Some("Scala"))
    val a2a: Posting = Posting(postingType = 2, id = 5, acceptedAnswer = None, parentId = Some(4), score = 5, tags = Some("Scala"))
    val postings = sc.parallelize(Seq(q1, a1a, a1b, q2, a2a))
    val grouped = StackOverflow.groupedPostings(postings)
    val scored = StackOverflow.scoredPostings(grouped).collect()
    assert(scored(0) == ((q2, 5)) | scored(1) == ((q2, 5)))
  }

  test("correct number of vectors") {
    
  }


  import scala.concurrent.duration.given
  override val munitTimeout = 300.seconds
