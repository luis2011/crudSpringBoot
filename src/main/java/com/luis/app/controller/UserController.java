package com.luis.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luis.app.entity.User;
import com.luis.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// Create a new user
	@PostMapping
	public ResponseEntity<?> create (@RequestBody User user){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
		
	}
	
	//Read  an User
	
	@GetMapping("/{id}")
	
	public ResponseEntity<?> read (@PathVariable Long id){ 
								// @PathVariable(value = "id" Long userId){}
		
		Optional<User> oUser = userService.findById(id);
		
		// is not exist user?
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build(); // return error 404:NotFound
		}
		
		return ResponseEntity.ok(oUser); // return status 200:Ok
		
	}
	
	//Update an User
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable Long id){
			Optional<User> user= userService.findById(id);
			
			if(!user.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			user.get().setName(userDetails.getName());
			user.get().setSurname(userDetails.getSurname());
			user.get().setEmail(userDetails.getEmail());
			user.get().setEnabled(userDetails.getEnabled());
			// other option 
			//BeanUtils.copyProperties(userDetails, user.get());
			
			
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
			
		}
	
	//delete an user
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable Long id){
		
		if(!userService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	//Read all Users
	@GetMapping()
	public List<User> readAll (){
		
		//StreamSupport = API of java 8
		List<User> users = StreamSupport
				.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		return users;
	}
	
}
