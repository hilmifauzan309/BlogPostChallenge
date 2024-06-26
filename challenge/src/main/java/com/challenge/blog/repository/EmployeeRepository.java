package com.challenge.blog.repository;

import com.challenge.blog.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

    Optional<EmployeeModel> findByUsername(String username);


}
