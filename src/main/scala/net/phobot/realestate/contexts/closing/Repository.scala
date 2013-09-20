package net.phobot.realestate.contexts.closing

import net.phobot.realestate.contexts.closing.roles._
import com.mysema.query.sql.{RelationalPath, H2Templates, SQLQuery}
import com.mysema.query.scala.Helpers._
import java.sql.DriverManager
import net.phobot.realestate.model.QActors
import com.mysema.query.scala.sql.RelationalPathImpl

trait Repository {
  Class.forName("org.h2.Driver").newInstance()
  val connection = DriverManager.getConnection ("jdbc:h2:~/Documents/workspace-IDEA/Scala-RealEstateClosing/test")

  def find(key: BuyerKey) : Option[Buyer] = {
    val query = new SQLQuery(connection, new H2Templates())
    val option: Option[Long] = query.from(QActors).where(QActors.actorId === key.id).unique(QActors.all())
    option map ((theId) => new Buyer(new BuyerKey(theId)))
  }

  def nameFor(aBuyer: BuyerKey) : Option[Name[String]]
  def nameFor(aSeller: SellerKey) : Option[Name[String]]

  def attorneyFor(aBuyer: BuyerKey) : Option[BuyersAttorney]
  def attorneyFor(aSeller: SellerKey) : Option[SellersAttorney]

  def clientFor(attorney: BuyersAttorney) : Option[Buyer]
  def clientFor(attorney: SellersAttorney) : Option[Seller]
  def clientFor(realEstateAgent: BuyersRealEstateAgent) : Option[Buyer]
  def clientFor(realEstateAgent: SellersRealEstateAgent) : Option[Seller]

  def representedBy(closingAgent: ClosingAgent) : Option[TitleCompany]
  def representedBy(representative: LendersRepresentative) : Option[Lender]

  def representativeFor(lender: Lender) : Option[LendersRepresentative]
  def representativeFor(lender: TitleCompany) : Option[ClosingAgent]

}

object ClosingRepository extends Repository {
  def nameFor(aBuyer: BuyerKey): Option[Name[String]] = None

  def nameFor(aSeller: SellerKey): Option[Name[String]] = None

  def attorneyFor(aBuyer: BuyerKey): Option[BuyersAttorney] = None

  def attorneyFor(aSeller: SellerKey): Option[SellersAttorney] = None

  def clientFor(attorney: BuyersAttorney): Option[Buyer] = None

  def clientFor(attorney: SellersAttorney): Option[Seller] = None

  def clientFor(realEstateAgent: BuyersRealEstateAgent): Option[Buyer] = None

  def clientFor(realEstateAgent: SellersRealEstateAgent): Option[Seller] = None

  def representedBy(closingAgent: ClosingAgent): Option[TitleCompany] = None

  def representedBy(representative: LendersRepresentative): Option[Lender] = None

  def representativeFor(lender: Lender): Option[LendersRepresentative] = None

  def representativeFor(lender: TitleCompany): Option[ClosingAgent] = None
}