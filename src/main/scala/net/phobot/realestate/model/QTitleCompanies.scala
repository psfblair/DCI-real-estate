package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QTitleCompanies extends QTitleCompanies("qTitleCompanies") {
  override def as(variable: String) = new QTitleCompanies(variable)
  
}

class QTitleCompanies(md: PathMetadata[_]) extends RelationalPathImpl[QTitleCompanies](md, "PUBLIC", "TITLE_COMPANIES") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val organizationId = createNumber[Long]("ORGANIZATION_ID")

  val constraint8a: PrimaryKey[QTitleCompanies] = createPrimaryKey(organizationId)

  val constraint8: ForeignKey[QOrganizations] = createForeignKey(organizationId, "ACTOR_ID")

}

