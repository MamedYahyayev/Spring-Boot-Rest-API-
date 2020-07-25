package az.maqa.project.repository;

import org.springframework.data.repository.CrudRepository;

import az.maqa.project.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

	PasswordResetTokenEntity findByToken(String token);
	
	

}
