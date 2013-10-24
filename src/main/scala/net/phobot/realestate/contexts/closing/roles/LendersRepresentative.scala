package net.phobot.realestate.contexts.closing.roles

import net.phobot.realestate.dataaccess.RoleKey
import net.phobot.realestate.contexts.closing.roles.payments.{PaymentType, CertifiedCheck}
import net.phobot.realestate.contexts.closing.roles.documents.ClosingDocument

class LendersRepresentative(val entityId: LendersRepresentativeKey,
                            val lender: Lender,
                            val documents: Map[Class[_ <: ClosingDocument], ClosingDocument],
                            val payments: Map[PaymentType.Value, CertifiedCheck]){

}

case class LendersRepresentativeKey(private val myId: java.lang.Long) extends RoleKey[java.lang.Long] {
  def id = myId
}
