package com.company.salestracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.salestracker.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
