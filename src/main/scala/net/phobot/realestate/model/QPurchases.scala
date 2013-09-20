package net.phobot.realestate.model

import com.mysema.query.types._
import com.mysema.query.scala._

import com.mysema.query.types.PathMetadataFactory._;

import com.mysema.query.scala.sql.RelationalPathImpl

import com.mysema.query.sql._

object QPurchases extends QPurchases("qPurchases") {
  override def as(variable: String) = new QPurchases(variable)
  
}

class QPurchases(md: PathMetadata[_]) extends RelationalPathImpl[QPurchases](md, "PUBLIC", "PURCHASES") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val purchaseId = createNumber[Long]("PURCHASE_ID")

  val constraint3b: PrimaryKey[QPurchases] = createPrimaryKey(purchaseId)

  val _constraint5: ForeignKey[QActorsrepresentatives] = createInvForeignKey(purchaseId, "PURCHASE_ID")

}

