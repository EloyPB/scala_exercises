import week3.Rational

// import everything in week3:
// import week3._

// import several:
// import week3.{Rational, Hello}

new Rational(1, 2)

def error(msg: String) = throw new Error(msg)

// error("test")

// Null is a subtype of the reference types, not of the value types
val x = null
val y: String = x

// val z: Int = null

if (true) 1 else false