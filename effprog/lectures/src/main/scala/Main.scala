@main def hello: Unit = 
  println("Hello world!")
  println(msg)

def msg = fansi.Color.Blue("I was compiled by Scala 3. :)")
