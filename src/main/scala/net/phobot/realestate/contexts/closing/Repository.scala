package net.phobot.realestate.contexts.closing

import _root_.scala.Some
import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.model.tables.Actors.ACTORS
import net.phobot.realestate.model.tables.Individuals.INDIVIDUALS
import net.phobot.realestate.model.tables.Organizations.ORGANIZATIONS
import net.phobot.realestate.model.tables.Attorneys.ATTORNEYS
import net.phobot.realestate.model.tables.Actorsrepresentatives.ACTORSREPRESENTATIVES
import java.sql.DriverManager
import org.jooq._
import org.jooq.scala.Conversions._
import org.jooq.impl.{UpdatableRecordImpl, TableImpl, DSL}
import net.phobot.realestate.contexts.closing.roles.attributes.{Name => NameAttribute, AttributeValue, OrganizationName, IndividualName}
import net.phobot.realestate.contexts.closing.roles.BuyerKey
import net.phobot.realestate.contexts.closing.roles.SellerKey
import net.phobot.realestate.model.tables.Individuals

abstract class RoleKey[IdType] {
  def id : IdType
}

case class RecordIdentifier[RecordType <: UpdatableRecordImpl[RecordType], IdType]
   (val table: TableImpl[RecordType],
    val keyField: TableField[RecordType, _ <: Any],
    val key: RoleKey[IdType])

trait Repository {
  // When done for real, manage exceptions with opening connection, connection pooling, etc.
  Class.forName("org.h2.Driver").newInstance()
  private val connection = DriverManager.getConnection ("jdbc:h2:~/Documents/workspace-IDEA/Scala-RealEstateClosing/test")
  private val context = DSL.using(connection, SQLDialect.H2)

  implicit def asOption(record : Record): Option[Record] = Option(record)
  implicit def asOption(buyer: Buyer): Option[Buyer] = Option(buyer)
  implicit def asOption(seller: Seller): Option[Seller] = Option(seller)
  implicit def asOption[RecordType <: Record, ValueType](attributeValue: AttributeValue[RecordType, ValueType]) : Option[AttributeValue[RecordType, ValueType]] = Option(attributeValue)

  def findBuyer(buyerKey: BuyerKey, purchaseKey: PurchaseKey) : Option[Buyer] = {
    for (
      actor <- findRecordForEntity(RecordIdentifier(ACTORS, ACTORS.ACTOR_ID, buyerKey)) ;
      name <- findActorName(buyerKey) ;
      buyer <- new Buyer(buyerKey, name) ;
      attorney <- findBuyerAttorney(buyer, purchaseKey)
    ) yield {
      buyer.attorney = attorney
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
      seller
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
                from(ATTORNEYS)
                where(ATTORNEYS.REPRESENTATIVE_ID === ACTORSREPRESENTATIVES.REPRESENTATIVE_ID)
                and(ACTORSREPRESENTATIVES.PURCHASE_ID === purchaseKey.id)
                and(ACTORSREPRESENTATIVES.ACTOR_ID === key.id)
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


  def representedBy(closingAgent: ClosingAgent) : Option[TitleCompany]
  def representedBy(representative: LendersRepresentative) : Option[Lender]

  def representativeFor(lender: Lender) : Option[LendersRepresentative]
  def representativeFor(lender: TitleCompany) : Option[ClosingAgent]

}

object ClosingRepository extends Repository {

  def representedBy(closingAgent: ClosingAgent): Option[TitleCompany] = None

  def representedBy(representative: LendersRepresentative): Option[Lender] = None

  def representativeFor(lender: Lender): Option[LendersRepresentative] = None

  def representativeFor(lender: TitleCompany): Option[ClosingAgent] = None
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