package net.phobot.realestate.contexts.closing.roles.attributes

import net.phobot.realestate.dataaccess.RoleKey
import java.util.Date

case class Signature[Role](signer: RoleKey[Role], date: Date)
