package net.phobot.realestate.dataaccess

import scala.language.existentials

import org.jooq.impl.TableImpl
import org.jooq.{Record, TableField}

trait RecordIdentifier {}

case class JOOQRecordIdentifier[RecordType <: Record, IdType] ( table: TableImpl[RecordType],
                                                                keyField: TableField[RecordType, _ <: Any],
                                                                key: RoleKey[IdType])   extends RecordIdentifier
