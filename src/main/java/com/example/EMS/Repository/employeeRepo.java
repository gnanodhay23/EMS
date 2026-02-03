package com.example.EMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EMS.model.employee;

@Repository
public interface employeeRepo extends JpaRepository<employee, Long>{
	Optional<employee> findByName(String name);

}
