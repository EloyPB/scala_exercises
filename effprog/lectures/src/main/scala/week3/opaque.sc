object UserID:
  opaque type UserID = Long
  def parse(string: String): Option[UserID] = string.toLongOption
  def value(userID: UserID): Long = userID


import UserID.UserID

val user: UserID = UserID.parse("55").get
UserID.value(user)


object Logarithms:
  opaque type Logarithm = Double

  object Logarithm:
    def apply(d: Double): Logarithm = math.log(d)

  extension (x: Logarithm)
    def toDouble: Double = math.exp(x)
    def + (y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))
    def * (y: Logarithm): Logarithm = x + y


import Logarithms.*
val l2 = Logarithm(2.0)
val l3 = Logarithm(3.0)
println((l2 * l3).toDouble) // prints 6.0
println((l2 + l3).toDouble) // prints 4.999...

