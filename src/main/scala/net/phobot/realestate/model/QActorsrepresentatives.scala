package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.types.PathMetadataFactory._;
import com.mysema.query.scala.sql.RelationalPathImpl
import com.mysema.query.sql._

object QActorsrepresentatives extends QActorsrepresentatives("qActorsrepresentatives") {
  override def as(variable: String) = new QActorsrepresentatives(variable)
}

class QActorsrepresentatives(md: PathMetadata[_]) extends RelationalPathImpl[QActorsrepresentatives](md, "PUBLIC", "ACTORSREPRESENTATIVES") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val actorId = createNumber[Long]("ACTOR_ID")

  val purchaseId = createNumber[Long]("PURCHASE_ID")

  val representativeId = createNumber[Long]("REPRESENTATIVE_ID")

  val constraint5a: ForeignKey[QActors] = createForeignKey(actorId, "ACTOR_ID")

  val constraint5: ForeignKey[QPurchases] = createForeignKey(purchaseId, "PURCHASE_ID")

  val constraint5a7: ForeignKey[QRepresentatives] = createForeignKey(representativeId, "ACTOR_ID")
}

