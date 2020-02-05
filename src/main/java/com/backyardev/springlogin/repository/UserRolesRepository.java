package com.backyardev.springlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backyardev.springlogin.model.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
	
}
