package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.contexts.closing.roles.attributes._

class Buyer(val key: BuyerKey, name: Name) {
  private var _attorney = null: BuyersAttorney

  def attorney_= (buyersAttorney: BuyersAttorney) : Unit = {
    if (_attorney eq null) _attorney = buyersAttorney else throw new IllegalStateException
  }
  def attorney = if (_attorney eq null) throw new IllegalStateException else _attorney

  def fullName = name.fullName
}

case class BuyerKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}
