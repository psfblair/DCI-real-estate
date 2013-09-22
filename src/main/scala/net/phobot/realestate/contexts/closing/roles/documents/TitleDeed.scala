package net.phobot.realestate.contexts.closing.roles.documents

import net.phobot.realestate.dataaccess.RoleKey

case class TitleDeed[RoleKeyType](val key: TitleDeedKey, var inThePossessionOf: RoleKey[RoleKeyType]) extends ClosingDocument
case class TitleDeedKey(private val myId: Long) extends RoleKey[Long] { def id = myId }