package id.nanda.sbswag.service;

import id.nanda.sbswag.exception.ResourceNotFoundException;
import id.nanda.sbswag.model.domain.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long employeeId) throws ResourceNotFoundException;
    Employee createEmployee(Employee employee);
}
