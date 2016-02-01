package com.gloudtek.base;

import org.apache.ibatis.session.SqlSessionFactory;
//import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public interface BaseDao {

	public JdbcTemplate getJdbcTemplate();
	
	//public SessionFactory getSessionFactory();
	
	public SqlSessionFactory getSqlSessionFactory();
	
	//public MongoTemplate getMongoTemplate();
	
}
