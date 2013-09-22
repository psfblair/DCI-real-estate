package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.contexts.closing.roles.attributes.Name

class NotaryPublic(val key: NotaryPublicKey, name: Name) {

  def fullName = name.fullName
}

case class NotaryPublicKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}