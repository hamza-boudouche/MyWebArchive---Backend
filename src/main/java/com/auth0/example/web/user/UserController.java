package com.auth0.example.web.user;

import com.auth0.example.model.Message;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.example.security.Utils;
import com.auth0.example.model.Users.User;

import java.util.List;

import org.springframework.http.HttpStatus;
/**
 * Handles requests to "/api" endpoints.
 * 
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are
 *      protected.
 */
@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should
// configure CORS for their use case.
@CrossOrigin(origins = "*")
public class UserController {

	private Utils utils;
	private UserService userService;

	public UserController(Utils utils, UserService userService) {
		this.utils = utils;
		this.userService = userService;
	}

	@GetMapping(value = "/public")
	public Message publicEndpoint() {
		return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
	}

	@GetMapping(value = "/private")
	publicy Message privateEndpoint(@RequestHeader("Authorization") String authHeader) {
		String user = this.utils.getUser(authHeader);
		return new Message("All good. You can see this because you are Authenticated. " + user);
	}

	@GetMapping(value = "/private-scoped")
	public Message privateScopedEndpoint() {
		return new Message(
				"All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
	}
	
	@GetMapping(value="/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}	
	
	@GetMapping(value="/getuser/{uid}")
	public User[] getUserById(@RequestParam String uid) {
		return userService.getUserById(uid);
	}
	
	@PostMapping(value="/users")
    	public void addUser(@RequestBody User user) {
        	userService.addUser(user);
    	}
	
	
	@PutMapping(value="/users")
	public void updateUserEmail(@RequestBody User user, @RequestParam String email ) {
		userService.updateUserEmail(user, email);
	}
	
	@PutMapping(value="/updateuser/{imageUrl}")
	public void updateUserImage(@RequestBody User user, @RequestParam String imageUrl) {
		userService.updateUserEmail(user, imageUrl);
	}
	
	@DeleteMapping(value="/users")
	public void deleteUser(@RequestBody String uid) {
		userService.deleteUser(uid);
	}
}
