
def balance(chars: List[Char]): Boolean = {
  def goOn(chars: List[Char], opened: Int): Boolean = {
    if (opened < 0) false
    else if (chars.isEmpty) opened == 0
    else if (chars.head == '(') goOn(chars.tail, opened + 1)
    else if (chars.head == ')') goOn(chars.tail, opened - 1)
    else goOn(chars.tail, opened)
  }
  goOn(chars, 0)
}

val s = "())(".toList
balance(s)