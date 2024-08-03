package com.Employee.RestApi.Repo;

import com.Employee.RestApi.DTO.EmployeeDto;

import com.Employee.RestApi.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {

}
