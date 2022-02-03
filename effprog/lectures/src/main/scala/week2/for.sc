case class Contact(
                    name: String,
                    email: String,
                    phoneNumbers: List[String]
                  )

val contacts = List(Contact("alice", "alice@gmail.com", List("+41123", "+34123")),
  Contact("bob", "bob@gmail.com", List("+41123", "+49123")))

def namesAndSwissNumbers(contacts: List[Contact]): List[(String, String)] =
  for
    contact <- contacts
    phoneNumber <- contact.phoneNumbers
    if phoneNumber.startsWith("+41")
  yield (contact.name, phoneNumber)

namesAndSwissNumbers(contacts)


contacts
  .flatMap(contact => contact.phoneNumbers.withFilter(n => n.startsWith("+41"))
    .map((contact.name, _)))