package az.maqa.project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import az.maqa.project.entity.UserEntity;
import az.maqa.project.repository.UserRepository;
import az.maqa.project.shared.SendEmail;
import az.maqa.project.shared.Utils;
import az.maqa.project.shared.dto.AddressDto;
import az.maqa.project.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	SendEmail sendEmail;

	String userId = "sadqwdq1243214";
	String encryptedPassword = "sadasd129341";

	UserEntity userEntity;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Mamed");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("test@gmail.com");
		userEntity.setEmailVerificationToken("asdasdqa123412");
	}

	@Test
	void testGetUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("test@test.com");

		assertNotNull(userDto);

		assertEquals("Mamed", userDto.getFirstName());

	}

	@Test
	void testGetUser_UsernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser("test@test.com");
		});
	}

	@Test
	void testCreateUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(null);
		
		when(utils.generateAddressId(anyInt())).thenReturn("adsfaasf346433");
		
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		
		when(passwordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		

		AddressDto addressDto = new AddressDto();
		addressDto.setType("shipping");

		List<AddressDto> addresses = new ArrayList<>();
		addresses.add(addressDto);

		UserDto userDto = new UserDto();
		userDto.setAddresses(addresses);

		UserDto storedUserDetails = userService.createUser(userDto);

		assertNotNull(storedUserDetails);

		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());

	}

}
