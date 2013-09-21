package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing.RoleKey

class LendersRepresentative(val entityId: LendersRepresentativeKey, val client: Lender)

case class LendersRepresentativeKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}