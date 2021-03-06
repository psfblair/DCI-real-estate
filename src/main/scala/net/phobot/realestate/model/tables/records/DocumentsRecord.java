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
public class DocumentsRecord extends org.jooq.impl.UpdatableRecordImpl<net.phobot.realestate.model.tables.records.DocumentsRecord> implements org.jooq.Record4<java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> {

	private static final long serialVersionUID = 967471165;

	/**
	 * Setter for <code>PUBLIC.DOCUMENTS.ID</code>. 
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.DOCUMENTS.ID</code>. 
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.DOCUMENTS.DOCUMENT_TYPE_ID</code>. 
	 */
	public void setDocumentTypeId(java.lang.Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.DOCUMENTS.DOCUMENT_TYPE_ID</code>. 
	 */
	public java.lang.Long getDocumentTypeId() {
		return (java.lang.Long) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.DOCUMENTS.PURCHASE_ID</code>. 
	 */
	public void setPurchaseId(java.lang.Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>PUBLIC.DOCUMENTS.PURCHASE_ID</code>. 
	 */
	public java.lang.Long getPurchaseId() {
		return (java.lang.Long) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.DOCUMENTS.IN_POSSESSION_OF</code>. 
	 */
	public void setInPossessionOf(java.lang.Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>PUBLIC.DOCUMENTS.IN_POSSESSION_OF</code>. 
	 */
	public java.lang.Long getInPossessionOf() {
		return (java.lang.Long) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return net.phobot.realestate.model.tables.Documents.DOCUMENTS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field2() {
		return net.phobot.realestate.model.tables.Documents.DOCUMENTS.DOCUMENT_TYPE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field3() {
		return net.phobot.realestate.model.tables.Documents.DOCUMENTS.PURCHASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field4() {
		return net.phobot.realestate.model.tables.Documents.DOCUMENTS.IN_POSSESSION_OF;
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
	public java.lang.Long value2() {
		return getDocumentTypeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value3() {
		return getPurchaseId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value4() {
		return getInPossessionOf();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DocumentsRecord
	 */
	public DocumentsRecord() {
		super(net.phobot.realestate.model.tables.Documents.DOCUMENTS);
	}
}
