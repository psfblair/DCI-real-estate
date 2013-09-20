package net.phobot.realestate.contexts.closing

import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.model._

class ClosingContext {
//  val repository = new Object with Repository {
//    def find[EntityType <: Entity, IdType](id: IdType) : Option[EntityType] = { None }
//    def find[EntityType <: Entity, RoleType, IdType](id: IdType) : Option[RoleType] = { None }
//  }

  def execute(buyerId: Int, sellerId: Int) {
    val theCast = for {
                    buyer <- castBuyer(buyerId)
                    buyerAttorney <- castBuyerAttorney(buyer)
                    seller <- castSeller(sellerId)
                    sellerAttorney <- castSellerAttorney(seller)
                } yield (buyer, seller, buyerAttorney, sellerAttorney)
    // Kick it off
  }

  private def castBuyer(buyerId: Long) : Option[Buyer] = {
    ClosingRepository.find[Buyer, BuyerKey](new BuyerKey(buyerId))
    None
  }

  private def castSeller(sellerId: Long) : Option[Seller] = {
    ClosingRepository.find[Seller, SellerKey](new SellerKey(sellerId))
    None
  }


  private def castBuyerAttorney(buyer: Buyer[Actor]) : Option[BuyersAttorney[Attorney]] = {
//    repository.find[Attorney, BuyersAttorney, Long](buyer.attorney)
    None
  }

  private def castSellerAttorney(seller: Seller[Actor]) : Option[SellersAttorney[Attorney]] = {
//    repository.find[Attorney, SellersAttorney, Long](seller.attorney)
    None
  }

}
