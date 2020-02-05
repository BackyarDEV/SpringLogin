package com.backyardev.springlogin.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backyardev.springlogin.exception.ResourceNotFoundException;
import com.backyardev.springlogin.model.MyUserAccounts;
import com.backyardev.springlogin.model.UserRoles;
import com.backyardev.springlogin.repository.UserAccountsRepository;
import com.backyardev.springlogin.repository.UserRolesRepository;

@RestController
@RequestMapping("/api")
public class MyUserAccountController {

	private static final Integer ROLE_USER = 1;
	
	@Autowired
	UserAccountsRepository userAccountRepository;
	
	@Autowired
	UserRolesRepository userRolesRepository;
	
	// Get all User Accounts
	@GetMapping("/useraccounts")
	public List<MyUserAccounts> getAllUserAccounts() {
		return userAccountRepository.findAll();
	}
	
	// Add a new User Account
	@PostMapping(value = "/useraccounts")
	public Boolean addUserAccount(@Valid MyUserAccounts myUserAccounts ) {
		if(userAccountRepository.save(myUserAccounts) != null) {
			System.out.println(myUserAccounts.getId());
			UserRoles userRoles = new UserRoles();
			userRoles.setUser_id(Integer.valueOf(myUserAccounts.getId()));
			userRoles.setRole_id(ROLE_USER);
			if(userRolesRepository.save(userRoles) != null) {
				return true;
			}
		}
		return false;
	}
	
	// Get a single User Account
	@GetMapping("/useraccounts/{id}")
	public String getUserAccountById(@PathVariable(value = "id") String UserId) {
		Optional<MyUserAccounts> data = userAccountRepository.findById(UserId);
		MyUserAccounts myUserAccounts = data.get();
		return myUserAccounts.getUser_name();
	}
	
	// Update a User Account
	@PutMapping("/useraccount/{id}")
	public MyUserAccounts updateUserAccount(@PathVariable(value = "id") String UserId
			, @Valid MyUserAccounts userDetails){
		MyUserAccounts myUserAccounts = userAccountRepository.findById(UserId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", UserId));
		
		myUserAccounts.setEmail(userDetails.getEmail()); 
		myUserAccounts.setFirst_name(userDetails.getFirst_name()); 
		myUserAccounts.setLast_name(userDetails.getLast_name()); 
		myUserAccounts.setUser_name(userDetails.getUser_name());
		myUserAccounts.setPassword(userDetails.getPassword());
		
		
		MyUserAccounts updateUser = userAccountRepository.save(myUserAccounts);
		
		return updateUser;
	}
	
	// Delete a User Account
	@DeleteMapping("/useraccounts/{id}")
	public ResponseEntity<?> deleteUserAccount(@PathVariable(value = "id") String UserId){
		MyUserAccounts myUserAccounts = userAccountRepository.findById(UserId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", UserId));
		
		userAccountRepository.delete(myUserAccounts);
		
		return ResponseEntity.ok().build();
	}
}