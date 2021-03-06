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
public class Actors extends org.jooq.impl.TableImpl<net.phobot.realestate.model.tables.records.ActorsRecord> {

	private static final long serialVersionUID = -1298063217;

	/**
	 * The singleton instance of <code>PUBLIC.ACTORS</code>
	 */
	public static final net.phobot.realestate.model.tables.Actors ACTORS = new net.phobot.realestate.model.tables.Actors();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<net.phobot.realestate.model.tables.records.ActorsRecord> getRecordType() {
		return net.phobot.realestate.model.tables.records.ActorsRecord.class;
	}

	/**
	 * The column <code>PUBLIC.ACTORS.ACTOR_ID</code>. 
	 */
	public final org.jooq.TableField<net.phobot.realestate.model.tables.records.ActorsRecord, java.lang.Long> ACTOR_ID = createField("ACTOR_ID", org.jooq.impl.SQLDataType.BIGINT, this);

	/**
	 * Create a <code>PUBLIC.ACTORS</code> table reference
	 */
	public Actors() {
		super("ACTORS", net.phobot.realestate.model.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>PUBLIC.ACTORS</code> table reference
	 */
	public Actors(java.lang.String alias) {
		super(alias, net.phobot.realestate.model.Public.PUBLIC, net.phobot.realestate.model.tables.Actors.ACTORS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.ActorsRecord> getPrimaryKey() {
		return net.phobot.realestate.model.Keys.CONSTRAINT_7;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.ActorsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<net.phobot.realestate.model.tables.records.ActorsRecord>>asList(net.phobot.realestate.model.Keys.CONSTRAINT_7);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public net.phobot.realestate.model.tables.Actors as(java.lang.String alias) {
		return new net.phobot.realestate.model.tables.Actors(alias);
	}
}
