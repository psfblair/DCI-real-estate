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
public class ActorsRecord extends org.jooq.impl.UpdatableRecordImpl<net.phobot.realestate.model.tables.records.ActorsRecord> implements org.jooq.Record1<java.lang.Long> {

	private static final long serialVersionUID = -96965418;

	/**
	 * Setter for <code>PUBLIC.ACTORS.ACTOR_ID</code>. 
	 */
	public void setActorId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.ACTORS.ACTOR_ID</code>. 
	 */
	public java.lang.Long getActorId() {
		return (java.lang.Long) getValue(0);
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
	// Record1 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.Long> fieldsRow() {
		return (org.jooq.Row1) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.Long> valuesRow() {
		return (org.jooq.Row1) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return net.phobot.realestate.model.tables.Actors.ACTORS.ACTOR_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getActorId();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ActorsRecord
	 */
	public ActorsRecord() {
		super(net.phobot.realestate.model.tables.Actors.ACTORS);
	}
}
