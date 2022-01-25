package com.qa.userapp.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qa.userapp.data.entity.User;

@RestController // this is a bean that should be stored in the app context
@RequestMapping(path = "/user") // access this controller at localhost:8080/user
public class UserController {

	private JpaRepository<User, Long> repository;

	@Autowired
	public UserController(JpaRepository<User, Long> repository) {
		this.repository = repository;
	}

//	private static long counter = 0;
//
//	private List<User> users = new ArrayList<>(
//			List.of(new User(counter++, "Fred", "Daly", 33), new User(counter++, "Sarah", "Daly", 33)));

	@GetMapping // localhost:8080/user
	public List<User> getUsers() {
		return users;
	}

	// {id} is a path variable
	// we send requests to: localhost:8080/user/{id}
	@RequestMapping(path = "/{id}", method = { RequestMethod.GET })
	// @GetMapping(path = "/{id}")
	public User getUserById(@PathVariable("id") Long id) {
		if (repository.existsById(id)) {
			Optional<User> user = repository.findById(id);
		}
		return null;
//		for (User user : users) {
//			if (user.getId() == id) {
//				return user;
//			}
//		}
//		throw new EntityNotFoundException("Entity with id " + id + " was not found.");
	}

	// RequestMapping(method = { RequestMethod.POST })
	@PostMapping // accepts requests to: localhost:8080/user using POST
	public User createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		return savedUser;
//		user.setId(counter++);
//		users.add(user);
//		return user;
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
//		for (User userInDb : users) {
		if (userExists(id)) {
			for (User userInDb : users) {
				if (userInDb.getId() == id) {
					userInDb.setAge(userInDb.getAge());
					userInDb.setForename(userInDb.getForename());
					userInDb.setSurname(userInDb.getSurname());
					return userInDb;
				}
			}
//			if (user.getId() == id) {
//				user.setId(id);
//				return user;
//			}
//			if getUs
		}
		throw new EntityNotFoundException("Entity with id " + id + " was not found.");
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		// TODO: Delete user in list if they exist
		if (userExists(id)) {
			Iterator<User> iterator = users.iterator();
			while (iterator.hasNext()) {
				User user = iterator.next();
				if (user.getId() == id) {
					iterator.remove();
					return;
				}

			}
		}
	}

	private boolean userExists(long id) {
		for (User user : getUsers()) {
			if (user.getId() == id) {
				return true;
			}
		}
		return false;
	}
}