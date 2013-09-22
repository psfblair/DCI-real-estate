/**
 * This class is generated by jOOQ
 */
package net.phobot.realestate.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CertifiedChecksRecord extends org.jooq.impl.TableRecordImpl<net.phobot.realestate.model.tables.records.CertifiedChecksRecord> implements org.jooq.Record6<java.lang.Long, java.math.BigDecimal, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> {

	private static final long serialVersionUID = -362605867;

	/**
	 * Setter for <code>PUBLIC.CERTIFIED_CHECKS.ID</code>. 
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.CERTIFIED_CHECKS.ID</code>. 
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.CERTIFIED_CHECKS.AMOUNT</code>. 
	 */
	public void setAmount(java.math.BigDecimal value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.CERTIFIED_CHECKS.AMOUNT</code>. 
	 */
	public java.math.BigDecimal getAmount() {
		return (java.math.BigDecimal) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.CERTIFIED_CHECKS.PAYABLE_TO</code>. 
	 */
	public void setPayableTo(java.lang.Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>PUBLIC.CERTIFIED_CHECKS.PAYABLE_TO</code>. 
	 */
	public java.lang.Long getPayableTo() {
		return (java.lang.Long) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.CERTIFIED_CHECKS.BELONGS_TO</code>. 
	 */
	public void setBelongsTo(java.lang.Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>PUBLIC.CERTIFIED_CHECKS.BELONGS_TO</code>. 
	 */
	public java.lang.Long getBelongsTo() {
		return (java.lang.Long) getValue(3);
	}

	/**
	 * Setter for <code>PUBLIC.CERTIFIED_CHECKS.IN_POSSESSION_OF</code>. 
	 */
	public void setInPossessionOf(java.lang.Long value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>PUBLIC.CERTIFIED_CHECKS.IN_POSSESSION_OF</code>. 
	 */
	public java.lang.Long getInPossessionOf() {
		return (java.lang.Long) getValue(4);
	}

	/**
	 * Setter for <code>PUBLIC.CERTIFIED_CHECKS.PURCHASE_ID</code>. 
	 */
	public void setPurchaseId(java.lang.Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>PUBLIC.CERTIFIED_CHECKS.PURCHASE_ID</code>. 
	 */
	public java.lang.Long getPurchaseId() {
		return (java.lang.Long) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Long, java.math.BigDecimal, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Long, java.math.BigDecimal, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.math.BigDecimal> field2() {
		return net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS.AMOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field3() {
		return net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS.PAYABLE_TO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field4() {
		return net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS.BELONGS_TO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field5() {
		return net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS.IN_POSSESSION_OF;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field6() {
		return net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS.PURCHASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.math.BigDecimal value2() {
		return getAmount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value3() {
		return getPayableTo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value4() {
		return getBelongsTo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value5() {
		return getInPossessionOf();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value6() {
		return getPurchaseId();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CertifiedChecksRecord
	 */
	public CertifiedChecksRecord() {
		super(net.phobot.realestate.model.tables.CertifiedChecks.CERTIFIED_CHECKS);
	}
}
