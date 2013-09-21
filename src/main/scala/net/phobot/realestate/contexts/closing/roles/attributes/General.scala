package net.phobot.realestate.contexts.closing.roles.attributes

case class Name(val fullName: String) {
  def this(firstName: String, lastName: String) = this(s"$firstName $lastName")
}

