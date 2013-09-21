package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class BuyersAttorney(val entityId: Long) {
  def client : Option[Buyer] = { ClosingRepository.clientFor(this) }
}

case class BuyersAttorneyKey(private val myId: Long) extends RoleKey[Long](myId)