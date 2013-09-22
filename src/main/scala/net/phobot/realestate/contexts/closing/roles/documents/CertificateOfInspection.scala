package net.phobot.realestate.contexts.closing.roles.documents

import net.phobot.realestate.dataaccess.RoleKey

case class CertificateOfInspection[RoleKeyType](val key: CertificateOfInspectionKey, var inThePossessionOf: RoleKey[RoleKeyType]) extends ClosingDocument
case class CertificateOfInspectionKey(private val myId: Long) extends RoleKey[Long] { def id = myId }
