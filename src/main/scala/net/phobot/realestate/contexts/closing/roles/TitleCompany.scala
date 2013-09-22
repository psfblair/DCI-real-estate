package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing.roles.attributes.Name
import net.phobot.realestate.dataaccess.RoleKey

class TitleCompany(val key: TitleCompanyKey, name: Name) {
  private var _representedBy = null: ClosingAgent

  def representedBy_= (closingAgent: ClosingAgent) : Unit = {
    if (_representedBy eq null) _representedBy = closingAgent else throw new IllegalStateException
  }
  def representedBy = if (_representedBy eq null) throw new IllegalStateException else _representedBy

  def fullName = name.fullName
}

case class TitleCompanyKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}