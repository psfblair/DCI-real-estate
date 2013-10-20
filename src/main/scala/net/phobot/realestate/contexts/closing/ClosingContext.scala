package net.phobot.realestate.contexts.closing

import ClosingRoleKeyConversions._
import net.phobot.realestate.dataaccess.RoleKey

case class PurchaseKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}

class ClosingContext {
  // Long -> RoleKey conversions are on object RoleKeyConversions
  def execute(purchaseId: Long, buyerId: Long, sellerId: Long, lenderId: Long, titleCompanyId: Long, notaryId: Long) {
    val theCast = for (
                    buyer <- ClosingRepository.findBuyer(buyerId, purchaseId) ;
                    seller <- ClosingRepository.findSeller(sellerId, purchaseId) ;
                    lender <- ClosingRepository.findLender(lenderId, purchaseId) ;
                    titleCompany <- ClosingRepository.findTitleCompany(titleCompanyId, purchaseId)
                  ) yield (buyer, seller, lender, titleCompany)
    // Kick it off - closing agent should orchestrate everything?
  }
}

/* Entities we need:

  Documents - we will have one table and a table of document types. These documents are all for a particular purchase.
    Mortgage - needs borrower's signature, lender's signature
    Promissory note - needs borrower's signature, lender's signature
    Contract of sale - needs borrower's signature, seller's signature
    Evidence of inspection
    Evidence of insurance
    Closing statement
    Title deed
  Signatures - join actors and documents, have a name and date

  Certified checks - forget cash for this.
    A check is in the possession of only one individual actor
    A check belongs to one actor
    A check is payable to one actor
    A check is for a particular purchase

  Payments for:
    Down payment
    Lender fees
    Attorney fees
    Closing costs (to closing agent)
    Broker fee
    Taxes
    Loan proceeds
*/

/* Simplified scenario:

Preconditions (i.e., "validation")
  Buyer must have:
    Check for down payment (if applicable)
    Check for lender fees
    Check for attorney fees
    Check for closing agent closing costs
    Evidence of inspections for lender, if required
    Evidence of homeowner's insurance for lender, if required

  Seller must have:
    Check for attorney fee (if applicable)
    Check for broker fee?
    Check for transfer tax
    Deed to property?
    Key to property?

  One or the other attorney, or closing agent must have:
    Closing statement
    Contract of sale ?

  Lender must have:
    Mortgage document
    Promissory note document
    Certified check for loan amount

What happens:
  Buyer provides lender with evidence of inspections/insurance if necessary
  Buyer reviews and signs mortgage and promissory note
	  Sad path: If something is different than expected or agreed to, don’t sign until it is resolved
	Buyer and seller review and sign contract of sale
	Seller provides keys and deed to closing agent

  Buyer pays buyer's attorney with certified check
  Buyer pays lender's fees to lender with certified check
  Buyer pays any purchaser taxes to closing agent with certified check
  Lender distributes funds covering home loan amount to the closing agent.
  Closing agent distributes purchase price to seller
  Closing agent gives keys to buyer
  Closing agent gives deed to lender
  Seller pays brokers' fees to seller's broker
  Seller pays seller's attorney with certified check
  Seller pays transfer taxes to closing agent with certified check


  Leave out:
  Seller's broker pays buyer's broker (later?)
  Everybody gets copies of all of the documents
*/


/*
Buyers need to come to the closing with certified checks for:
	Down payment (if applicable) - pay to seller
	Closing costs - pay to lender:
    Fee for running your credit report.
    A loan origination fee (to lender)
    UCC-1 filing fee
    Discount points, which are fees you pay in exchange for a lower interest rate.
    Prepaid interest
    Underwriting fee, which covers the cost of evaluating a mortgage loan application.
  Closing costs - pay to attorney:
    Attorney’s fees
  Closing costs - pay to closing agent?
    Taxes (flip tax, mansion tax)
    Recording fee, which is paid to a city or county in exchange for recording the new land records.
  Closing costs - pay to ?
    Charges for any inspection required or requested by the lender or you.
    Appraisal fee.
    Survey fee, which covers the cost of verifying property lines.
    Title insurance, which protects the lender in case the title isn’t clean.
    Title search fees
    Escrow deposit, which may pay for a couple months' property taxes and private mortgage insurance.
    Pest inspection fee.
    insurance?

Buyer may need to provide evidence of required homeowners insurance and inspections (if applicable)

Seller needs to come with key to property?

Attorneys will have a closing statement (HUD-1 Settlement Statement) with:
     costs paid by buyer & seller
     how much money each should walk away with after brokers and attorney’s fees, flip taxes etc.
     itemized list of buyer’s and seller’s “debits and credits” in the transaction.
	Buyer and seller should have received a copy at least one day before closing for review.

Lender's representative will have:
	Mortgage: Documents in which buyer agrees to a lien on the property
	Promissory note: Buyer's promise to pay the lender according to the agreed terms, including dates
		on which you must make mortgage payments and where they must be sent.

Review and sign all loan documents:
	If something is different than expected or agreed to, don’t sign until it is resolved

* Ending:
*   Buyer pays buyer's attorney with certified check
*   Buyer pays lender's fees to lender with certified check
*   Buyer pays any purchaser taxes to closing agent with certified check
*   Lender distributes funds covering home loan amount to the closing agent.
*   Closing agent distributes purchase price to seller
*   Closing agent gives keys to Buyer
*   Seller pays brokers' fees to seller's broker
*   Seller pays seller's attorney with certified check
*   Seller pays transfer taxes to closing agent with certified check
*   Seller's broker pays buyer's broker (later?)

Later:
*   attorneys record deed and mortgage in public records,

 */

/* Possible pre-closing scenarios:

   Prospective seller engages real estate agent
   Prospective seller lists property
   Prospective buyer engages real estate agent
   Real estate agent shows properties to prospective buyer
   Buyer completes purchase application
   Property inspection
   Title search
   Buyer pre-closing walk-through of property with real estate agent - verify nothing is wrong with the property

*/