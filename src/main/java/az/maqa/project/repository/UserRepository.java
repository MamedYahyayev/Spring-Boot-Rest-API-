package az.maqa.project.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import az.maqa.project.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userId);

	UserEntity findUserByEmailVerificationToken(String token);
	
	@Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'" , 
			countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
			nativeQuery = true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);
	
	
	@Query(value = "select * from Users u where u.first_name = ?1" , nativeQuery = true)
	List<UserEntity> findUserByFirstName(String firstName);
	
	@Query(value = "select * from Users u where u.last_name = :lastName" , nativeQuery = true)
	List<UserEntity> findUserByLastName(@Param("lastName") String lastName);
	
	@Query(value = "select * from Users u where u.last_name LIKE %:keyword%" , nativeQuery = true)
	List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

	@Query(value = "select u.last_name from Users u where u.last_name LIKE %:keyword%" , nativeQuery = true)
	List<Object[]> findUserLastnameByKeyword(@Param("keyword") String keyword);
	
	@Transactional
	@Modifying
	@Query(value = "update users  u set u.EMAIL_VERIFICATION_STATUS = :emailVerificationStatus where u.user_id = :userId " , nativeQuery = true)
	void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus , @Param("userId") String userId);
	
}
