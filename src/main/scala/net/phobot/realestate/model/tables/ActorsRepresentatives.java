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
public class ActorsRepresentatives extends org.jooq.impl.TableImpl<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord> {

	private static final long serialVersionUID = 1694977163;

	/**
	 * The singleton instance of <code>PUBLIC.ACTORS_REPRESENTATIVES</code>
	 */
	public static final net.phobot.realestate.model.tables.ActorsRepresentatives ACTORS_REPRESENTATIVES = new net.phobot.realestate.model.tables.ActorsRepresentatives();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord> getRecordType() {
		return net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord.class;
	}

	/**
	 * The column <code>PUBLIC.ACTORS_REPRESENTATIVES.ACTOR_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord, java.lang.Long> ACTOR_ID = createField("ACTOR_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.ACTORS_REPRESENTATIVES.REPRESENTATIVE_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord, java.lang.Long> REPRESENTATIVE_ID = createField("REPRESENTATIVE_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * The column <code>PUBLIC.ACTORS_REPRESENTATIVES.PURCHASE_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord, java.lang.Long> PURCHASE_ID = createField("PURCHASE_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * Create a <code>PUBLIC.ACTORS_REPRESENTATIVES</code> table reference
	 */
	public ActorsRepresentatives() {
		super("ACTORS_REPRESENTATIVES", net.phobot.realestate.model.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.ACTORS_REPRESENTATIVES</code> table reference
	 */
	public ActorsRepresentatives(java.lang.String alias) {
		super(alias, net.phobot.realestate.model.Public.PUBLIC, net.phobot.realestate.model.tables.ActorsRepresentatives.ACTORS_REPRESENTATIVES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<net.phobot.realestate.model.tables.records.ActorsRepresentativesRecord, ?>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_2A3, net.phobot.realestate.model.Keys.CONSTRAINT_2A3C5, net.phobot.realestate.model.Keys.CONSTRAINT_2A3C);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.phobot.realestate.model.tables.ActorsRepresentatives as(java.lang.String alias) {
		return new net.phobot.realestate.model.tables.ActorsRepresentatives(alias);
	}
}
