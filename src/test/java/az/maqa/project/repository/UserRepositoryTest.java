package az.maqa.project.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import az.maqa.project.entity.AddressEntity;
import az.maqa.project.entity.UserEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	boolean recordsCreated = false;

	@BeforeEach
	void setUp() throws Exception {
		if (!recordsCreated)
			createRecord();

	}

	@Test
	void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 2);
		Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);

		assertNotNull(pages);

		List<UserEntity> userEntities = pages.getContent();
		assertNotNull(userEntities);
		assertTrue(userEntities.size() == 2);

	}

	@Test
	void findUserByFirstNameTest() {

		List<UserEntity> users = userRepository.findUserByFirstName("Mamed");
		assertNotNull(users);

		assertTrue(users.size() == 2);

		UserEntity userEntity = users.get(0);

		assertTrue(userEntity.getFirstName().equals("Mamed"));

	}

	private void createRecord() {
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("Mamed");
		userEntity.setLastName("Yahyayev");
		userEntity.setUserId("123124asdas");
		userEntity.setEncryptedPassword("12341dsa");
		userEntity.setEmail("test@gmail.com");
		userEntity.setEmailVerificationStatus(true);

		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setType("shipping");
		addressEntity.setAddressId("sdsada");
		addressEntity.setCity("fsasafs");
		addressEntity.setCountry("fadsdas");
		addressEntity.setPostalCode("sadfada");
		addressEntity.setStreetName("vasfasfa");

		List<AddressEntity> addresses = new ArrayList<>();
		addresses.add(addressEntity);

		userEntity.setAddresses(addresses);

		userRepository.save(userEntity);
		
		recordsCreated = true;
	}

}
