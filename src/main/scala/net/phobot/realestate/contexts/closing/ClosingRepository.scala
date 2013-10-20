package net.phobot.realestate.contexts.closing

import _root_.scala.Some
import scala.collection.JavaConverters._
import scala.collection.mutable.{Set => MutableSet}
import scala.language.implicitConversions
import scala.language.postfixOps

import org.jooq._
import org.jooq.scala.Conversions._

import net.phobot.realestate.model.tables.Actors.ACTORS
import net.phobot.realestate.model.tables.Individuals.INDIVIDUALS
import net.phobot.realestate.model.tables.Organizations.ORGANIZATIONS
import net.phobot.realestate.model.tables.Attorneys.ATTORNEYS
import net.phobot.realestate.model.tables.ActorsRepresentatives.ACTORS_REPRESENTATIVES
import net.phobot.realestate.model.tables.Representatives.REPRESENTATIVES
import net.phobot.realestate.model.tables.RealEstateAgents.REAL_ESTATE_AGENTS
import net.phobot.realestate.model.tables.Documents.DOCUMENTS
import net.phobot.realestate.model.tables.DocumentTypes.DOCUMENT_TYPES
import net.phobot.realestate.model.tables.CertifiedChecks._
import net.phobot.realestate.model.tables.PaymentTypes.PAYMENT_TYPES

import net.phobot.realestate.dataaccess._
import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.contexts.closing.roles.documents._
import net.phobot.realestate.contexts.closing.roles.payments._
import net.phobot.realestate.contexts.closing.roles.attributes.{Name => NameAttribute, _}

import ClosingRoleKeyConversions._
import java.math

object ClosingRoleConversions {
  implicit def buyerAsOption(buyer: Buyer): Option[Buyer] = Option(buyer)
  implicit def sellerAsOption(seller: Seller): Option[Seller] = Option(seller)
  implicit def lenderAsOption(lender: Lender): Option[Lender] = Option(lender)
  implicit def titleCompanyAsOption(company: TitleCompany): Option[TitleCompany] = Option(company)
}

trait ActorKey[KeyType <: RoleKey[IdType], IdType]{
  def id(key: KeyType): IdType
}
case class UnknownActorKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] { def id = myId }

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
  implicit def asOptionalUnknownAgentKey(id: java.lang.Long) : Option[RoleKey[Any]]           = Option(UnknownActorKey(id).asInstanceOf[RoleKey[Any]]) //??

  implicit object BuyerKeyAsActorKey extends ActorKey[BuyerKey, java.lang.Long] { def id(key: BuyerKey) = key.id }
  implicit object SellerKeyAsActorKey extends ActorKey[SellerKey, java.lang.Long] { def id(key: SellerKey) = key.id }
  implicit object LendersRepresentativeKeyAsActorKey extends ActorKey[LendersRepresentativeKey, java.lang.Long] { def id(key: LendersRepresentativeKey) = key.id }
  implicit object ClosingAgentKeyAsActorKey extends ActorKey[ClosingAgentKey, java.lang.Long] { def id(key: ClosingAgentKey) = key.id }
  implicit object BuyersAttorneyKeyAsActorKey extends ActorKey[BuyersAttorneyKey, java.lang.Long] { def id(key: BuyersAttorneyKey) = key.id }
  implicit object SellersAttorneyKeyAsActorKey extends ActorKey[SellersAttorneyKey, java.lang.Long] { def id(key: SellersAttorneyKey) = key.id }
  implicit object BuyersRealEstateAgentKeyAsActorKey extends ActorKey[BuyersRealEstateAgentKey, java.lang.Long] { def id(key: BuyersRealEstateAgentKey) = key.id }
  implicit object SellersRealEstateAgentKeyAsActorKey extends ActorKey[SellersRealEstateAgentKey, java.lang.Long] { def id(key: SellersRealEstateAgentKey) = key.id }
  implicit object UnknownActorKeyAsActorKey extends ActorKey[UnknownActorKey, java.lang.Long] { def id(key: UnknownActorKey) = key.id }
}

object ClosingRepository extends JOOQRepository {
  import DatabaseResultConversions._
  import ClosingRoleConversions._

  def findBuyer(buyerKey: BuyerKey, purchaseKey: PurchaseKey) : Option[Buyer] = {
    val buyerIterable = for (
      actor <- findRecordForEntity(JOOQRecordIdentifier(ACTORS, ACTORS.ACTOR_ID, buyerKey)) ;
      name <- findActorName(buyerKey) ;
      attorney <- findBuyerAttorney(buyerKey, purchaseKey)
    ) yield {
      val realEstateAgent = findBuyerRealEstateAgent(buyerKey, purchaseKey)  // Option
      val initialDocuments = findDocumentsFor(buyerKey, purchaseKey)
      val checks = findChecksCarriedBy(buyerKey, purchaseKey)
      val buyer = new Buyer(buyerKey, name, attorney, realEstateAgent, initialDocuments, checks)
      attorney.client = buyer
      realEstateAgent foreach (agent => agent.client = buyer)
      buyer
    }
    buyerIterable head
  }

  def findSeller(sellerKey: SellerKey, purchaseKey: PurchaseKey) : Option[Seller] = {
    val sellerIterable = for (
      actor <- findRecordForEntity(JOOQRecordIdentifier(ACTORS, ACTORS.ACTOR_ID, sellerKey)) ;
      name <- findActorName(sellerKey) ;
      attorney  <- findSellerAttorney(sellerKey, purchaseKey)
    ) yield {
      val realEstateAgent = findSellerRealEstateAgent(sellerKey, purchaseKey)  // Option
      val initialDocuments = findDocumentsFor(sellerKey, purchaseKey)
      val checks = findChecksCarriedBy(sellerKey, purchaseKey)
      val seller = new Seller(sellerKey, name, attorney, realEstateAgent, initialDocuments, checks) ;
      attorney.client = seller
      realEstateAgent foreach (agent => agent.client = seller)
      seller
    }
    sellerIterable head
  }

  def findLender(lenderKey: LenderKey, purchaseKey: PurchaseKey) : Option[Lender] = {
    for (
      actor <- findRecordForEntity(JOOQRecordIdentifier(ACTORS, ACTORS.ACTOR_ID, lenderKey)) ;
      name <- findActorName(lenderKey) ;
      lender <- new Lender(lenderKey, name) ;
      lenderRepresentative <- findLenderRepresentative(lender, purchaseKey)
    ) yield {
      lender.representedBy = lenderRepresentative
      lender
    }
  }

  def findTitleCompany(titleCompanyKey: TitleCompanyKey, purchaseKey: PurchaseKey) : Option[TitleCompany] = {
    for (
      actor <- findRecordForEntity(JOOQRecordIdentifier(ACTORS, ACTORS.ACTOR_ID, titleCompanyKey)) ;
      name <- findActorName(titleCompanyKey) ;
      titleCompany <- new TitleCompany(titleCompanyKey, name) ;
      closingAgent  <- findClosingAgent(titleCompany, purchaseKey)
    ) yield {
      titleCompany.representedBy = closingAgent
      titleCompany
    }
  }

  private def findActorName[IdType](key: RoleKey[IdType]): Option[NameAttribute] = {
    val individualRecordId = JOOQRecordIdentifier(INDIVIDUALS, INDIVIDUALS.ACTOR_ID, key)
    val individualName = for (
      individual: Record <- findRecordForEntity(individualRecordId) ;
      firstNameField <- nonNullableAttributeValueFor(individualRecordId, individual, INDIVIDUALS.FIRST_NAME) ;
      lastNameField <- nonNullableAttributeValueFor(individualRecordId, individual, INDIVIDUALS.LAST_NAME)
    ) yield new IndividualName(firstNameField, lastNameField)

    val organizationRecordId = JOOQRecordIdentifier(ORGANIZATIONS, ORGANIZATIONS.ACTOR_ID, key)
    val organizationName = for (
      individualNotFound <- individualName match { case None => Some(true); case Some(_) => None };
      organization <- findRecordForEntity(organizationRecordId) ;
      organizationNameField <- nonNullableAttributeValueFor(organizationRecordId, organization, ORGANIZATIONS.NAME)
    ) yield new OrganizationName(organizationNameField)

    List(individualName, organizationName) filter (x => x.isDefined) head
  }

  private def findBuyerAttorney(key: BuyerKey, purchaseKey: PurchaseKey) : Option[BuyersAttorney] = {
    for {
      attorneyRecord <- findAttorneyFor(key, purchaseKey)
    } yield new BuyersAttorney(attorneyRecord.getValue(ATTORNEYS.REPRESENTATIVE_ID))
  }

  private def findSellerAttorney(key: SellerKey, purchaseKey: PurchaseKey) : Option[SellersAttorney] = {
    for {
      attorneyRecord <- findAttorneyFor(key, purchaseKey)
    } yield new SellersAttorney(attorneyRecord.getValue(ATTORNEYS.REPRESENTATIVE_ID))
  }

  private def findAttorneyFor(key: RoleKey[java.lang.Long], purchaseKey: PurchaseKey): Option[Record] = {
    try{
      (context.select()
                from ATTORNEYS
                where ATTORNEYS.REPRESENTATIVE_ID === ACTORS_REPRESENTATIVES.REPRESENTATIVE_ID
                and ACTORS_REPRESENTATIVES.PURCHASE_ID === purchaseKey.id
                and ACTORS_REPRESENTATIVES.ACTOR_ID === key.id
                fetchAny())
    } catch { case e: Throwable => None }
  }

  private def findBuyerRealEstateAgent(key: BuyerKey, purchaseKey: PurchaseKey) : Option[BuyersRealEstateAgent] = {
    for {
      realEstateAgentRecord <- findRealEstateAgentFor(key, purchaseKey)
    } yield new BuyersRealEstateAgent(realEstateAgentRecord.getValue(REPRESENTATIVES.ACTOR_ID))
  }

  private def findSellerRealEstateAgent(key: SellerKey, purchaseKey: PurchaseKey) : Option[SellersRealEstateAgent] = {
    for {
      realEstateAgentRecord <- findRealEstateAgentFor(key, purchaseKey)
    } yield new SellersRealEstateAgent(realEstateAgentRecord.getValue(REPRESENTATIVES.ACTOR_ID))
  }

  private def findRealEstateAgentFor(key: RoleKey[java.lang.Long], purchaseKey: PurchaseKey): Option[Record] = {
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
    } yield {
      val lendersRepresentativeKey: LendersRepresentativeKey = representativeRecord.getValue(REPRESENTATIVES.ACTOR_ID)
      val initialDocuments = findDocumentsFor(lendersRepresentativeKey, purchaseKey)
      val checks = findChecksCarriedBy(lendersRepresentativeKey, purchaseKey)
      val representative = new LendersRepresentative(lendersRepresentativeKey, lender, initialDocuments, checks)
      representative
    }
  }

  private def findClosingAgent(titleCompany: TitleCompany, purchaseKey: PurchaseKey) : Option[ClosingAgent] = {
    for {
      representativeRecord <- findRepresentativeFor(titleCompany.key, purchaseKey)
    } yield new ClosingAgent(representativeRecord.getValue(REPRESENTATIVES.ACTOR_ID), titleCompany)
  }

  private def findRepresentativeFor(key: RoleKey[java.lang.Long], purchaseKey: PurchaseKey): Option[Record] = {
    try{
      (context.select()
                from REPRESENTATIVES
                where REPRESENTATIVES.ACTOR_ID === ACTORS_REPRESENTATIVES.REPRESENTATIVE_ID
                and ACTORS_REPRESENTATIVES.PURCHASE_ID === purchaseKey.id
                and ACTORS_REPRESENTATIVES.ACTOR_ID === key.id
                fetchAny())
    } catch { case e: Throwable => None }
  }

  private def findDocumentsFor(roleKey: RoleKey[java.lang.Long], purchaseKey: PurchaseKey) : Map[Class[_ <: ClosingDocument], ClosingDocument] = {

    implicit def CertificateOfInspectionAsOption[T](cert: CertificateOfInspection[T]): Option[CertificateOfInspection[T]] = Option(cert)
    implicit def CertificateOfInsuranceAsOption[T](cert: CertificateOfInsurance[T]): Option[CertificateOfInsurance[T]] = Option(cert)
    implicit def ClosingStatementAsOption[T](stmt: ClosingStatement[T]): Option[ClosingStatement[T]] = Option(stmt)
    implicit def ContractOfSaleAsOption[T](contract: ContractOfSale[T]): Option[ContractOfSale[T]] = Option(contract)
    implicit def MortgageAsOption[T](mortgage: Mortgage[T]): Option[Mortgage[T]] = Option(mortgage)
    implicit def PromissoryNoteAsOption[T](note: PromissoryNote[T]): Option[PromissoryNote[T]] = Option(note)
    implicit def TitleDeedAsOption[T](deed: TitleDeed[T]): Option[TitleDeed[T]] = Option(deed)

    try {
      val results: Iterator[Record] = (context.select (DOCUMENTS.ID, DOCUMENT_TYPES.DOC_TYPE)
                                                from (DOCUMENTS, DOCUMENT_TYPES)
                                                where DOCUMENTS.IN_POSSESSION_OF === ACTORS.ACTOR_ID
                                                and DOCUMENTS.PURCHASE_ID === purchaseKey.id
                                                and ACTORS.ACTOR_ID === roleKey.id
                                                and DOCUMENTS.DOCUMENT_TYPE_ID === DOCUMENT_TYPES.ID
                                                fetch).iterator().asScala

      val documentIterator: Iterator[Option[ClosingDocument]] = results map (record => {
        val id = record.getValue(DOCUMENTS.ID)
        record.getValue(DOCUMENT_TYPES.DOC_TYPE) match {
          case "CERTIFICATE_OF_INSPECTION"    => CertificateOfInspection(CertificateOfInspectionKey(id), roleKey)
          case "CERTIFICATE_OF_INSURANCE"     => CertificateOfInsurance(CertificateOfInsuranceKey(id), roleKey)
          case "CLOSING_STATEMENT"            => ClosingStatement(ClosingStatementKey(id), roleKey)
          case "CONTRACT_OF_SALE"             => ContractOfSale(ContractOfSaleKey(id), roleKey)
          case "MORTGAGE"                     => Mortgage(MortgageKey(id), roleKey)
          case "PROMISSORY_NOTE"              => PromissoryNote(PromissoryNoteKey(id), roleKey)
          case "TITLE_DEED"                   => TitleDeed(TitleDeedKey(id), roleKey)
          case _                              => None
        }
      })

      documentIterator.foldLeft(Map[Class[_ <: ClosingDocument], ClosingDocument]()) ((theMap: Map[Class[_ <: ClosingDocument], ClosingDocument], documentOption: Option[ClosingDocument]) => documentOption match {
          case Some(document) => theMap + (document.getClass -> document)
          case None => theMap
      })
    } catch { case e: Throwable => Map() }
  }

  private def findChecksCarriedBy[PossessorRoleKeyType <: RoleKey[java.lang.Long]]
                    (roleKey: PossessorRoleKeyType,
                     purchaseKey: PurchaseKey)
                    (implicit actorKeyObj: ActorKey[PossessorRoleKeyType, _]): Map[String, CertifiedCheck] = {

    implicit def nullableAmountAsMoneyOption(attribute: AttributeWithOptionalValue[math.BigDecimal, Option[math.BigDecimal]]): Option[Money] = {
      attribute.convertToOptionalAttributeValue map (x => MoneyImpl(x))
    }

    def createCheck(record: Record): CertifiedCheck = {
      val checkKey: CertifiedCheckKey = CertifiedCheckKey(record.getValue(CERTIFIED_CHECKS.ID))
      val recordIdentifier = JOOQRecordIdentifier(CERTIFIED_CHECKS, CERTIFIED_CHECKS.ID, checkKey)
      val check = new CertifiedCheck(checkKey, roleKey.asInstanceOf[RoleKey[Any]])    //Why do I have to do this conversion?

      check.amount = nullableAttributeValueFor(recordIdentifier, record, CERTIFIED_CHECKS.AMOUNT)
//      check.payableTo = nullableAttributeValueFor(recordIdentifier, record, CERTIFIED_CHECKS.PAYABLE_TO)
//      check.belongsTo = nullableAttributeValueFor(recordIdentifier, record, CERTIFIED_CHECKS.BELONGS_TO)
      check.paymentFor = PaymentType.values.find(_.toString == record.getValue(PAYMENT_TYPES.PAYMENT_TYPE))
      check
    }

    try {
      val results = (context.select (CERTIFIED_CHECKS.ID,
                                     CERTIFIED_CHECKS.AMOUNT,
                                     CERTIFIED_CHECKS.PAYABLE_TO,
                                     CERTIFIED_CHECKS.BELONGS_TO,
                                     PAYMENT_TYPES.PAYMENT_TYPE
                              )
                              from CERTIFIED_CHECKS
                              where CERTIFIED_CHECKS.PURCHASE_ID === purchaseKey.id
                              and CERTIFIED_CHECKS.PAYMENT_FOR === PAYMENT_TYPES.ID       //Don't want anything with null payment type
                              and CERTIFIED_CHECKS.IN_POSSESSION_OF === ACTORS.ACTOR_ID
                              and ACTORS.ACTOR_ID === roleKey.id
                              fetch).iterator().asScala
//      results foldLeft Map[String, CertifiedCheck]() ((theMap: Map[String, CertifiedCheck], theRecord: Record4[Long, BigDecimal, Long, Long]) => {
//        theMap + ("foo" -> createCheck(theRecord))
//      })
      Map()
    } catch { case e: Throwable => Map() }
  }
}
