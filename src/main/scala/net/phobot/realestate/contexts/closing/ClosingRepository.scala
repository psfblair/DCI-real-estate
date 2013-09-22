package net.phobot.realestate.contexts.closing

import scala.language.implicitConversions
import scala.language.postfixOps

import org.jooq._
import org.jooq.scala.Conversions._

import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.model.tables.Actors.ACTORS
import net.phobot.realestate.model.tables.Individuals.INDIVIDUALS
import net.phobot.realestate.model.tables.Organizations.ORGANIZATIONS
import net.phobot.realestate.model.tables.Attorneys.ATTORNEYS
import net.phobot.realestate.model.tables.ActorsRepresentatives.ACTORS_REPRESENTATIVES
import net.phobot.realestate.model.tables.Representatives.REPRESENTATIVES
import net.phobot.realestate.model.tables.RealEstateAgents.REAL_ESTATE_AGENTS
import net.phobot.realestate.contexts.closing.roles.attributes.{Name => NameAttribute}
import net.phobot.realestate.dataaccess._
import net.phobot.realestate.contexts.closing.roles.TitleCompanyKey
import net.phobot.realestate.contexts.closing.roles.attributes.IndividualName
import net.phobot.realestate.contexts.closing.roles.LenderKey
import net.phobot.realestate.contexts.closing.roles.attributes.OrganizationName
import net.phobot.realestate.contexts.closing.roles.BuyerKey
import net.phobot.realestate.contexts.closing.roles.SellerKey

import ClosingRoleKeyConversions._

object ClosingRoleConversions {
  implicit def buyerAsOption(buyer: Buyer): Option[Buyer] = Option(buyer)
  implicit def sellerAsOption(seller: Seller): Option[Seller] = Option(seller)
  implicit def lenderAsOption(lender: Lender): Option[Lender] = Option(lender)
  implicit def titleCompanyAsOption(company: TitleCompany): Option[TitleCompany] = Option(company)
}

object ClosingRoleKeyConversions {
  implicit def asPurchaseKey(purchaseId: Long): PurchaseKey                         = PurchaseKey(purchaseId)
  implicit def asBuyerKey(buyerId: Long): BuyerKey                                  = BuyerKey(buyerId)
  implicit def asSellerKey(sellerId: Long): SellerKey                               = SellerKey(sellerId)
  implicit def asLenderKey(lenderId: Long): LenderKey                               = LenderKey(lenderId)
  implicit def asTitleCompanyKey(companyId: Long): TitleCompanyKey                  = TitleCompanyKey(companyId)

  implicit def asBuyersAttorneyKey(attorneyId: java.lang.Long): BuyersAttorneyKey             = BuyersAttorneyKey(attorneyId)
  implicit def asSellersAttorneyKey(attorneyId: java.lang.Long): SellersAttorneyKey           = SellersAttorneyKey(attorneyId)
  implicit def asBuyersRealEstateAgentKey(repId: java.lang.Long): BuyersRealEstateAgentKey    = BuyersRealEstateAgentKey(repId)
  implicit def asSellersRealEstateAgentKey(repId: java.lang.Long): SellersRealEstateAgentKey  = SellersRealEstateAgentKey(repId)
  implicit def asLendersRepresentativeKey(repId: java.lang.Long): LendersRepresentativeKey    = LendersRepresentativeKey(repId)
  implicit def asClosingAgentKey(repId: java.lang.Long): ClosingAgentKey                      = ClosingAgentKey(repId)
}

object ClosingRepository extends Repository {
  import DatabaseResultConversions._
  import ClosingRoleConversions._

  def findBuyer(buyerKey: BuyerKey, purchaseKey: PurchaseKey) : Option[Buyer] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, buyerKey)) ;
      name <- findActorName(buyerKey) ;
      buyer <- new Buyer(buyerKey, name);
      attorney <- findBuyerAttorney(buyer, purchaseKey)
    ) yield {
      buyer.attorney = attorney
      buyer.realEstateAgent = findBuyerRealEstateAgent(buyer, purchaseKey)
      buyer
    }
  }

  def findSeller(sellerKey: SellerKey, purchaseKey: PurchaseKey) : Option[Seller] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, sellerKey)) ;
      name <- findActorName(sellerKey) ;
      seller <- new Seller(sellerKey, name) ;
      attorney  <- findSellerAttorney(seller, purchaseKey)
    ) yield {
      seller.attorney = attorney
      seller.realEstateAgent = findSellerRealEstateAgent(seller, purchaseKey)
      seller
    }
  }

  def findLender(lenderKey: LenderKey, purchaseKey: PurchaseKey) : Option[Lender] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, lenderKey)) ;
      name <- findActorName(lenderKey) ;
      lender <- new Lender(lenderKey, name) ;
      lenderRepresentative  <- findLenderRepresentative(lender, purchaseKey)
    ) yield {
      lender.representedBy = lenderRepresentative
      lender
    }
  }

  def findTitleCompany(titleCompanyKey: TitleCompanyKey, purchaseKey: PurchaseKey) : Option[TitleCompany] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, titleCompanyKey)) ;
      name <- findActorName(titleCompanyKey) ;
      titleCompany <- new TitleCompany(titleCompanyKey, name) ;
      closingAgent  <- findClosingAgent(titleCompany, purchaseKey)
    ) yield {
      titleCompany.representedBy = closingAgent
      titleCompany
    }
  }

  private def findActorName[IdType](key: RoleKey[IdType]): Option[NameAttribute] = {
    val individualRecordId = RecordIdentifier(INDIVIDUALS, INDIVIDUALS.ACTOR_ID, key)
    val individualName = for (
      individual: Record <- findRecordForEntity(individualRecordId) ;
      firstNameField <- attributeValueFor(individual, INDIVIDUALS.FIRST_NAME) ;
      lastNameField <- attributeValueFor(individual, INDIVIDUALS.LAST_NAME)
    ) yield new IndividualName(individualRecordId, firstNameField, lastNameField)

    val organizationRecordId = RecordIdentifier(ORGANIZATIONS, ORGANIZATIONS.ACTOR_ID, key)
    val organizationName = for (
      individualNotFound <- individualName match { case None => Some(true); case Some(_) => None };
      organization <- findRecordForEntity(organizationRecordId) ;
      organizationNameField <- attributeValueFor(organization, ORGANIZATIONS.NAME)
    ) yield new OrganizationName(organizationRecordId, organizationNameField)

    List(individualName, organizationName) filter (x => x.isDefined) head
  }

  private def findBuyerAttorney(buyer: Buyer, purchaseKey: PurchaseKey) : Option[BuyersAttorney] = {
    for {
      attorneyRecord <- findAttorneyFor(buyer.key, purchaseKey)
    } yield new BuyersAttorney(attorneyRecord.getValue(ATTORNEYS.REPRESENTATIVE_ID), buyer)
  }

  private def findSellerAttorney(seller: Seller, purchaseKey: PurchaseKey) : Option[SellersAttorney] = {
    for {
      attorneyRecord <- findAttorneyFor(seller.key, purchaseKey)
    } yield new SellersAttorney(attorneyRecord.getValue(ATTORNEYS.REPRESENTATIVE_ID), seller)
  }

  private def findAttorneyFor(key: RoleKey[Long], purchaseKey: PurchaseKey): Option[Record] = {
    try{
      (context.select()
                from ATTORNEYS
                where ATTORNEYS.REPRESENTATIVE_ID === ACTORS_REPRESENTATIVES.REPRESENTATIVE_ID
                and ACTORS_REPRESENTATIVES.PURCHASE_ID === purchaseKey.id
                and ACTORS_REPRESENTATIVES.ACTOR_ID === key.id
                fetchAny())
    } catch { case e: Throwable => None }
  }

  private def findBuyerRealEstateAgent(buyer: Buyer, purchaseKey: PurchaseKey) : Option[BuyersRealEstateAgent] = {
    for {
      realEstateAgentRecord <- findRealEstateAgentFor(buyer.key, purchaseKey)
    } yield new BuyersRealEstateAgent(realEstateAgentRecord.getValue(REPRESENTATIVES.ACTOR_ID), buyer)
  }

  private def findSellerRealEstateAgent(seller: Seller, purchaseKey: PurchaseKey) : Option[SellersRealEstateAgent] = {
    for {
      realEstateAgentRecord <- findRealEstateAgentFor(seller.key, purchaseKey)
    } yield new SellersRealEstateAgent(realEstateAgentRecord.getValue(REPRESENTATIVES.ACTOR_ID), seller)
  }

  private def findRealEstateAgentFor(key: RoleKey[Long], purchaseKey: PurchaseKey): Option[Record] = {
    try{
      (context.select()
                from REAL_ESTATE_AGENTS
                where REAL_ESTATE_AGENTS.REPRESENTATIVE_ID === ACTORS_REPRESENTATIVES.REPRESENTATIVE_ID
                and ACTORS_REPRESENTATIVES.PURCHASE_ID === purchaseKey.id
                and ACTORS_REPRESENTATIVES.ACTOR_ID === key.id
                fetchAny())
    } catch { case e: Throwable => None }
  }

  private def findLenderRepresentative(lender: Lender, purchaseKey: PurchaseKey) : Option[LendersRepresentative] = {
    for {
      representativeRecord <- findRepresentativeFor(lender.key, purchaseKey)
    } yield new LendersRepresentative(representativeRecord.getValue(REPRESENTATIVES.ACTOR_ID), lender)
  }

  private def findClosingAgent(titleCompany: TitleCompany, purchaseKey: PurchaseKey) : Option[ClosingAgent] = {
    for {
      representativeRecord <- findRepresentativeFor(titleCompany.key, purchaseKey)
    } yield new ClosingAgent(representativeRecord.getValue(REPRESENTATIVES.ACTOR_ID), titleCompany)
  }

  private def findRepresentativeFor(key: RoleKey[Long], purchaseKey: PurchaseKey): Option[Record] = {
    try{
      (context.select()
                from REPRESENTATIVES
                where REPRESENTATIVES.ACTOR_ID === ACTORS_REPRESENTATIVES.REPRESENTATIVE_ID
                and ACTORS_REPRESENTATIVES.PURCHASE_ID === purchaseKey.id
                and ACTORS_REPRESENTATIVES.ACTOR_ID === key.id
                fetchAny())
    } catch { case e: Throwable => None }
  }
}
