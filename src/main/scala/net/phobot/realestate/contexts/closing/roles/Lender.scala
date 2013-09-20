package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class Lender(val entityId: Long, private val repository: Repository) {
  def representedBy : LendersRepresentative = { repository.representativeFor(this) }
}
