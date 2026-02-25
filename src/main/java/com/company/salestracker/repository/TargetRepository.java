package com.company.salestracker.repository;

import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, String> {

}
