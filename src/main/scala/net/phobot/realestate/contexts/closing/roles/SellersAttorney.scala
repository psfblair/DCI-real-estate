package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.dataaccess.RoleKey

class SellersAttorney(val entityId: SellersAttorneyKey, val client: Seller)

case class SellersAttorneyKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}