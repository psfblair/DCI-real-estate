/**
 * This class is generated by jOOQ
 */
package net.phobot.realestate.model;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 883148930;

	/**
	 * The singleton instance of <code>PUBLIC</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("PUBLIC");
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			net.phobot.realestate.model.tables.Actors.ACTORS,
			net.phobot.realestate.model.tables.ActorsRepresentatives.ACTORS_REPRESENTATIVES,
			net.phobot.realestate.model.tables.Attorneys.ATTORNEYS,
			net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS,
			net.phobot.realestate.model.tables.PaymentTypes.PAYMENT_TYPES,
			net.phobot.realestate.model.tables.Contacts.CONTACTS,
			net.phobot.realestate.model.tables.Documents.DOCUMENTS,
			net.phobot.realestate.model.tables.DocumentTypes.DOCUMENT_TYPES,
			net.phobot.realestate.model.tables.Individuals.INDIVIDUALS,
			net.phobot.realestate.model.tables.Organizations.ORGANIZATIONS,
			net.phobot.realestate.model.tables.Purchases.PURCHASES,
			net.phobot.realestate.model.tables.RealEstateAgents.REAL_ESTATE_AGENTS,
			net.phobot.realestate.model.tables.Representatives.REPRESENTATIVES,
			net.phobot.realestate.model.tables.Signatures.SIGNATURES,
			net.phobot.realestate.model.tables.TitleCompanies.TITLE_COMPANIES);
	}
}
