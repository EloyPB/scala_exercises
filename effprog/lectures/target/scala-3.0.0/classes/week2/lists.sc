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