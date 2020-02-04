package com.backyardev.springlogin.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Roles {
	
	@Id
	private Integer role_id;
	
	private String role;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
    joinColumns={@JoinColumn(name="role_id", referencedColumnName="role_id")},
    inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})
    private List<MyUserAccounts> userList;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<MyUserAccounts> getUserList() {
		return userList;
	}

	public void setUserList(List<MyUserAccounts> userList) {
		this.userList = userList;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
}
