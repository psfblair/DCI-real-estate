package net.phobot.realestate.contexts.closing

import _root_.scala.collection.JavaConversions
import net.phobot.realestate.contexts.closing.roles._
import net.phobot.realestate.model.tables.Actors.ACTORS
import net.phobot.realestate.model.tables.Individuals.INDIVIDUALS
import net.phobot.realestate.model.tables.Organizations.ORGANIZATIONS
import java.sql.DriverManager
import org.jooq._
import org.jooq.scala.Conversions._
import org.jooq.impl.{UpdatableRecordImpl, TableImpl, DSL}
import net.phobot.realestate.contexts.closing.roles.BuyerKey
import net.phobot.realestate.contexts.closing.roles.SellerKey
import net.phobot.realestate.contexts.closing.roles.attributes.Name

abstract class RoleKey[IdType](val id: IdType)

trait Repository {
  // When done for real, manage exceptions with opening connection, connection pooling, etc.
  Class.forName("org.h2.Driver").newInstance()
  private val connection = DriverManager.getConnection ("jdbc:h2:~/Documents/workspace-IDEA/Scala-RealEstateClosing/test")
  private val context = DSL.using(connection, SQLDialect.H2)
  private var entities : Map[RoleKey[_], Record] = Map()
  private def addToCache[IdType](aKey: RoleKey[IdType]) (record: Record) = { entities += aKey -> record ; record }

  implicit def asOption(record : Record): Option[Record] = Option(record)

  def find(key: BuyerKey) : Option[Buyer] = {

    val actorRecord: Option[Record] <- findRecordForEntity(ACTORS, ACTORS.ACTOR_ID, key, ACTORS.ACTOR_ID)
    actorRecord match {
      case None => None
      case Some(record) =>
        val individualRecord: Option[Record] = findRecordForEntity(INDIVIDUALS, INDIVIDUALS.ACTOR_ID, key,
                                                                   INDIVIDUALS.FIRST_NAME, INDIVIDUALS.LAST_NAME)
        individualRecord match {
          case Some(record) => new Name(record.getValue(INDIVIDUALS.FIRST_NAME), record.getValue(INDIVIDUALS.LAST_NAME))
          case None => findRecordForEntity( ORGANIZATIONS,
                                                  ORGANIZATIONS.ACTOR_ID,
                                                  key,
                                                  ORGANIZATIONS.ACTOR_ID, ORGANIZATIONS.NAME)
        }


    }

    )
    match  {
      case None => None
      case Some(record) =>
    }

    val individualInfo: Option[Record] =

    individualInfo match {
      case None => findRecordForEntity( ORGANIZATIONS,
                                        ORGANIZATIONS.ACTOR_ID,
                                        key,
                                        ORGANIZATIONS.ACTOR_ID, ORGANIZATIONS.NAME)
      case _ => None // Merge buyer with individual info adapter, merge db record in cache
    }

    buyer
  }

  def find(key: SellerKey) : Option[Seller] = {
    val createSeller = (record: Record) => new Seller(key)
    findRecordForEntity(ACTORS, ACTORS.ACTOR_ID, key, ACTORS.ACTOR_ID) map (addToCache(key _) andThen createSeller)
  }

  def findRecordForEntity[RecordType <: UpdatableRecordImpl, IdType]( table: TableImpl[RecordType],
                                                                      keyField: TableField[RecordType, IdType],
                                                                      key: RoleKey[IdType],
                                                                      fields: (_ <: Field[_])*) : Option[Record] = {
    val javaFieldCollection = JavaConversions.asJavaCollection(fields)
    try {
      // Implicit conversion to Option in asOption above
      val result: Option[Record] = context select (javaFieldCollection) from table where keyField === key.id fetchOne()
      result match {
        case None => None
        case Some(record) => addToCache(key)(record); result
      }
    } catch {
      case e => None
    }
  }

  def nameFor(aBuyer: BuyerKey) : Option[Name[String]]
  def nameFor(aSeller: SellerKey) : Option[Name[String]]

  def attorneyFor(aBuyer: BuyerKey) : Option[BuyersAttorney]
  def attorneyFor(aSeller: SellerKey) : Option[SellersAttorney]

  def clientFor(attorney: BuyersAttorney) : Option[Buyer]
  def clientFor(attorney: SellersAttorney) : Option[Seller]
  def clientFor(realEstateAgent: BuyersRealEstateAgent) : Option[Buyer]
  def clientFor(realEstateAgent: SellersRealEstateAgent) : Option[Seller]

  def representedBy(closingAgent: ClosingAgent) : Option[TitleCompany]
  def representedBy(representative: LendersRepresentative) : Option[Lender]

  def representativeFor(lender: Lender) : Option[LendersRepresentative]
  def representativeFor(lender: TitleCompany) : Option[ClosingAgent]

}

object ClosingRepository extends Repository {
  def nameFor(aBuyer: BuyerKey): Option[Name[String]] = None

  def nameFor(aSeller: SellerKey): Option[Name[String]] = None

  def attorneyFor(aBuyer: BuyerKey): Option[BuyersAttorney] = None

  def attorneyFor(aSeller: SellerKey): Option[SellersAttorney] = None

  def clientFor(attorney: BuyersAttorney): Option[Buyer] = None

  def clientFor(attorney: SellersAttorney): Option[Seller] = None

  def clientFor(realEstateAgent: BuyersRealEstateAgent): Option[Buyer] = None

  def clientFor(realEstateAgent: SellersRealEstateAgent): Option[Seller] = None

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