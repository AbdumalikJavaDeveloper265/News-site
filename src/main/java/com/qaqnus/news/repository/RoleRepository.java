package com.qaqnus.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qaqnus.news.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
