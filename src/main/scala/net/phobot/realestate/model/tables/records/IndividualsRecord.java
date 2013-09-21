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
public class IndividualsRecord extends org.jooq.impl.UpdatableRecordImpl<net.phobot.realestate.model.tables.records.IndividualsRecord> implements org.jooq.Record3<java.lang.String, java.lang.String, java.lang.Long> {

	private static final long serialVersionUID = 1950393580;

	/**
	 * Setter for <code>PUBLIC.INDIVIDUALS.FIRST_NAME</code>. 
	 */
	public void setFirstName(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.INDIVIDUALS.FIRST_NAME</code>. 
	 */
	public java.lang.String getFirstName() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.INDIVIDUALS.LAST_NAME</code>. 
	 */
	public void setLastName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.INDIVIDUALS.LAST_NAME</code>. 
	 */
	public java.lang.String getLastName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.INDIVIDUALS.ACTOR_ID</code>. 
	 */
	public void setActorId(java.lang.Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>PUBLIC.INDIVIDUALS.ACTOR_ID</code>. 
	 */
	public java.lang.Long getActorId() {
		return (java.lang.Long) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.String, java.lang.String, java.lang.Long> fieldsRow() {
		return (org.jooq.Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.String, java.lang.String, java.lang.Long> valuesRow() {
		return (org.jooq.Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return net.phobot.realestate.model.tables.Individuals.INDIVIDUALS.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return net.phobot.realestate.model.tables.Individuals.INDIVIDUALS.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field3() {
		return net.phobot.realestate.model.tables.Individuals.INDIVIDUALS.ACTOR_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value3() {
		return getActorId();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached IndividualsRecord
	 */
	public IndividualsRecord() {
		super(net.phobot.realestate.model.tables.Individuals.INDIVIDUALS);
	}
}
