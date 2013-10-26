package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.contexts.closing.roles.attributes._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument
import net.phobot.realestate.contexts.closing.roles.payments.{PaymentType, CertifiedCheck}

class Buyer(val key: BuyerKey,
                name: Name,
            val attorney: BuyersAttorney,
            val realEstateAgent: Option[BuyersRealEstateAgent],
            val documents: Map[Class[_ <: ClosingDocument], ClosingDocument],
            val payments: Map[PaymentType.Value, CertifiedCheck]) {
  def fullName = name.fullName
}

case class BuyerKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}