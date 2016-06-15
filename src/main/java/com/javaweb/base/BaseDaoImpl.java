package com.javaweb.base;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
//import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("baseDaoImpl")
public class BaseDaoImpl implements BaseDao{
	
	//jdbcTemplate
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	//hibernate
	//@Resource
	//private SessionFactory sessionFactory;
	
	//mybatis
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	//mongodb
	//@Resource
	//private MongoTemplate mongoTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	//public SessionFactory getSessionFactory() {
	//	return sessionFactory;
	//}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	//public MongoTemplate getMongoTemplate() {
	//	return mongoTemplate;
	//}

}
