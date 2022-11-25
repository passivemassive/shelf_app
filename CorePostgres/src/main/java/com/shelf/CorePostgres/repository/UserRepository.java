package com.shelf.CorePostgres.repository;

import java.util.List;

import com.shelf.CorePostgres.models.User;

public interface UserRepository {
	
	  int save(User user);

	  int update(User user);

	  User findById(int id);

	  int deleteById(int id);

	  List<User> findAll();	  

	  int deleteAll();

}
