package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.dataaccess.RoleKey

class BuyersAttorney(val key: BuyersAttorneyKey, val client: Buyer)

case class BuyersAttorneyKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}

