package net.phobot.realestate.contexts.closing.roles.documents

import net.phobot.realestate.dataaccess.RoleKey

case class CertificateOfInsurance[RoleKeyType](val key: CertificateOfInsuranceKey, var inThePossessionOf: RoleKey[RoleKeyType]) extends ClosingDocument
case class CertificateOfInsuranceKey(private val myId: Long) extends RoleKey[Long] { def id = myId }
