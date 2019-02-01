package ebook.repository.eBook.Repository.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ebook.repository.eBook.Repository.pojo.User;
import ebook.repository.eBook.Repository.pojo.UserDetailsImpl;
import ebook.repository.eBook.Repository.services.IUserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	private IUserService userService;

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	 
	public List<User> get() {
		return userService.get();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/users/{userName}", method = RequestMethod.GET, produces = "application/json")
	 
	public User get(@PathVariable String userName) {
		return userService.get(userName);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	 
	public User post(@RequestBody User user) {
		return userService.add(user);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/users/{userName}", method = RequestMethod.DELETE)
	 
	public boolean delete(@PathVariable String userName) {
		return userService.delete(userName);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Boolean> login(@RequestBody User user) {
		if(userService.login(user.getUsername(), user.getUserPassword()))
			return new ResponseEntity<>(true, HttpStatus.OK);
		else return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Boolean> logout() {
		userService.logout();
		return new ResponseEntity<>(true, HttpStatus.OK);
		
	}

	@PreAuthorize("hasAuthority('SUB')")
	@RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> changePassword(@RequestBody User u) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl logged = (UserDetailsImpl) auth.getPrincipal();
		
		return new ResponseEntity<>(userService.changePassword(logged.getUsername(), u.getUserPassword()),	HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SUB')")
	@RequestMapping(value = "users/{username}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> put(@RequestBody User user, @PathVariable String username) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl logged = (UserDetailsImpl) auth.getPrincipal();
		User oldUserData = userService.get(username);
		if (logged.getUser().getUsername() != user.getUsername() && !logged.isAdmin())
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		oldUserData.setFirstname(user.getFirstname());
		oldUserData.setLastname(user.getLastname());
		if(logged.isAdmin())
			oldUserData.setType(user.getType());
		userService.update(oldUserData);
		return new ResponseEntity<>(oldUserData, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/loggedIn", method = RequestMethod.GET, produces = "application/json") 
	public ResponseEntity<User> loggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName().equals("anonymousUser")) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		User u =  this.get(auth.getName());
		u.setUserPassword(""); 
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('SUB')")
	@RequestMapping(value = "users/changeData", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	 
	public ResponseEntity<User> changeData(@RequestBody User user) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl logged = (UserDetailsImpl) auth.getPrincipal();
		User oldUserData = userService.get(logged.getUsername());
		if (logged.getUser().getUsername() != user.getUsername() && !logged.isAdmin())
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		oldUserData.setFirstname(user.getFirstname());
		oldUserData.setLastname(user.getLastname());
		if(logged.isAdmin())
			oldUserData.setType(user.getType());
		userService.update(oldUserData);
		return new ResponseEntity<>(oldUserData, HttpStatus.OK);

	}

}
