package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.dataaccess.RoleKey

class BuyersRealEstateAgent(val key: BuyersRealEstateAgentKey) {
  private var _client: Option[Buyer] = None

  def client_= (buyer: Buyer) = { _client = _client.setOnlyOnce(buyer) }
  def client = _client match { case Some(buyer) => buyer; case None => throw new IllegalStateException }
}

case class BuyersRealEstateAgentKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}