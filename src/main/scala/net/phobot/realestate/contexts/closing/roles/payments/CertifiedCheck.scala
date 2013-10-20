package net.phobot.realestate.contexts.closing.roles.payments

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.attributes.Money

object PaymentType extends Enumeration {
  val DownPayment = Value("DOWN_PAYMENT")
  val LenderFees = Value("LENDER_FEES")
  val AttorneyFees = Value("ATTORNEY_FEES")
  val ClosingCosts = Value("CLOSING_COSTS")
  val BrokerFees = Value("BROKER_FEES")
  val Taxes = Value("TAXES")
  val LoanProceeds = Value("LOAN_PROCEEDS")
}

class CertifiedCheck(key: CertifiedCheckKey, var inThePossessionOf: RoleKey[Any]) {
  private var _amount: Option[Money] = None
  def amount_= (option: Option[Money]) = option match { case Some(amt) => _amount = _amount.setOnlyOnce(amt); case None => () }
  def amount = _amount match { case Some(amt) => amt; case None => throw new IllegalStateException }

  private var _payableTo: Option[RoleKey[Any]] = None
  def payableTo_= (option: Option[RoleKey[Any]]) = option match { case Some(actor) => _payableTo = _payableTo.setOnlyOnce(actor); case None => () }
  def payableTo = _payableTo match { case Some(actor) => actor; case None => throw new IllegalStateException }

  private var _belongsTo: Option[RoleKey[Any]] = None
  def belongsTo_= (option: Option[RoleKey[Any]]) = option match { case Some(actor) => _belongsTo = _belongsTo.setOnlyOnce(actor); case None => () }
  def belongsTo = _belongsTo match { case Some(actor) => actor; case None => throw new IllegalStateException }

  private var _inPossessionOf: Option[RoleKey[Any]] = None
  def inPossessionOf_= (option: Option[RoleKey[Any]]) = option match { case Some(person) => _inPossessionOf = _inPossessionOf.setOnlyOnce(person); case None => () }
  def inPossessionOf = _inPossessionOf match { case Some(person) => person; case None => throw new IllegalStateException }

  private var _paymentFor: Option[PaymentType.Value] = None
  def paymentFor_= (option: Option[PaymentType.Value]) = option match { case Some(purpose) => _paymentFor = _paymentFor.setOnlyOnce(purpose); case None => () }
  def paymentFor = _paymentFor match { case Some(purpose) => purpose; case None => throw new IllegalStateException }
}
case class CertifiedCheckKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] { def id = myId }
