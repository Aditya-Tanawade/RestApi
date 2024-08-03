package com.Employee.RestApi.Service;

import com.Employee.RestApi.DTO.EmployeeDto;

import com.Employee.RestApi.Entity.EmployeeEntity;
import com.Employee.RestApi.Exception.ResourceNotFoundException;
import com.Employee.RestApi.Repo.EmployeeRepo;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepo repo;
    private final ModelMapper mapper;


    public EmployeeService(EmployeeRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<EmployeeDto> getAllEmployee() {
        List<EmployeeEntity> employeeEntities=repo.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> mapper.map(employeeEntity,EmployeeDto.class))
                .collect(Collectors.toList());
    }


    public Optional<EmployeeDto> getEmployeeById(long id) {
         return repo.findById(id).map(employeeEntity -> mapper.map(employeeEntity,EmployeeDto.class));
    }

    public EmployeeDto createEmployee(EmployeeDto inputEmployee) {
        EmployeeEntity toSaveEntity=mapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity savedemployeeEntity=repo.save(toSaveEntity);
        return mapper.map(savedemployeeEntity,EmployeeDto.class);

    }


    public EmployeeDto updateEmployeeByID(EmployeeDto employeeDto,long emp_Id) {
        boolean exits=employeeExitsByID(emp_Id);
        if(!exits) throw new ResourceNotFoundException("Employee Doesnt Exits by ID : " + emp_Id);
        EmployeeEntity toUpdatEntity=mapper.map(employeeDto,EmployeeEntity.class);
        toUpdatEntity.setId(emp_Id);
        EmployeeEntity savedEmployeeEntity=repo.save(toUpdatEntity);
        return mapper.map(savedEmployeeEntity,EmployeeDto.class);
    }

    public Boolean employeeExitsByID(long emp_Id){
        return repo.existsById( emp_Id);
    }

    public Boolean deleteEmployee(long emp_Id) {
        boolean exits=employeeExitsByID(emp_Id);
        if( ! exits) throw new ResourceNotFoundException("Employee WIth this id Not Found :" +emp_Id);


        repo.deleteById(emp_Id);

        return true;
    }

    public EmployeeDto updatePartiallyEmployeeById(Long emp_Id, Map<String, Object>updates) {
        boolean exits=employeeExitsByID(emp_Id);
        if(!exits) return null;

        EmployeeEntity employee=repo.findById(emp_Id).get();
        updates.forEach((field, value)->{
           Field fieldToBeUpdated= ReflectionUtils.findField(EmployeeEntity.class,field);
           fieldToBeUpdated.setAccessible(true);
           ReflectionUtils.setField(fieldToBeUpdated,employee,value);
        });
        return mapper.map(repo.save(employee),EmployeeDto.class);
    }
}
