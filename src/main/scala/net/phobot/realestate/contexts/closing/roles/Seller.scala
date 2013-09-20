package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class Seller(val key: SellerKey) {
  def fullName = { ClosingRepository.nameFor(key) }
  def attorney = { ClosingRepository.attorneyFor(key) }
}

case class SellerKey(val id: Long)
