package net.phobot.realestate.contexts.closing.roles

import scala.collection.mutable.{Set => MutableSet}
import net.phobot.realestate.contexts.closing.roles.attributes._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument

class Buyer(val key: BuyerKey,
                name: Name,
            val attorney: BuyersAttorney,
            val realEstateAgent: Option[BuyersRealEstateAgent],
            val documents: MutableSet[ClosingDocument]) {
  def fullName = name.fullName
}

case class BuyerKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}