package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing.ClosingRepository

class TitleCompany(val entityId: Long) {
  def representedBy : Option[ClosingAgent] = { ClosingRepository.representativeFor(this) }
}
