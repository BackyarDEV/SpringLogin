package com.backyardev.springlogin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backyardev.springlogin.model.MyUserAccounts;
import com.backyardev.springlogin.model.MyUserDetails;
import com.backyardev.springlogin.repository.UserAccountsRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserAccountsRepository userAccountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<MyUserAccounts> user = userAccountRepository.findByEmail(email);
		
		return user.map(MyUserDetails::new).get();
	}
	
}
