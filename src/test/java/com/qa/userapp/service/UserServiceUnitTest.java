package com.qa.userapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qa.userapp.data.entity.User;
import com.qa.userapp.data.repository.UserRepository;

// No need to use the spring boot context, just create stubs using pure Mockito rather than Springs variant of Mockito
@ExtendWith(MockitoExtension.class) // JUnit test runner
public class UserServiceUnitTest {

	@Mock // equivalent to MockBean
	private UserRepository userRepository;

	@InjectMocks // equivalent to @Autowired
	private UserService userService;

	private List<User> users;
	private User expectedUserWithId;
	private User expectedUserWithoutId;

	@BeforeEach // junit5 (jupiter) annotation to run this method before every test
	public void init() {
		users = new ArrayList<>();
		users.addAll(List.of(new User(1, "bob", "lee", 22), new User(2, "fred", "see", 25),
				new User(3, "sarah", "fee", 28)));
		expectedUserWithoutId = new User("bob", "lee", 22);
		expectedUserWithId = new User(1, "bob", "lee", 22);
	}

	@Test
	public void getAllUsersTest() {
		when(userRepository.findAll()).thenReturn(users);
		assertThat(userService.getAll()).isEqualTo(users);
		verify(userRepository).findAll();
	}

	@Test
	public void createUserTest() {
		when(userRepository.save(expectedUserWithoutId)).thenReturn(expectedUserWithId);

		assertThat(userService.create(expectedUserWithoutId)).isEqualTo(expectedUserWithId);
		verify(userRepository).save(expectedUserWithoutId);
	}

	@Test
	public void getUserByIdTest() {
		when(userRepository.findById(expectedUserWithId.getId())).thenReturn(Optional.of(expectedUserWithId));

		assertThat(userService.getById(expectedUserWithId.getId())).isEqualTo(expectedUserWithId);
		verify(userRepository).findById(expectedUserWithId.getId());
	}

	@Test
	public void updateUserTest() {
		User userToUpdate = users.get(0);
		long id = userToUpdate.getId();
		User updatedUser = new User(id, userToUpdate.getForename(), userToUpdate.getSurname(),
				userToUpdate.getAge() + 1);
		when(userRepository.existsById(id)).thenReturn(true);
		when(userRepository.getById(id)).thenReturn(userToUpdate);
		when(userRepository.save(userToUpdate)).thenReturn(updatedUser);
		assertThat(userService.update(id, updatedUser)).isEqualTo(updatedUser);
		verify(userRepository).existsById(id);
		verify(userRepository).getById(id);
		verify(userRepository).save(userToUpdate);
	}

	@Test
	public void deleteUserTest() {
		User userToDelete = users.get(0);
		long id = userToDelete.getId();

		when(userRepository.existsById(id)).thenReturn(true);

//		when(userRepository.deleteById(id));

		assertThat(userService.delete(id)).isEqualTo(null);
		verify(userRepository.existsById(id));
		verify(userRepository).deleteById(id);
	}
}