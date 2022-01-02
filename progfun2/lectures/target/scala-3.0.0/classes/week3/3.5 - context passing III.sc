case class Person(name: String)
case class Paper(title: String, authors: List[Person], body: String)

object ConfManagement:
  opaque type Viewers = Set[Person]
  def viewers(using vs: Viewers) = vs

  class Conference(ratings: (Paper, Int)*):
    private val realScore = ratings.toMap

    def papers: List[Paper] = ratings.map(_._1).toList

    def score(paper: Paper)(using Viewers): Int =
      if paper.authors.exists(viewers.contains) then -100
      else realScore(paper)

    def rankings(using Viewers): List[Paper] =
      papers.sortBy(score(_)).reverse

    def ask[T](p: Person, query: Viewers => T) = query(Set(p))

    def delegateTo[T](p: Person, query: Viewers => T)(using Viewers): T =
      query(viewers + p)

  end Conference
end ConfManagement

import ConfManagement.*


val Smith = Person("Smith")
val Peters = Person("Peters")
val Abel = Person("Abel")
val Black = Person("Black")
val Ed = Person("Ed")

val conf = Conference(
  Paper("How to grow beans", List(Smith, Peters), "...") -> 92,
  Paper("Organic gardening", List(Abel, Peters), "...") -> 83,
  Paper("Composting done right", List(Black, Smith), "...") -> 99,
  Paper("The secret life of snails", authors = List(Ed), "...") -> 77
)

// which authors have at least two papers with a score over 80?
def excellentAuthors(asking: Person): Set[Person] =
  def query(viewers: Viewers): Set[Person] =
    given Viewers = viewers
    val highlyRanked =
      conf.rankings.takeWhile(conf.score(_) > 80).toSet
    for
      p1 <- highlyRanked
      p2 <- highlyRanked
      author <- p1.authors
      if p1 != p2 && p2.authors.contains(author)
    yield author
  conf.ask(asking, query)

def testAs(person: Person) =
  excellentAuthors(asking = person).map(_.name).mkString(", ")

testAs(Black)
testAs(Smith)
testAs(Abel)
testAs(Ed)