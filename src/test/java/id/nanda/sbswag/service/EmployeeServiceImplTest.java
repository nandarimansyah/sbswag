package id.nanda.sbswag.service;

import id.nanda.sbswag.exception.ResourceNotFoundException;
import id.nanda.sbswag.model.domain.Employee;
import id.nanda.sbswag.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void getAllEmployees() {
        Employee testEmployee = createSampleEmployee();
        List<Employee> expectedResult = Arrays.asList(testEmployee);

        Mockito.when(employeeRepository.findAll()).thenReturn(expectedResult);

        List<Employee> actualResult = employeeService.getAllEmployees();

        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult.get(0).toString(), actualResult.get(0).toString());
    }

    @Test
    public void getEmployeeById_Found() throws ResourceNotFoundException {
        Long empId = 200L;

        Employee testEmployee = createSampleEmployee();
        Mockito.when(employeeRepository.findById(Mockito.eq(empId))).thenReturn(Optional.of(testEmployee));

        Employee actual = employeeService.getEmployeeById(empId);
        assertEquals(testEmployee.toString(), actual.toString());
    }

    @Test
    public void createEmployee() {
        Employee createEmployee = createSampleEmployee();
        Employee expecteedResponse = createEmployee;
        expecteedResponse.setId(201);
        Mockito.when(employeeRepository.save(Mockito.eq(createEmployee))).thenReturn(expecteedResponse);

        Employee actual = employeeService.createEmployee(createEmployee);

        assertEquals(expecteedResponse.getId(), actual.getId());
        assertEquals(expecteedResponse.getFirstName(), actual.getFirstName());
        assertEquals(expecteedResponse.getLastName(), actual.getLastName());
        assertEquals(expecteedResponse.getEmailId(), actual.getEmailId());
    }

    private Employee createSampleEmployee() {
        return new Employee("John", "Canon", "John.Canon@mail.com");
    }
}