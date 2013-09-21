package net.phobot.realestate.contexts.closing

import net.phobot.realestate.contexts.closing.roles._

case class PurchaseKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}

class ClosingContext {

  def execute(purchaseId: Long, buyerId: Long, sellerId: Long) {
    val theCast = for {
                    buyer <- castBuyer(buyerId, purchaseId)
                    seller <- castSeller(sellerId, purchaseId)
                } yield (buyer, seller)
    // Kick it off
  }

  private def castBuyer(buyerId: Long, purchaseId: Long) : Option[Buyer] = {
    ClosingRepository.findBuyer(BuyerKey(buyerId), PurchaseKey(purchaseId))
  }

  private def castSeller(sellerId: Long, purchaseId: Long) : Option[Seller] = {
    ClosingRepository.findSeller(SellerKey(sellerId), PurchaseKey(purchaseId))
  }
}
