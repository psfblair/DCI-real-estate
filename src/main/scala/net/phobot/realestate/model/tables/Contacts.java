/**
 * This class is generated by jOOQ
 */
package net.phobot.realestate.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Contacts extends org.jooq.impl.TableImpl<net.phobot.realestate.model.tables.records.ContactsRecord> {

	private static final long serialVersionUID = 619705510;

	/**
	 * The singleton instance of <code>PUBLIC.CONTACTS</code>
	 */
	public static final net.phobot.realestate.model.tables.Contacts CONTACTS = new net.phobot.realestate.model.tables.Contacts();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.phobot.realestate.model.tables.records.ContactsRecord> getRecordType() {
		return net.phobot.realestate.model.tables.records.ContactsRecord.class;
	}

	/**
	 * The column <code>PUBLIC.CONTACTS.EMAIL</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ContactsRecord, java.lang.String> EMAIL = createField("EMAIL", org.jooq.impl.SQLDataType.VARCHAR.length(256), this);

	/**
	 * The column <code>PUBLIC.CONTACTS.ACTOR_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ContactsRecord, java.lang.Long> ACTOR_ID = createField("ACTOR_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * Create a <code>PUBLIC.CONTACTS</code> table reference
	 */
	public Contacts() {
		super("CONTACTS", net.phobot.realestate.model.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.CONTACTS</code> table reference
	 */
	public Contacts(java.lang.String alias) {
		super(alias, net.phobot.realestate.model.Public.PUBLIC, net.phobot.realestate.model.tables.Contacts.CONTACTS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.ContactsRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.ContactsRecord, ?>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_C);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.phobot.realestate.model.tables.Contacts as(java.lang.String alias) {
		return new net.phobot.realestate.model.tables.Contacts(alias);
	}
}
