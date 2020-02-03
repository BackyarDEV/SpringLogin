package com.backyardev.springlogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backyardev.springlogin.model.MyUserAccounts;

@Repository
public interface UserAccountsRepository extends JpaRepository<MyUserAccounts, String> {

	Optional<MyUserAccounts> findByEmail(String email);
	
	
}
