package com.predica.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.predica.test.model.User;

@Repository
public interface UserRegistrationRepository extends JpaRepository<User, String>{
	User findByUserId(String userName);
}
