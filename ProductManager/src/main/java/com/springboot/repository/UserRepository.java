 package com.springboot.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("SELECT u From User u WHERE u.user_username = :user_username")
	public User getUserByUsername(@Param("user_username") String user_username);
}
 