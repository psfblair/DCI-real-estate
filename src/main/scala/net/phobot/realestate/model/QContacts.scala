package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QContacts extends QContacts("qContacts") {
  override def as(variable: String) = new QContacts(variable)
  
}

class QContacts(md: PathMetadata[_]) extends RelationalPathImpl[QContacts](md, "PUBLIC", "CONTACTS") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val actorId = createNumber[Long]("ACTOR_ID")

  val email = createString("EMAIL")

  val constraintC: ForeignKey[QActors] = createForeignKey(actorId, "ACTOR_ID")

}

