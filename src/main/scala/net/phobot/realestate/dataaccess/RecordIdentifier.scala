package net.phobot.realestate.dataaccess

import scala.language.existentials
import org.jooq.impl.{TableImpl, UpdatableRecordImpl}
import org.jooq.TableField

case class RecordIdentifier[RecordType <: UpdatableRecordImpl[RecordType], IdType]
   (val table: TableImpl[RecordType],
    val keyField: TableField[RecordType, _ <: Any],
    val key: RoleKey[IdType])
