package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class LendersRepresentative(val entityId: Long) {
  def represents : Option[Lender] = { ClosingRepository.representedBy(this) }
}
