package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class BuyersAttorney(val entityId: Long, private val repository: Repository) {
  def client : Buyer = { repository.clientFor(this) }
}