package com.veilu.sprinboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.veilu.sprinboot.exception.ResourceNotFoundException;

import com.veilu.sprinboot.entity.User;
import com.veilu.sprinboot.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	//find all
	@GetMapping
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
		
	}
	//find byid
	@GetMapping("/{id}")
	public User getUserByID(@PathVariable (value = "id") long userId){
		return this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User Not Found with ID" + userId));
		
	}
	//createuser
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	//updateuser
		@PutMapping("/{id}")
		public User updateUser(@RequestBody User user, @PathVariable (value = "id") long userId) {
			User existingUser = this.userRepository.findById(userId)
					.orElseThrow(()-> new ResourceNotFoundException("User Not Found with ID" + userId));
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setEmail(user.getEmail());
			return this.userRepository.save(existingUser);
		}
		//delete
		@DeleteMapping("/{id}")
		public ResponseEntity<User> deleteUser(@PathVariable (value = "id") long userId) {
			User existingUser = this.userRepository.findById(userId)
					.orElseThrow(()-> new ResourceNotFoundException("User Not Found with ID" + userId));
			 this.userRepository.delete(existingUser);
			 return ResponseEntity.ok().build();
		}
}
