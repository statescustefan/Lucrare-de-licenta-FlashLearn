package com.info.uaic.licenta.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.info.uaic.licenta.model.User;


public interface UserRepository extends CrudRepository<User, Long>{

	@Query("from User where username = :userName")
	User findByUserName(@Param("userName") String userName);

	@Query("from User where email = :email")
	User findUserByEmail(@Param("email") String email);

}
