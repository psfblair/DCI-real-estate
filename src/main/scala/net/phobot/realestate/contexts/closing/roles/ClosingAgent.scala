package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.dataaccess.RoleKey

class ClosingAgent(val entityId: ClosingAgentKey, val client: TitleCompany)

case class ClosingAgentKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}