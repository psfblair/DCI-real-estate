package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.dataaccess.RoleKey

class ClosingAgent(val entityId: ClosingAgentKey, val client: TitleCompany)

case class ClosingAgentKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}