package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class SellersAttorney(val entityId: Long) {
  def client : Option[Seller] = { ClosingRepository.clientFor(this) }
}

case class SellersAttorneyKey(private val myId: Long) extends RoleKey[Long](myId)
