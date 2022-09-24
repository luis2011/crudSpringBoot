package com.luis.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luis.app.entity.User;

public interface UserService {
	
	public Iterable<User> findAll();
	
	public Page<User> findAll(Pageable pageable);// investigation 
	
	public Optional<User> findById(long id);
	
	public User save(User user);
	
	public void deleteById(long id);
	
}
