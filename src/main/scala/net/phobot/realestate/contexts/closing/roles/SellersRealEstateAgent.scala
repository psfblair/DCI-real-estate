package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.dataaccess.RoleKey

class SellersRealEstateAgent(val key: SellersRealEstateAgentKey, val client: Seller)

case class SellersRealEstateAgentKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}
