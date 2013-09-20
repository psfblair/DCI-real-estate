package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QIndividuals extends QIndividuals("qIndividuals") {
  override def as(variable: String) = new QIndividuals(variable)
  
}

class QIndividuals(md: PathMetadata[_]) extends RelationalPathImpl[QIndividuals](md, "PUBLIC", "INDIVIDUALS") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val actorId = createNumber[Long]("ACTOR_ID")

  val firstName = createString("FIRST_NAME")

  val lastName = createString("LAST_NAME")

  val constraint2: PrimaryKey[QIndividuals] = createPrimaryKey(actorId)

  val constraint1: ForeignKey[QActors] = createForeignKey(actorId, "ACTOR_ID")
}

