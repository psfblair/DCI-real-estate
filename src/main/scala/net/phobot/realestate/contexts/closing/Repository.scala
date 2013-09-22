package net.phobot.realestate.contexts.closing

import scala.language.implicitConversions
import scala.language.postfixOps
import scala.language.existentials

import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.model.tables.Actors.ACTORS
import net.phobot.realestate.model.tables.Individuals.INDIVIDUALS
import net.phobot.realestate.model.tables.Organizations.ORGANIZATIONS
import net.phobot.realestate.model.tables.Attorneys.ATTORNEYS
import net.phobot.realestate.model.tables.ActorsRepresentatives.ACTORS_REPRESENTATIVES
import net.phobot.realestate.model.tables.Representatives.REPRESENTATIVES
import net.phobot.realestate.model.tables.RealEstateAgents.REAL_ESTATE_AGENTS
import net.phobot.realestate.model.tables.Notaries.NOTARIES
import java.sql.DriverManager
import org.jooq._
import org.jooq.scala.Conversions._
import org.jooq.impl.{UpdatableRecordImpl, TableImpl, DSL}
import net.phobot.realestate.contexts.closing.roles.attributes.{Name => NameAttribute, AttributeValue, OrganizationName, IndividualName}
import net.phobot.realestate.contexts.closing.roles.BuyerKey
import net.phobot.realestate.contexts.closing.roles.SellerKey
import net.phobot.realestate.contexts.closing.RoleKeyConversions._
import net.phobot.realestate.contexts.closing.DatabaseResultConversions._

abstract class RoleKey[IdType] {
  def id : IdType
}
object RoleKeyConversions {
  implicit def asPurchaseKey(purchaseId: Long): PurchaseKey                         = PurchaseKey(purchaseId)
  implicit def asBuyerKey(buyerId: Long): BuyerKey                                  = BuyerKey(buyerId)
  implicit def asSellerKey(sellerId: Long): SellerKey                               = SellerKey(sellerId)
  implicit def asLenderKey(lenderId: Long): LenderKey                               = LenderKey(lenderId)
  implicit def asTitleCompanyKey(companyId: Long): TitleCompanyKey                  = TitleCompanyKey(companyId)
  implicit def asNotaryPublicKey(notaryId: Long): NotaryPublicKey                   = NotaryPublicKey(notaryId)

  implicit def asBuyersAttorneyKey(attorneyId: java.lang.Long): BuyersAttorneyKey             = BuyersAttorneyKey(attorneyId)
  implicit def asSellersAttorneyKey(attorneyId: java.lang.Long): SellersAttorneyKey           = SellersAttorneyKey(attorneyId)
  implicit def asBuyersRealEstateAgentKey(repId: java.lang.Long): BuyersRealEstateAgentKey    = BuyersRealEstateAgentKey(repId)
  implicit def asSellersRealEstateAgentKey(repId: java.lang.Long): SellersRealEstateAgentKey  = SellersRealEstateAgentKey(repId)
  implicit def asLendersRepresentativeKey(repId: java.lang.Long): LendersRepresentativeKey    = LendersRepresentativeKey(repId)
  implicit def asClosingAgentKey(repId: java.lang.Long): ClosingAgentKey                      = ClosingAgentKey(repId)
}

object DatabaseResultConversions {
  implicit def asOption(record : Record): Option[Record] = Option(record)
  implicit def asOption[RecordType <: Record, ValueType](attributeValue: AttributeValue[RecordType, ValueType]) : Option[AttributeValue[RecordType, ValueType]] = Option(attributeValue)

  implicit def asOption(buyer: Buyer): Option[Buyer] = Option(buyer)
  implicit def asOption(seller: Seller): Option[Seller] = Option(seller)
  implicit def asOption(lender: Lender): Option[Lender] = Option(lender)
  implicit def asOption(company: TitleCompany): Option[TitleCompany] = Option(company)
}

case class RecordIdentifier[RecordType <: UpdatableRecordImpl[RecordType], IdType]
   (val table: TableImpl[RecordType],
    val keyField: TableField[RecordType, _ <: Any],
    val key: RoleKey[IdType])

object Repository {
  // When done for real, manage exceptions with opening connection, connection pooling, etc.
  Class.forName("org.h2.Driver").newInstance()
  private val connection = DriverManager.getConnection ("jdbc:h2:~/Documents/workspace-IDEA/Scala-RealEstateClosing/test")
  private val context = DSL.using(connection, SQLDialect.H2)

  def findBuyer(buyerKey: BuyerKey, purchaseKey: PurchaseKey) : Option[Buyer] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, buyerKey)) ;
      name <- findActorName(buyerKey) ;
      buyer <- new Buyer(buyerKey, name) ;
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

  def findNotary(notaryPublicKey: NotaryPublicKey) : Option[NotaryPublic] = {
    for (
        actor <- findRecordForEntity(RecordIdentifier(NOTARIES, NOTARIES.INDIVIDUAL_ID, notaryPublicKey)) ;
        name <- findActorName(notaryPublicKey)
    ) yield new NotaryPublic(notaryPublicKey, name)
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

  private def findRecordForEntity[RecordType <: UpdatableRecordImpl[RecordType], IdType]
                (recordIdentifier: RecordIdentifier[RecordType, IdType]): Option[Record] = {
    val fieldIdentifier = recordIdentifier.keyField.asInstanceOf[TableField[RecordType, IdType]] //GRRR
    try {
      // Implicit conversion to Option in asOption above
      context select () from recordIdentifier.table where fieldIdentifier === recordIdentifier.key.id fetchOne()
    } catch {
      case e: Throwable => None
    }
  }

  private def attributeValueFor[RecordType <: UpdatableRecordImpl[RecordType], ValueType]
                               (record: Record,
                                fieldIdentifier: TableField[RecordType, ValueType]): AttributeValue[RecordType, ValueType] = {
    AttributeValue(fieldIdentifier, record.getValue(fieldIdentifier))
  }
}

/* JOOQ broken down:

    val selectStep: SelectSelectStep[Record]        = context select()
    val joinStep: SelectJoinStep[Record]            = selectStep from(ACTORS)
    val condition: Condition                        = ACTORS.ACTOR_ID === key.id
    val conditionStep: SelectConditionStep[Record]  = joinStep where condition
    val record1: Record                             = conditionStep fetchOne()
    val result: Result[Record]                      = conditionStep fetch()
    val list: util.List[Result[Record]]             = conditionStep fetchMany()

 */
/* JOOQ Scala example:

    val x = ACTORS as "x"                                            // SQL-esque table aliasing

    for (record <- context                                           // Iteration over Result. "r" is an org.jooq.Record
        select (
          INDIVIDUALS.ACTOR_ID * INDIVIDUALS.ACTOR_ID,               // Using the overloaded "*" operator
          INDIVIDUALS.ACTOR_ID + INDIVIDUALS.ACTOR_ID * 3 + 4,       // Using the overloaded "+" operator
          INDIVIDUALS.FIRST_NAME || " abc" || " xy"                  // Using the overloaded "||" operator
        )
        from INDIVIDUALS                                             // No need to use parentheses or "." here
        leftOuterJoin (
          context select (x.ACTOR_ID)                                // Dereference fields from aliased table
          from x
          limit 1
          asTable x.getName()
        )
        on INDIVIDUALS.ACTOR_ID === x.ACTOR_ID                       // Using the overloaded "===" operator
        where (INDIVIDUALS.ACTOR_ID <> 2)                            // Using the olerloaded "<>" operator
        or (INDIVIDUALS.FIRST_NAME in ("Alquimista", "Brida"))       // Neat IN predicate expression
        fetch
    ) { println(record) }
*/