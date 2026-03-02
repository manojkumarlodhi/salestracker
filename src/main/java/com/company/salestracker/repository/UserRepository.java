package com.company.salestracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.company.salestracker.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	@EntityGraph(attributePaths = { "roles", "roles.permissions" })
	Optional<User> findByEmail(String email);

	Optional<User> findByPhone(String phone);

}
