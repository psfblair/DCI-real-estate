package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QRepresentatives extends QRepresentatives("qRepresentatives") {
  override def as(variable: String) = new QRepresentatives(variable)
  
}

class QRepresentatives(md: PathMetadata[_]) extends RelationalPathImpl[QRepresentatives](md, "PUBLIC", "REPRESENTATIVES") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val actorId = createNumber[Long]("ACTOR_ID")

  val constraint6a: PrimaryKey[QRepresentatives] = createPrimaryKey(actorId)

  val constraint6: ForeignKey[QActors] = createForeignKey(actorId, "ACTOR_ID")

  val _constraintA: ForeignKey[QAttorneys] = createInvForeignKey(actorId, "REPRESENTATIVE_ID")

  val _constraint5a7: ForeignKey[QActorsrepresentatives] = createInvForeignKey(actorId, "REPRESENTATIVE_ID")

}

