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
public class PaymentTypes extends org.jooq.impl.TableImpl<net.phobot.realestate.model.tables.records.PaymentTypesRecord> {

	private static final long serialVersionUID = -1539104496;

	/**
	 * The singleton instance of <code>PUBLIC.PAYMENT_TYPES</code>
	 */
	public static final net.phobot.realestate.model.tables.PaymentTypes PAYMENT_TYPES = new net.phobot.realestate.model.tables.PaymentTypes();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.phobot.realestate.model.tables.records.PaymentTypesRecord> getRecordType() {
		return net.phobot.realestate.model.tables.records.PaymentTypesRecord.class;
	}

	/**
	 * The column <code>PUBLIC.PAYMENT_TYPES.ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.PaymentTypesRecord, java.lang.Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.PAYMENT_TYPES.PAYMENT_TYPE</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.PaymentTypesRecord, java.lang.String> PAYMENT_TYPE = createField("PAYMENT_TYPE", org.jooq.impl.SQLDataType.VARCHAR.length(2147483647), this);

	/**
	 * Create a <code>PUBLIC.PAYMENT_TYPES</code> table reference
	 */
	public PaymentTypes() {
		super("PAYMENT_TYPES", net.phobot.realestate.model.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.PAYMENT_TYPES</code> table reference
	 */
	public PaymentTypes(java.lang.String alias) {
		super(alias, net.phobot.realestate.model.Public.PUBLIC, net.phobot.realestate.model.tables.PaymentTypes.PAYMENT_TYPES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.PaymentTypesRecord> getPrimaryKey() {
		return net.phobot.realestate.model.Keys.CONSTRAINT_5;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.PaymentTypesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.PaymentTypesRecord>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.phobot.realestate.model.tables.PaymentTypes as(java.lang.String alias) {
		return new net.phobot.realestate.model.tables.PaymentTypes(alias);
	}
}
