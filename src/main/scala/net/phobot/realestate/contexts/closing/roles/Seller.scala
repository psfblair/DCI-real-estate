package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.contexts.closing.roles.attributes._
import net.phobot.realestate.dataaccess.RoleKey

class Seller(val key: SellerKey, name: Name) {
  private var _attorney: Option[SellersAttorney] = None
  private var _realEstateAgent: Option[SellersRealEstateAgent] = None

  def attorney_= (sellersAttorney: SellersAttorney) = { _attorney = _attorney.setOnlyOnce(sellersAttorney) }
  def attorney = _attorney match { case Some(atty) => atty; case None => throw new IllegalStateException }

  def realEstateAgent_= (agent: Option[SellersRealEstateAgent]) : Unit = { _realEstateAgent = agent }
  def realEstateAgent = _realEstateAgent

  def fullName = name.fullName
}

case class SellerKey(private val myId: Long) extends RoleKey[Long]{
  def id = myId
}
