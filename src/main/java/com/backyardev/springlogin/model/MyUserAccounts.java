package com.backyardev.springlogin.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
	
	public static final String ROLE_USER = "ROLE_USER";
	 
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
	 
	public MyUserAccounts() {
 
	}
 
	public MyUserAccounts(String id, String email,String userName, String firstName, //
           String lastName, String password,String role, String enabled) {
       this.id = id;
       this.email = email;
       this.user_name= userName;
       this.first_name = firstName;
       this.last_name = lastName;
       this.password = password;
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

	
}
