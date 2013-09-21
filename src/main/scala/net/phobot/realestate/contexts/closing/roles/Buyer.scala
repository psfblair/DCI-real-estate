package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.contexts.closing._
import net.phobot.realestate.contexts.closing.roles.attributes._

class Buyer(val key: BuyerKey, name: Name) {
  private var _attorney: Option[BuyersAttorney] = None
  private var _realEstateAgent: Option[BuyersRealEstateAgent] = None

  def attorney_= (buyersAttorney: BuyersAttorney) = { _attorney = _attorney.setOnlyOnce(buyersAttorney) }
  def attorney = _attorney match { case Some(atty) => atty; case None => throw new IllegalStateException }

  def realEstateAgent_= (agent: Option[BuyersRealEstateAgent]) : Unit = { _realEstateAgent = agent }
  def realEstateAgent = _realEstateAgent

  def fullName = name.fullName
}


case class BuyerKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}