package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class LendersRepresentative(val entityId: Long, private val repository: Repository) {
  def represents : Lender = { repository.representedBy(this) }
}
