package net.phobot.realestate.contexts.closing.roles.attributes
import net.phobot.realestate.dataaccess.AttributeValue

abstract class Name {
  def fullName : String
}

case class IndividualName(firstName: AttributeValue[String],
                          lastName:  AttributeValue[String]) extends Name{

  def fullName = s"${firstName.underlyingValue} ${lastName.underlyingValue}"
}

case class OrganizationName(name: AttributeValue[String]) extends Name {
  def fullName = name.underlyingValue
}

