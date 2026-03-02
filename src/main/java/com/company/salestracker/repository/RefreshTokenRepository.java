package com.company.salestracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.salestracker.entity.RefreshToken;
import com.company.salestracker.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
	  	Optional<RefreshToken> findByToken(String token);
	  	void deleteByUserAndIsUsed(User user,Boolean isUsed);
	  	void deleteByUser(User user);

}
