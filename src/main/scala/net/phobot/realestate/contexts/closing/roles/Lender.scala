package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class Lender(val entityId: Long) {
  def representedBy : Option[LendersRepresentative] = { ClosingRepository.representativeFor(this) }
}
