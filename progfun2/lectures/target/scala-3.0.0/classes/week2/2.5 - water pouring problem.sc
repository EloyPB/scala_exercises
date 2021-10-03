type Glass = Int
type State = Vector[Int]

class Pouring(full: State):

  enum Move:
    case Empty(glass: Glass)
    case Fill(glass: Glass)
    case Pour(from: Glass, to: Glass)

    def apply(state: State): State = this match
      case Empty(glass) => state.updated(glass, 0)
      case Fill(glass) => state.updated(glass, full(glass))
      case Pour(from: Glass, to: Glass) =>
        val amount = state(from) min (full(to) - state(to))
        state.updated(from, state(from) - amount).updated(to, state(to) + amount)
  end Move

  val moves =
    val glasses: Range = 0 until full.length
    (for g <- glasses yield Move.Empty(g))
    ++ (for g <- glasses yield Move.Fill(g))
    ++ (for g1 <- glasses; g2 <- glasses if g1 != g2 yield Move.Pour(g1, g2))

  class Path(history: List[Move], val endState: State):
    def extend(move: Move) = Path(move :: history, move(endState))
    override def toString = s"${history.reverse.mkString(" ")} --> $endState"
  end Path

  val empty: State = full.map(x => 0)
  val start = Path(Nil, empty)

  def pathsFrom(paths: List[Path], explored: Set[State]): LazyList[List[Path]] =
    val frontier =
      for
        path <- paths
        move <- moves
        next = path.extend(move)
        if !explored.contains(next.endState)
      yield next
    paths #:: pathsFrom(frontier, explored ++ frontier.map(_.endState))

  def solutions(target: Int): LazyList[Path] =
    for
      paths <- pathsFrom(List(start), Set(empty))
      path <- paths
      if path.endState.contains(target)
    yield path

end Pouring

val problem = Pouring(Vector(4, 7))
problem.solutions(6).head



