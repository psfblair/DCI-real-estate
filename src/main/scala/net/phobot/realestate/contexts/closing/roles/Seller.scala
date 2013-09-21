package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.contexts.closing.roles.attributes._

class Seller(val key: SellerKey, name: Name) {
  private var _attorney = null: SellersAttorney

  def attorney_= (sellersAttorney: SellersAttorney) : Unit = {
    if (_attorney eq null) _attorney = sellersAttorney else throw new IllegalStateException
  }
  def attorney = if (_attorney eq null) throw new IllegalStateException else _attorney

  def fullName = name.fullName
}

case class SellerKey(private val myId: Long) extends RoleKey[Long]{
  def id = myId
}
