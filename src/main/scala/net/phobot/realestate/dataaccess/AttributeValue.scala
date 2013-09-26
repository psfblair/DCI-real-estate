package net.phobot.realestate.dataaccess

import org.jooq.TableField
import org.jooq.Record

trait AttributeValue[ValueType] {
  def underlyingValue : ValueType
}

trait AttributeWithOptionalValue[ValueType, OptionType <: Option[ValueType]] extends AttributeValue[OptionType] {
  def convertToOptionalAttributeValue : Option[AttributeValue[ValueType]]
}

case class JOOQAttributeValue[RecordType <: Record, RecordKeyType, ValueType]
              ( val recordIdentifier: JOOQRecordIdentifier[RecordType, RecordKeyType],
                val keyField: TableField[RecordType, ValueType],
                val value: ValueType) extends AttributeValue[ValueType] {

  override def underlyingValue : ValueType = value
}

case class JOOQAttributeOptionalValue[RecordType <: Record, RecordKeyType, ValueType]
              ( val recordIdentifier: JOOQRecordIdentifier[RecordType, RecordKeyType],
                val keyField: TableField[RecordType, ValueType],
                val value: Option[ValueType])
              extends AttributeWithOptionalValue[ValueType, Option[ValueType]] {

  override def underlyingValue : Option[ValueType] = value

  override def convertToOptionalAttributeValue : Option[AttributeValue[ValueType]] = {
    this.underlyingValue match {
      case Some(rawValue) => {
        val newAttribute: AttributeValue[ValueType] = new JOOQAttributeValue[RecordType, RecordKeyType, ValueType](recordIdentifier, keyField, rawValue)
        Some(newAttribute)
      }
      case None => None
    }
  }
}