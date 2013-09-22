package net.phobot.realestate.contexts.closing

import scala.collection.mutable.{Set => MutableSet}
import scala.collection.JavaConverters._
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

import net.phobot.realestate.dataaccess._
import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.contexts.closing.roles.documents._
import net.phobot.realestate.contexts.closing.roles.attributes.{Name => NameAttribute, IndividualName, OrganizationName}

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
    val buyerIterable = for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, buyerKey)) ;
      name <- findActorName(buyerKey) ;
      attorney <- findBuyerAttorney(buyerKey, purchaseKey)
    ) yield {
      val initialDocuments = findDocumentsFor(buyerKey, purchaseKey)
      val realEstateAgent = findBuyerRealEstateAgent(buyerKey, purchaseKey)  // Option
      val buyer = new Buyer(buyerKey, name, attorney, realEstateAgent, initialDocuments)
      attorney.client = buyer
      realEstateAgent foreach (agent => agent.client = buyer)
      buyer
    }
    buyerIterable head
  }

  def findSeller(sellerKey: SellerKey, purchaseKey: PurchaseKey) : Option[Seller] = {
    val sellerIterable = for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, sellerKey)) ;
      name <- findActorName(sellerKey) ;
      attorney  <- findSellerAttorney(sellerKey, purchaseKey)
    ) yield {
      val initialDocuments = findDocumentsFor(sellerKey, purchaseKey)
      val realEstateAgent = findSellerRealEstateAgent(sellerKey, purchaseKey)  // Option
      val seller = new Seller(sellerKey, name, attorney, realEstateAgent, initialDocuments) ;
      attorney.client = seller
      realEstateAgent foreach (agent => agent.client = seller)
      seller
    }
    sellerIterable head
  }

  def findLender(lenderKey: LenderKey, purchaseKey: PurchaseKey) : Option[Lender] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, lenderKey)) ;
      name <- findActorName(lenderKey) ;
      initialDocuments = findDocumentsFor(lenderKey, purchaseKey) ; lender <- new Lender(lenderKey, name, initialDocuments) ;
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

  private def findDocumentsFor(roleKey: RoleKey[Long], purchaseKey: PurchaseKey) : MutableSet[ClosingDocument] = {
    try{
      val results: Iterator[Record] = (context.select (DOCUMENTS.IN_POSSESSION_OF, DOCUMENT_TYPES.DOC_TYPE)
                                        from (DOCUMENTS, DOCUMENT_TYPES)
                                        where DOCUMENTS.IN_POSSESSION_OF === ACTORS.ACTOR_ID
                                        and DOCUMENTS.PURCHASE_ID === purchaseKey.id
                                        and ACTORS.ACTOR_ID === roleKey.id
                                        and DOCUMENTS.DOCUMENT_TYPE_ID === DOCUMENT_TYPES.ID
                                        fetch).iterator().asScala

      implicit def CertificateOfInspectionAsOption[T](cert: CertificateOfInspection[T]): Option[CertificateOfInspection[T]] = Option(cert)
      implicit def CertificateOfInsuranceAsOption[T](cert: CertificateOfInsurance[T]): Option[CertificateOfInsurance[T]] = Option(cert)
      implicit def ClosingStatementAsOption[T](stmt: ClosingStatement[T]): Option[ClosingStatement[T]] = Option(stmt)
      implicit def ContractOfSaleAsOption[T](contract: ContractOfSale[T]): Option[ContractOfSale[T]] = Option(contract)
      implicit def MortgageAsOption[T](mortgage: Mortgage[T]): Option[Mortgage[T]] = Option(mortgage)
      implicit def PromissoryNoteAsOption[T](note: PromissoryNote[T]): Option[PromissoryNote[T]] = Option(note)
      implicit def TitleDeedAsOption[T](deed: TitleDeed[T]): Option[TitleDeed[T]] = Option(deed)

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
      MutableSet((documentIterator flatMap (x => x)).toList: _*)
    } catch { case e: Throwable => MutableSet.empty }
  }
}
