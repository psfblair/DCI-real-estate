package net.phobot.realestate.contexts.closing.roles.documents

import net.phobot.realestate.dataaccess.RoleKey

case class ClosingStatement[RoleKeyType](val key: ClosingStatementKey, var inThePossessionOf: RoleKey[RoleKeyType]) extends ClosingDocument
case class ClosingStatementKey(private val myId: Long) extends RoleKey[Long] { def id = myId }
