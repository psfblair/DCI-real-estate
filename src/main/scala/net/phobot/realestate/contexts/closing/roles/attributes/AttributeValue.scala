package net.phobot.realestate.contexts.closing.roles.attributes

import org.jooq.{Record, TableField}

// TODO: Implementation-specific
case class AttributeValue[RecordType <: Record, ValueType](val keyField: TableField[RecordType, ValueType], val value: ValueType)
