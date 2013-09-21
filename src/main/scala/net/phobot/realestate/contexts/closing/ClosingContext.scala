package net.phobot.realestate.contexts.closing

import net.phobot.realestate.contexts.closing.roles._

case class PurchaseKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}

class ClosingContext {

  // Long -> RoleKey conversions are on object RoleKey
  def execute(purchaseId: Long, buyerId: Long, sellerId: Long, lenderId: Long) {
    val theCast = for {
                    buyer <- Repository.findBuyer(buyerId, purchaseId)
                    seller <- Repository.findSeller(sellerId, purchaseId)
                    lender <- Repository.findLender(lenderId, purchaseId)
                } yield (buyer, seller, lender)
    // Kick it off
  }
}
