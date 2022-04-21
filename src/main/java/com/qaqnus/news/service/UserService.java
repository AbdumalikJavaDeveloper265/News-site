package com.qaqnus.news.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qaqnus.news.entity.Role;
import com.qaqnus.news.entity.User;
import com.qaqnus.news.repository.RoleRepository;
import com.qaqnus.news.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		return user;
	}
	
	public User findUserById(Long userId) {
		Optional<User> userFromDb = userRepository.findById(userId);
		return userFromDb.orElse(new User());
	}
	
	public List<User> allUsers(){
		return userRepository.findAll();
	}
	
	public boolean saveUser(User user) {
		User userFromDb = userRepository.findByUsername(user.getUsername());
		
		if(userFromDb != null) {
			return false;
		}
		
		user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return true;
	}
	
	public boolean deleteUser(Long userId) {
		Optional<User> userFromDb = userRepository.findById(userId);
		
		if(userFromDb == null) {
			return false;
		}
		
		userRepository.deleteById(userId);
		return true;
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
