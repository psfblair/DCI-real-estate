package net.phobot.realestate.dataaccess

import java.sql.DriverManager
import org.jooq.impl.{UpdatableRecordImpl, DSL}
import org.jooq._
import org.jooq.scala.Conversions._
import DatabaseResultConversions._

class Repository {
  // When done for real, manage exceptions with opening connection, connection pooling, etc.
  Class.forName("org.h2.Driver").newInstance()
  private val connection = DriverManager.getConnection ("jdbc:h2:~/Documents/workspace-IDEA/Scala-RealEstateClosing/test")
  protected val context = DSL.using(connection, SQLDialect.H2)

  def findRecordForEntity[RecordType <: UpdatableRecordImpl[RecordType], IdType]
                (recordIdentifier: RecordIdentifier[RecordType, IdType]): Option[Record] = {

    val fieldIdentifier = recordIdentifier.keyField.asInstanceOf[TableField[RecordType, IdType]] //GRRR
    try {
      // Implicit conversion to Option in asOption above
      context select () from recordIdentifier.table where fieldIdentifier === recordIdentifier.key.id fetchOne()
    } catch {
      case e: Throwable => None
    }
  }

  def attributeValueFor[RecordType <: UpdatableRecordImpl[RecordType], ValueType]
        (record: Record, fieldIdentifier: TableField[RecordType, ValueType]): AttributeValue[RecordType, ValueType] = {
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