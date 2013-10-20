package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.contexts.closing.roles.attributes.Name
import net.phobot.realestate.dataaccess.RoleKey

class Lender(val key: LenderKey, name: Name) {
  private var _representedBy: Option[LendersRepresentative] = None

  def representedBy_= (lenderRep: LendersRepresentative) = { _representedBy = _representedBy.setOnlyOnce(lenderRep) }
  def representedBy = _representedBy match { case Some(representative) => representative; case None => throw new IllegalStateException }

  def fullName = name.fullName
}

case class LenderKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}