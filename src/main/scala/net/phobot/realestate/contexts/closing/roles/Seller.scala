package net.phobot.realestate.contexts.closing.roles

import scala.collection.mutable.{Set => MutableSet}
import net.phobot.realestate.contexts.closing.roles.attributes._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument

class Seller(val key: SellerKey,
                 name: Name,
             val attorney: SellersAttorney,
             val realEstateAgent: Option[SellersRealEstateAgent],
             val documents: MutableSet[ClosingDocument]) {
  def fullName = name.fullName
}

case class SellerKey(private val myId: Long) extends RoleKey[Long]{
  def id = myId
}
