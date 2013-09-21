package net.phobot.realestate.contexts.closing.roles.attributes
import net.phobot.realestate.contexts.closing.RecordIdentifier
import org.jooq.impl.UpdatableRecordImpl

abstract class Name {
  def fullName : String
}
case class IndividualName[RecordType <: UpdatableRecordImpl[RecordType], IdType]
                          (recordIdentifier: RecordIdentifier[RecordType, IdType],
                           firstName: AttributeValue[RecordType, String],
                           lastName: AttributeValue[RecordType, String]) extends Name{

  def fullName = s"${firstName.value} ${lastName.value}"
}

case class OrganizationName[RecordType <: UpdatableRecordImpl[RecordType], IdType]
                            (recordIdentifier: RecordIdentifier[RecordType, IdType],
                             name: AttributeValue[RecordType, String]) extends Name {
  def fullName = name.value
}

