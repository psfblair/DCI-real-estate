package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class SellersAttorney(val entityId: Long, private val repository: Repository) {
  def client : Seller = { repository.clientFor(this) }
}