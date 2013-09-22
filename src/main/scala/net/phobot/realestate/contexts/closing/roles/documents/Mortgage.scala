package net.phobot.realestate.contexts.closing.roles.documents

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.attributes.Signature
import net.phobot.realestate.contexts.closing.roles.{Lender, Buyer}

case class Mortgage[RoleKeyType](val key: MortgageKey, var inThePossessionOf: RoleKey[RoleKeyType]) extends ClosingDocument {
   private var _buyerSignature: Option[Signature[Buyer]] = None
   private var _lenderSignature: Option[Signature[Lender]] = None

   def buyerSignature_= (signature: Signature[Buyer]) = { _buyerSignature = _buyerSignature.setOnlyOnce(signature) }
   def buyerSignature = _buyerSignature match { case Some(signature) => signature; case None => throw new IllegalStateException }

   def lenderSignature_= (signature: Signature[Lender]) = { _lenderSignature = _lenderSignature.setOnlyOnce(signature) }
   def lenderSignature = _lenderSignature match { case Some(signature) => signature; case None => throw new IllegalStateException }
 }
case class MortgageKey(private val myId: Long) extends RoleKey[Long] { def id = myId }
