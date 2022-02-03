val list = List(1, 2, 3, 4)
list.map(x => x + 1)

// flatMap is map followed by flatten
list.flatMap(x => List())
list.flatMap(x => List(x, x*2))

val name = List("Eloy", "Parra")
name.flatMap(_.toLowerCase)

list.foldLeft(0)((sum, elm) => sum + elm)

// reverse a list
list.foldLeft(List.empty[Int])((accum, elm) => elm +: accum)

// true if the last element is even or the list is empty
list.foldLeft(true)((accum, elm) => elm % 2 == 0)


val emails = List("alice@gmail.com", "bob@outlook.es", "eloy@gmail.com")
val domain: String => String = email => email.dropWhile(_ != '@').drop(1)
val emailsByDomain = emails.groupBy(domain)

val names = List("eloy", "manuel", "miguel")
names.map(name => s"Hello, $name")