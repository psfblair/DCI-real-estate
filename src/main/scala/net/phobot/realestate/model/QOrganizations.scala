package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QOrganizations extends QOrganizations("qOrganizations") {
  override def as(variable: String) = new QOrganizations(variable)
  
}

class QOrganizations(md: PathMetadata[_]) extends RelationalPathImpl[QOrganizations](md, "PUBLIC", "ORGANIZATIONS") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val actorId = createNumber[Long]("ACTOR_ID")

  val name = createString("NAME")

  val constraint3c: PrimaryKey[QOrganizations] = createPrimaryKey(actorId)

  val constraintD: ForeignKey[QActors] = createForeignKey(actorId, "ACTOR_ID")

  val _constraint8: ForeignKey[QTitleCompanies] = createInvForeignKey(actorId, "ORGANIZATION_ID")

}

