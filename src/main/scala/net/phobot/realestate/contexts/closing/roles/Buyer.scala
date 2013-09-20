package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class Buyer(val key: BuyerKey) {
  def fullName = { ClosingRepository.nameFor(key) }
  def attorney = { ClosingRepository.attorneyFor(key) }
}

case class BuyerKey(val id: Long)

