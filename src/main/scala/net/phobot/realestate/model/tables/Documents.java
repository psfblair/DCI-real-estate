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
public class Documents extends org.jooq.impl.TableImpl<net.phobot.realestate.model.tables.records.DocumentsRecord> {

	private static final long serialVersionUID = 1860556245;

	/**
	 * The singleton instance of <code>PUBLIC.DOCUMENTS</code>
	 */
	public static final net.phobot.realestate.model.tables.Documents DOCUMENTS = new net.phobot.realestate.model.tables.Documents();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.phobot.realestate.model.tables.records.DocumentsRecord> getRecordType() {
		return net.phobot.realestate.model.tables.records.DocumentsRecord.class;
	}

	/**
	 * The column <code>PUBLIC.DOCUMENTS.ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.DocumentsRecord, java.lang.Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.DOCUMENTS.DOCUMENT_TYPE_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.DocumentsRecord, java.lang.Long> DOCUMENT_TYPE_ID = createField("DOCUMENT_TYPE_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.DOCUMENTS.PURCHASE_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.DocumentsRecord, java.lang.Long> PURCHASE_ID = createField("PURCHASE_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.DOCUMENTS.IN_POSSESSION_OF</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.DocumentsRecord, java.lang.Long> IN_POSSESSION_OF = createField("IN_POSSESSION_OF", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * Create a <code>PUBLIC.DOCUMENTS</code> table reference
	 */
	public Documents() {
		super("DOCUMENTS", net.phobot.realestate.model.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.DOCUMENTS</code> table reference
	 */
	public Documents(java.lang.String alias) {
		super(alias, net.phobot.realestate.model.Public.PUBLIC, net.phobot.realestate.model.tables.Documents.DOCUMENTS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.DocumentsRecord> getPrimaryKey() {
		return net.phobot.realestate.model.Keys.CONSTRAINT_D;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.DocumentsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.DocumentsRecord>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_D);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.DocumentsRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.DocumentsRecord, ?>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_DE, net.phobot.realestate.model.Keys.CONSTRAINT_DE55, net.phobot.realestate.model.Keys.CONSTRAINT_DE5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.phobot.realestate.model.tables.Documents as(java.lang.String alias) {
		return new net.phobot.realestate.model.tables.Documents(alias);
	}
}
