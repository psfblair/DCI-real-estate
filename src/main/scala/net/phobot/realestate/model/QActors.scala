package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.types.PathMetadataFactory._;
import com.mysema.query.scala.sql.RelationalPathImpl
import com.mysema.query.sql._

object QActors extends QActors("qActors") {
  override def as(variable: String) = new QActors(variable)
}

class QActors(md: PathMetadata[_]) extends RelationalPathImpl[QActors](md, "PUBLIC", "ACTORS") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val actorId = createNumber[Long]("ACTOR_ID")

  val constraint3: PrimaryKey[QActors] = createPrimaryKey(actorId)

  val _constraint1: ForeignKey[QIndividuals] = createInvForeignKey(actorId, "ACTOR_ID")

  val _constraint5a: ForeignKey[QActorsrepresentatives] = createInvForeignKey(actorId, "ACTOR_ID")

  val _constraintD: ForeignKey[QOrganizations] = createInvForeignKey(actorId, "ACTOR_ID")

  val _constraintC: ForeignKey[QContacts] = createInvForeignKey(actorId, "ACTOR_ID")

  val _constraint6: ForeignKey[QRepresentatives] = createInvForeignKey(actorId, "ACTOR_ID")
}

