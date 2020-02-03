package com.backyardev.springlogin.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Entity
@Table(name = "user_accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
        allowGetters = true)
public class MyUserAccounts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String user_name;
	    
	@NotBlank
	private String first_name;
	
	@NotBlank
	private String last_name;
	   
	private String password;
	
	private String role;

	private boolean enabled;
 
	public MyUserAccounts(String id, String email,String user_name, String first_name, //
           String last_name, String password,String roles, boolean enabled) {
       this.id = id;
       this.email = email;
       this.user_name= user_name;
       this.first_name = first_name;
       this.last_name = last_name;
       this.password = password;
       this.enabled = enabled;
       this.role = roles;
	}
	
	public MyUserAccounts() {
		
	}

	public String getRoles() {
		return role;
	}

	public void setRoles(String roles) {
		this.role = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
