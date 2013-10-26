import scala.slick.driver.H2Driver.simple._
package net.phobot.realestate.model.schema.tables.base{
  import net.phobot.realestate.model.schema.entities.Actor
  abstract class Actors extends Table[Actor]("ACTORS"){
    // columns
    def actorId = column[Long]("ACTOR_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = actorId <> (Actor, Actor.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Attorney
  abstract class Attorneys extends Table[Attorney]("ATTORNEYS"){
    // columns
    def representativeId = column[Long]("REPRESENTATIVE_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = representativeId <> (Attorney, Attorney.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.CertifiedCheck
  abstract class CertifiedChecks extends Table[CertifiedCheck]("CERTIFIED_CHECKS"){
    // columns
    def id = column[Long]("ID", O.DBType("BIGINT(19)"))
    def amount = column[BigDecimal]("AMOUNT", O.DBType("DECIMAL(65535)"))
    def payableTo = column[Option[Long]]("PAYABLE_TO", O.DBType("BIGINT(19)"))
    def belongsTo = column[Option[Long]]("BELONGS_TO", O.DBType("BIGINT(19)"))
    def inPossessionOf = column[Option[Long]]("IN_POSSESSION_OF", O.DBType("BIGINT(19)"))
    def paymentFor = column[Long]("PAYMENT_FOR", O.DBType("BIGINT(19)"))
    def purchaseId = column[Long]("PURCHASE_ID", O.DBType("BIGINT(19)"))
  
    def * = id ~ amount ~ payableTo ~ belongsTo ~ inPossessionOf ~ paymentFor ~ purchaseId <> (CertifiedCheck, CertifiedCheck.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Contact
  abstract class Contacts extends Table[Contact]("CONTACTS"){
    // columns
    def email = column[Option[String]]("EMAIL", O.DBType("VARCHAR(256)"))
    def actorId = column[Option[Long]]("ACTOR_ID", O.DBType("BIGINT(19)"))
  
    def * = email ~ actorId <> (Contact, Contact.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Document
  abstract class Documents extends Table[Document]("DOCUMENTS"){
    // columns
    def id = column[Long]("ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
    def documentTypeId = column[Long]("DOCUMENT_TYPE_ID", O.DBType("BIGINT(19)"))
    def purchaseId = column[Long]("PURCHASE_ID", O.DBType("BIGINT(19)"))
    def inPossessionOf = column[Option[Long]]("IN_POSSESSION_OF", O.DBType("BIGINT(19)"))
  
    def * = id ~ documentTypeId ~ purchaseId ~ inPossessionOf <> (Document, Document.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.DocumentType
  abstract class DocumentTypes extends Table[DocumentType]("DOCUMENT_TYPES"){
    // columns
    def id = column[Long]("ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
    def docType = column[String]("DOC_TYPE", O.DBType("VARCHAR(2147483647)"))
  
    def * = id ~ docType <> (DocumentType, DocumentType.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Individual
  abstract class Individuals extends Table[Individual]("INDIVIDUALS"){
    // columns
    def firstName = column[Option[String]]("FIRST_NAME", O.DBType("VARCHAR(256)"))
    def lastName = column[Option[String]]("LAST_NAME", O.DBType("VARCHAR(256)"))
    def actorId = column[Long]("ACTOR_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = firstName ~ lastName ~ actorId <> (Individual, Individual.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Organization
  abstract class Organizations extends Table[Organization]("ORGANIZATIONS"){
    // columns
    def name = column[Option[String]]("NAME", O.DBType("VARCHAR(256)"))
    def actorId = column[Long]("ACTOR_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = name ~ actorId <> (Organization, Organization.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.PaymentType
  abstract class PaymentTypes extends Table[PaymentType]("PAYMENT_TYPES"){
    // columns
    def id = column[Long]("ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
    def paymentType = column[String]("PAYMENT_TYPE", O.DBType("VARCHAR(32)"))
  
    def * = id ~ paymentType <> (PaymentType, PaymentType.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Purchase
  abstract class Purchases extends Table[Purchase]("PURCHASES"){
    // columns
    def purchaseId = column[Long]("PURCHASE_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = purchaseId <> (Purchase, Purchase.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.RealEstateAgent
  abstract class RealEstateAgents extends Table[RealEstateAgent]("REAL_ESTATE_AGENTS"){
    // columns
    def representativeId = column[Long]("REPRESENTATIVE_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = representativeId <> (RealEstateAgent, RealEstateAgent.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Representative
  abstract class Representatives extends Table[Representative]("REPRESENTATIVES"){
    // columns
    def actorId = column[Long]("ACTOR_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = actorId <> (Representative, Representative.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.Signature
  abstract class Signatures extends Table[Signature]("SIGNATURES"){
    // columns
    def documentId = column[Long]("DOCUMENT_ID", O.DBType("BIGINT(19)"))
    def actorId = column[Long]("ACTOR_ID", O.DBType("BIGINT(19)"))
    def date = column[java.sql.Date]("DATE", O.DBType("DATE(8)"))
  
    def * = documentId ~ actorId ~ date <> (Signature, Signature.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
  import net.phobot.realestate.model.schema.entities.TitleCompany
  abstract class TitleCompanies extends Table[TitleCompany]("TITLE_COMPANIES"){
    // columns
    def organizationId = column[Long]("ORGANIZATION_ID", O.DBType("BIGINT(19)"), O.PrimaryKey)
  
    def * = organizationId <> (TitleCompany, TitleCompany.unapply _)
    
    // foreign keys
    
    
    // constraints
    
    
    
  }
}
package net.phobot.realestate.model.schema.tables{
  object Actors extends net.phobot.realestate.model.schema.tables.base.Actors
  object Attorneys extends net.phobot.realestate.model.schema.tables.base.Attorneys
  object CertifiedChecks extends net.phobot.realestate.model.schema.tables.base.CertifiedChecks
  object Contacts extends net.phobot.realestate.model.schema.tables.base.Contacts
  object Documents extends net.phobot.realestate.model.schema.tables.base.Documents
  object DocumentTypes extends net.phobot.realestate.model.schema.tables.base.DocumentTypes
  object Individuals extends net.phobot.realestate.model.schema.tables.base.Individuals
  object Organizations extends net.phobot.realestate.model.schema.tables.base.Organizations
  object PaymentTypes extends net.phobot.realestate.model.schema.tables.base.PaymentTypes
  object Purchases extends net.phobot.realestate.model.schema.tables.base.Purchases
  object RealEstateAgents extends net.phobot.realestate.model.schema.tables.base.RealEstateAgents
  object Representatives extends net.phobot.realestate.model.schema.tables.base.Representatives
  object Signatures extends net.phobot.realestate.model.schema.tables.base.Signatures
  object TitleCompanies extends net.phobot.realestate.model.schema.tables.base.TitleCompanies
}
package net.phobot.realestate.model.schema.entities{
  case class Actor( actorId :Long )
  case class Attorney( representativeId :Long )
  case class CertifiedCheck( id :Long, amount :BigDecimal, payableTo :Option[Long], belongsTo :Option[Long], inPossessionOf :Option[Long], paymentFor :Long, purchaseId :Long )
  case class Contact( email :Option[String], actorId :Option[Long] )
  case class Document( id :Long, documentTypeId :Long, purchaseId :Long, inPossessionOf :Option[Long] )
  case class DocumentType( id :Long, docType :String )
  case class Individual( firstName :Option[String], lastName :Option[String], actorId :Long )
  case class Organization( name :Option[String], actorId :Long )
  case class PaymentType( id :Long, paymentType :String )
  case class Purchase( purchaseId :Long )
  case class RealEstateAgent( representativeId :Long )
  case class Representative( actorId :Long )
  case class Signature( documentId :Long, actorId :Long, date :java.sql.Date )
  case class TitleCompany( organizationId :Long )
}