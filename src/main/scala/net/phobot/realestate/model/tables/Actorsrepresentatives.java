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
public class Actorsrepresentatives extends org.jooq.impl.TableImpl<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord> {

	private static final long serialVersionUID = -192159117;

	/**
	 * The singleton instance of <code>PUBLIC.ACTORSREPRESENTATIVES</code>
	 */
	public static final net.phobot.realestate.model.tables.Actorsrepresentatives ACTORSREPRESENTATIVES = new net.phobot.realestate.model.tables.Actorsrepresentatives();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord> getRecordType() {
		return net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord.class;
	}

	/**
	 * The column <code>PUBLIC.ACTORSREPRESENTATIVES.ACTOR_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord, java.lang.Long> ACTOR_ID = createField("ACTOR_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.ACTORSREPRESENTATIVES.REPRESENTATIVE_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord, java.lang.Long> REPRESENTATIVE_ID = createField("REPRESENTATIVE_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.ACTORSREPRESENTATIVES.PURCHASE_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord, java.lang.Long> PURCHASE_ID = createField("PURCHASE_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * Create a <code>PUBLIC.ACTORSREPRESENTATIVES</code> table reference
	 */
	public Actorsrepresentatives() {
		super("ACTORSREPRESENTATIVES", net.phobot.realestate.model.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.ACTORSREPRESENTATIVES</code> table reference
	 */
	public Actorsrepresentatives(java.lang.String alias) {
		super(alias, net.phobot.realestate.model.Public.PUBLIC, net.phobot.realestate.model.tables.Actorsrepresentatives.ACTORSREPRESENTATIVES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.ActorsrepresentativesRecord, ?>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_5A, net.phobot.realestate.model.Keys.CONSTRAINT_5A7, net.phobot.realestate.model.Keys.CONSTRAINT_5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.phobot.realestate.model.tables.Actorsrepresentatives as(java.lang.String alias) {
		return new net.phobot.realestate.model.tables.Actorsrepresentatives(alias);
	}
}
