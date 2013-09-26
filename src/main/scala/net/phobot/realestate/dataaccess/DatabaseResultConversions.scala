package net.phobot.realestate.dataaccess

import scala.language.implicitConversions
import org.jooq.Record

object DatabaseResultConversions {
  implicit def recordAsOption(record : Record): Option[Record] = Option(record)
  implicit def attributeValueAsOption[ValueType](attributeValue: AttributeValue[ValueType]) : Option[AttributeValue[ValueType]] = Option(attributeValue)
}
