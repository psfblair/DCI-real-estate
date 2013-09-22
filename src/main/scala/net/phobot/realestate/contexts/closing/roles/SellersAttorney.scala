package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.dataaccess.RoleKey

class SellersAttorney(val entityId: SellersAttorneyKey) {
  private var _client: Option[Seller] = None

  def client_= (seller: Seller) = { _client = _client.setOnlyOnce(seller) }
  def client = _client match { case Some(seller) => seller; case None => throw new IllegalStateException }
}

case class SellersAttorneyKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}