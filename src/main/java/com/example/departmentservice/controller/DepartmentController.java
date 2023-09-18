package com.example.departmentservice.controller;

import com.example.departmentservice.client.EmployeeClient;
import com.example.departmentservice.model.Department;
import com.example.departmentservice.model.Employee;
import com.example.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {


    private static final Logger LOGGER
            = LoggerFactory.getLogger(DepartmentController.class);

    private final EmployeeClient employeeClient;
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository,EmployeeClient employeeClient){
        this.departmentRepository=departmentRepository;
        this.employeeClient=employeeClient;
    }

    @PostMapping
    public Department add(@RequestBody Department department){
        LOGGER.info("Department add: {}",department);
        return departmentRepository.addDepartment(department);
    }
    @GetMapping
    public List<Department> findAll(){
        LOGGER.info("Department find");
        return departmentRepository.getDepartments();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id){
        LOGGER.info("Department find: id={}", id);
        return departmentRepository.findById(id);

    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees(){
        LOGGER.info("Department find");
        List<Department> departments
                =departmentRepository.getDepartments();
        departments.forEach(department -> department.setEmployees(
                employeeClient.findEmployeesByDepartment(department.getId())));
        return departments;
    }


}
