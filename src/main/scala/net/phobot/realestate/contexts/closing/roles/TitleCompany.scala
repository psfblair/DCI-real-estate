package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.contexts.closing.roles.attributes.Name
import net.phobot.realestate.dataaccess.RoleKey

class TitleCompany(val key: TitleCompanyKey, name: Name) {
  private var _representedBy: Option[ClosingAgent] = None

  def representedBy_= (closingAgent: ClosingAgent) = { _representedBy = _representedBy.setOnlyOnce(closingAgent) }
  def representedBy = _representedBy match { case Some(closingAgent) => closingAgent; case None => throw new IllegalStateException }

  def fullName = name.fullName
}

case class TitleCompanyKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}