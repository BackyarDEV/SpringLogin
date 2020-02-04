package com.backyardev.springlogin.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MyUserDetails implements UserDetails {

	private String user_name;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private boolean enabled;
	private List<SimpleGrantedAuthority> authorities;
	
	
	public MyUserDetails(MyUserAccounts userAccounts) {
		this.user_name = userAccounts.getUser_name();
		this.email = userAccounts.getEmail();
		this.password = userAccounts.getPassword();
		this.enabled = userAccounts.isEnabled();
		this.first_name = userAccounts.getFirst_name();
		this.last_name = userAccounts.getLast_name();
		this.authorities = Arrays.stream(userAccounts.getRoles().getRole().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
	}

	public MyUserDetails() {
	}
	
	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return user_name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public String getEmail() {
		return email;
	}

}
