package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._

class ClosingAgent(val entityId: Long, private val repository: Repository) {
  def represents : TitleCompany = { repository.representedBy(this) }
}
