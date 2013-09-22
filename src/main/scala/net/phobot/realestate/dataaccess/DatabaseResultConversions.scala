package net.phobot.realestate.dataaccess

import scala.language.implicitConversions
import org.jooq.Record

object DatabaseResultConversions {
  implicit def recordAsOption(record : Record): Option[Record] = Option(record)
  implicit def attributeValueAsOption[RecordType <: Record, ValueType](attributeValue: AttributeValue[RecordType, ValueType]) : Option[AttributeValue[RecordType, ValueType]] = Option(attributeValue)
}
