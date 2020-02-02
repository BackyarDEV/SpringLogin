package com.backyardev.springlogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backyardev.springlogin.model.MyUserAccounts;


public interface UserAccountsRepository extends JpaRepository<MyUserAccounts, String> {

	Optional<MyUserAccounts> findByUser_name(String user_name);
	
}
