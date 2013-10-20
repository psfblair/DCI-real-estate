package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.payments.CertifiedCheck
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument

class LendersRepresentative(val entityId: LendersRepresentativeKey,
                            val lender: Lender,
                            val documents: Map[Class[_ <: ClosingDocument], ClosingDocument],
                            val payments: Map[String, CertifiedCheck]){
//  def paymentFor(purpose: String): Option[CertifiedCheck] = _payments.map((paymentMap) => paymentMap(purpose))

}

case class LendersRepresentativeKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}

// val documents: Map[Class[_ <: ClosingDocument], ClosingDocument] = Map()
// val foo = Mortgage("a")
// documents + (foo.getClass -> foo)
// bar(foo.getClass)