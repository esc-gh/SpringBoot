package com.qa.userapp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.userapp.data.entity.User;
import com.qa.userapp.data.repository.UserRepository;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private List<User> usersInDatabase;
	private long nextNewElementsId;

	// initialise the database for each test as it is rolled back afterwards
	@BeforeEach
	public void init() {
		List<User> users = List.of(new User(1, "bob", "lee", 22), new User(2, "fred", "see", 25),
				new User(3, "sarah", "fee", 28));
		usersInDatabase = new ArrayList<>();
		usersInDatabase.addAll(userRepository.saveAll(users));
		int size = usersInDatabase.size();
		nextNewElementsId = usersInDatabase.get(size - 1).getId() + 1;
	}

	@Test
	public void getAllUsersTest() {
		assertThat(usersInDatabase).isEqualTo(userService.getAll());
	}

	@Test
	public void createUserTest() {
		User userToSave = new User("Janet", "Carlisle", 32);
		User expectedUser = new User(nextNewElementsId, userToSave.getForename(), userToSave.getSurname(),
				userToSave.getAge());

		assertThat(expectedUser).isEqualTo(userService.create(userToSave));
	}

	@Test
	public void getUserByIdTest() {
		User expectedUser = new User(1, "bob", "lee", 22);
		long userId = 1;

		assertThat(expectedUser).isEqualTo(userService.getById(userId));
	}

	@Test
	public void updateUserTest() {
		// TODO: Implement me
		User updatedUser = new User(1, "bob", "lee-swagger", 22);
		User toUpdateWith = new User("bob", "lee-swagger", 22);
		long userId = 1;

		assertThat(updatedUser).isEqualTo(userService.update(userId, toUpdateWith));
	}

	@Test
	public void deleteUserTest() {
		User expectedUser = null;
		long userId = 1;

		assertThat(expectedUser).isEqualTo(userService.delete(userId));
	}
}