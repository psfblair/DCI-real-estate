package net.phobot.realestate.contexts.closing

import net.phobot.realestate.contexts.closing.RoleKeyConversions._

case class PurchaseKey(private val myId: Long) extends RoleKey[Long] {
  def id = myId
}

class ClosingContext {

  // Long -> RoleKey conversions are on object RoleKeyConversions
  def execute(purchaseId: Long, buyerId: Long, sellerId: Long, lenderId: Long, titleCompanyId: Long, notaryId: Long) {
    val theCast = for (
                    buyer <- Repository.findBuyer(buyerId, purchaseId) ;
                    seller <- Repository.findSeller(sellerId, purchaseId) ;
                    lender <- Repository.findLender(lenderId, purchaseId) ;
                    titleCompany <- Repository.findTitleCompany(titleCompanyId, purchaseId) ;
                    notaryPublic <- Repository.findNotary(notaryId)
                  ) yield (buyer, seller, lender, titleCompany, notaryPublic)
    // Kick it off
  }
}
