package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing.Repository

class TitleCompany(val entityId: Long, private val repository: Repository) {
  def representedBy : ClosingAgent = { repository.representativeFor(this) }
}
