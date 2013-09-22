package net.phobot.realestate.contexts.closing.roles.attributes
import org.jooq.impl.UpdatableRecordImpl
import net.phobot.realestate.dataaccess.{AttributeValue, RecordIdentifier}

abstract class Name {
  def fullName : String
}

// TODO: Implementation-specific
case class IndividualName[RecordType <: UpdatableRecordImpl[RecordType], IdType]
                          (recordIdentifier: RecordIdentifier[RecordType, IdType],
                           firstName: AttributeValue[RecordType, String],
                           lastName: AttributeValue[RecordType, String]) extends Name{

  def fullName = s"${firstName.value} ${lastName.value}"
}

// TODO: Implementation-specific
case class OrganizationName[RecordType <: UpdatableRecordImpl[RecordType], IdType]
                            (recordIdentifier: RecordIdentifier[RecordType, IdType],
                             name: AttributeValue[RecordType, String]) extends Name {
  def fullName = name.value
}

