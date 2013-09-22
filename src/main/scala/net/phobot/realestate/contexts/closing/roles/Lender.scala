package net.phobot.realestate.contexts.closing.roles

import scala.collection.mutable.{Set => MutableSet}
import net.phobot.realestate.contexts.closing.roles.attributes.Name
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument

class Lender(val key: LenderKey, name: Name, val documents: MutableSet[ClosingDocument]) {
  private var _representedBy = null: LendersRepresentative

  def representedBy_= (buyersAttorney: LendersRepresentative) : Unit = {
    if (_representedBy eq null) _representedBy = buyersAttorney else throw new IllegalStateException
  }
  def representedBy = if (_representedBy eq null) throw new IllegalStateException else _representedBy

  def fullName = name.fullName
}

case class LenderKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}