package com.shelf.CorePostgres.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import com.shelf.CorePostgres.models.User;


@Repository
public class UserJDBC implements UserRepository{
	
	@Autowired
	private JdbcAggregateTemplate jdbcTemplate;

	@Override
	public int save(User user) {
		
		return jdbcTemplate.update("INSERT INTO tutorials (title, description, published) VALUES(?,?,?)",
		         user.getUserId() , user.getName(), user.getBalance() );
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

}
