case class Contact(
                  name: String,
                  maybeEmail: Option[String],
                  phoneNumbers: List[String]
                  )

val alice = Contact("Alice", Some("alice@gmail.com"), List())
val bob = Contact("Bob", None, List("123"))

def hasGmailEmail(contact: Contact): Boolean =
  contact.maybeEmail match {
    case Some(email) => email.endsWith("@gmail.com")
    case None => false
  }

hasGmailEmail(alice)


// options have map and flatmap
def emailLength(contact: Contact): Int =
  contact.maybeEmail.map(email => email.size).getOrElse(0)

bob.maybeEmail.map(email => email.size)
emailLength(bob)

val maybeAliceAndBobEmails: Option[(String, String)] =
  alice.maybeEmail.zip(bob.maybeEmail)


val emptyList = List.empty[Int]
emptyList.headOption