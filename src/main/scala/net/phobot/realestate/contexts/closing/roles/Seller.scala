package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing.roles.attributes._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument
import net.phobot.realestate.contexts.closing.roles.payments.CertifiedCheck

class Seller(val key: SellerKey,
                 name: Name,
             val attorney: SellersAttorney,
             val realEstateAgent: Option[SellersRealEstateAgent],
             val documents: Map[Class[_ <: ClosingDocument], ClosingDocument],
             val payments: Map[String, CertifiedCheck]) {
  def fullName = name.fullName
}

case class SellerKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long]{
  def id = myId
}
