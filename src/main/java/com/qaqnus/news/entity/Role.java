package com.qaqnus.news.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Table(name="t_role")
public class Role implements GrantedAuthority {
	@Id
	private Long id;
	
	@Size(min=5, message="Не меньше 5 знаков")
	private String name;
	@Transient
	@ManyToMany(mappedBy="roles")
	private Set<User> users;
	
	public Role() {
		
	}
	
	
	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}


	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return getName();
	}

}
