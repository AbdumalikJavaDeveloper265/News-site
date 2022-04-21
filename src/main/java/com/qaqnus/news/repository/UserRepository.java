package com.qaqnus.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qaqnus.news.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String name);
}
