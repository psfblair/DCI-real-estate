package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QAttorneys extends QAttorneys("qAttorneys") {
  override def as(variable: String) = new QAttorneys(variable)
  
}

class QAttorneys(md: PathMetadata[_]) extends RelationalPathImpl[QAttorneys](md, "PUBLIC", "ATTORNEYS") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val representativeId = createNumber[Long]("REPRESENTATIVE_ID")

  val constraintA2: PrimaryKey[QAttorneys] = createPrimaryKey(representativeId)

  val constraintA: ForeignKey[QRepresentatives] = createForeignKey(representativeId, "ACTOR_ID")

}

