package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.dataaccess.RoleKey

class BuyersRealEstateAgent(val key: BuyersRealEstateAgentKey, val client: Buyer)

case class BuyersRealEstateAgentKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}