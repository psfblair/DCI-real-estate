package net.phobot.realestate.contexts.closing.roles.documents

import net.phobot.realestate.util.OptionExtensions._
import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.attributes.Signature
import net.phobot.realestate.contexts.closing.roles.{Seller, Buyer}

case class ContractOfSale[RoleKeyType](val key: ContractOfSaleKey, var inThePossessionOf: RoleKey[RoleKeyType]) extends ClosingDocument{
   private var _buyerSignature: Option[Signature[Buyer]] = None
   private var _sellerSignature: Option[Signature[Seller]] = None

   def buyerSignature_= (signature: Signature[Buyer]) = { _buyerSignature = _buyerSignature.setOnlyOnce(signature) }
   def buyerSignature = _buyerSignature match { case Some(signature) => signature; case None => throw new IllegalStateException }

   def sellerSignature_= (signature: Signature[Seller]) = { _sellerSignature = _sellerSignature.setOnlyOnce(signature) }
   def sellerSignature = _sellerSignature match { case Some(signature) => signature; case None => throw new IllegalStateException }
 }
case class ContractOfSaleKey(private val myId: Long) extends RoleKey[Long] { def id = myId }
