case class Contact(
                    name: String,
                    email: String,
                    phoneNumbers: List[String]
                  )
case class AddressBook(contacts: List[Contact])

val alice = Contact("Alice", "alice@sca.la", List())
val bob = Contact("Bob", "bob@sca.la", List("+41787829420"))

val addressBook = AddressBook(List(alice, bob))
val numberOfContacts = addressBook.contacts.size
val aliceThere = addressBook.contacts.contains(alice)
val contactNames = addressBook.contacts.map(contact => contact.name)
val contactsWithPhone = addressBook.contacts.filter(contact => contact.phoneNumbers.nonEmpty)


// prepending to a list is a constant time operation because it reuses the tail list
val contactNames2 = "Eloy" :: contactNames

// :: is right associative
alice :: bob :: Nil
// is the same as:
Nil.::(bob).::(alice)


addressBook.contacts match
  case first :: second :: Nil => second
  case _ => println("unexpected number of contacts")

val fruits = List("apples", "oranges", "pears")
fruits.tail
fruits(0)

fruits ++ List("bananas", "strawberries")