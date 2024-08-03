package com.Employee.RestApi.Controller;

import com.Employee.RestApi.DTO.EmployeeDto;

import com.Employee.RestApi.Entity.EmployeeEntity;
import com.Employee.RestApi.Exception.ResourceNotFoundException;
import com.Employee.RestApi.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity< List<EmployeeDto>> getAllEmployee( ){
        return ResponseEntity.ok(service.getAllEmployee()) ;
    }

    @GetMapping("{Id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable ("Id") long id){
        Optional<EmployeeDto> employeeDto=service.getEmployeeById(id) ;
        return employeeDto
                .map(employeeDto1 -> ResponseEntity.ok(employeeDto1))
                .orElseThrow(()->new ResourceNotFoundException("Employee not Found" + id) );
    }


    @PostMapping("create")
    public  ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto inputEmployee){
        return  new ResponseEntity<>(service.createEmployee(inputEmployee), HttpStatus.CREATED) ;
    }

    @PutMapping("{emp_Id}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable long emp_Id){
        return  ResponseEntity.ok(service.updateEmployeeByID(employeeDto,emp_Id));
    }

    @DeleteMapping("{emp_Id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable long emp_Id) {
        Boolean gotDeleted=service.deleteEmployee(emp_Id);
        if(gotDeleted) return ResponseEntity.ok(service.deleteEmployee(emp_Id)) ;
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{emp_Id}")
    public ResponseEntity<EmployeeDto> updatePartiallyEmployeeById(@PathVariable  long emp_Id, @RequestBody Map<String,Object> updates ){
        EmployeeDto emp1=service.updatePartiallyEmployeeById(emp_Id,updates);
        if(emp1==null) return  ResponseEntity.notFound().build();

        return  ResponseEntity.ok(emp1);
    }

}
