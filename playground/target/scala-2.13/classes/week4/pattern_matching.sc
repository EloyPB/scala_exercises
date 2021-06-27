trait Expr
case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Var(x: String) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr

def eval(e: Expr): Int = e match {
  case Number(n) => n
  case Sum(e1, e2) => eval(e1) + eval(e2)
}

eval(Sum(Number(1), Number(2)))

def show(e: Expr): String = {
  def showP(e: Expr): String = e match {
    case e: Sum => s"(${show(e)})"
    case _ => show(e)
  }
  e match {
    case Var(x) => x
    case Number(n) => n.toString
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => showP(e1) + " * " + showP(e2)
  }
}

show(Sum(Number(1), Sum(Number(2), Number(3))))
show(Sum(Prod(Number(2), Var("x")), Var("y")))
show(Prod(Sum(Number(1), Number(2)), Sum(Number(1), Var("x"))))
show(Sum(Prod(Var("x"), Var("y")), Number(3)))