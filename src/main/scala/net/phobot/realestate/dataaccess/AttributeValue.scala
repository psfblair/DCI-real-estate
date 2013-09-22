package net.phobot.realestate.dataaccess

import org.jooq.{Record, TableField}

// TODO: Implementation-specific
case class AttributeValue[RecordType <: Record, ValueType](val keyField: TableField[RecordType, ValueType], val value: ValueType)
