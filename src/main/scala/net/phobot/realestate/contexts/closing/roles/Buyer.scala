package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class Buyer(val key: BuyerKey, name: Name[String]) {
  def fullName =
  def attorney = { ClosingRepository.attorneyFor(key) }
}

case class BuyerKey(private val myId: Long) extends RoleKey[Long](myId)
