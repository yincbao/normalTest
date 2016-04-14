package com.cpw.cache.dao;

public interface IEntityDao<Entity> {

	public static final String table ="entity_rds_";
	
	
	/**
	 * 覆盖式
	 * @param entity
	 * @return
	 */
	public Entity save(Entity entity,boolean cover,Long expeir);
	
	public Entity query(long id);
	
	public boolean delete(long id);
	
	public Entity update(Entity entity);
	
}
