package net.phobot.realestate.contexts.closing.roles.attributes

import net.phobot.realestate.dataaccess.AttributeValue

//In real code, probably would use scala BigDecimal and have some converter underneath at the JOOQ level.
trait Money {
  def amount: java.math.BigDecimal
}

case class MoneyImpl(moneyAmount: AttributeValue[java.math.BigDecimal]) extends Money {
  def amount: java.math.BigDecimal = moneyAmount.underlyingValue
}
