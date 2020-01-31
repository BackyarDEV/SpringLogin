package com.backyardev.springlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backyardev.springlogin.model.MyUserAccounts;

@Repository
public interface UserAccountRepository extends JpaRepository<MyUserAccounts, String>{
	
	MyUserAccounts findUserByEmail(String email);

}
