package net.phobot.realestate.contexts.closing.roles.payments

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.attributes.Money

// Maybe could have something like a "payment for [purpose]" if it were useful.
class CertifiedCheck(key: CertifiedCheckKey, var inThePossessionOf: RoleKey[Any]) {
  private var _amount: Option[Money] = None
  def amount_= (option: Option[Money]) = option match { case Some(amt) => _amount = _amount.setOnlyOnce(amt) }
  def amount = _amount match { case Some(amt) => amt; case None => throw new IllegalStateException }

  private var _payableTo: Option[RoleKey[Any]] = None
  def payableTo_= (option: Option[RoleKey[Any]]) = option match { case Some(actor) => _payableTo = _payableTo.setOnlyOnce(actor) }
  def payableTo = _payableTo match { case Some(actor) => actor; case None => throw new IllegalStateException }

  private var _belongsTo: Option[RoleKey[Any]] = None
  def belongsTo_= (option: Option[RoleKey[Any]]) = option match { case Some(actor) => _belongsTo = _belongsTo.setOnlyOnce(actor) }
  def belongsTo = _belongsTo match { case Some(actor) => actor; case None => throw new IllegalStateException }

  private var _inPossessionOf: Option[RoleKey[Any]] = None
  def inPossessionOf_= (option: Option[RoleKey[Any]]) = option match { case Some(person) => _inPossessionOf = _inPossessionOf.setOnlyOnce(person) }
  def inPossessionOf = _inPossessionOf match { case Some(person) => person; case None => throw new IllegalStateException }
}
case class CertifiedCheckKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] { def id = myId }
