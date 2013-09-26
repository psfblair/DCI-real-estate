package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.dataaccess.RoleKey

class LendersRepresentative(val entityId: LendersRepresentativeKey, val client: Lender)

case class LendersRepresentativeKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}