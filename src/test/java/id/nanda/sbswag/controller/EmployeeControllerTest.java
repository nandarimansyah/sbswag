package id.nanda.sbswag.controller;

import com.google.gson.Gson;
import id.nanda.sbswag.model.domain.Employee;
import id.nanda.sbswag.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
        Employee testEmployee = createSampleEmployee();
        List<Employee> expectedResult = Arrays.asList(testEmployee);

        given(service.getAllEmployees()).willReturn(expectedResult);

        mvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(testEmployee.getFirstName())));
    }

    @Test
    public void givenEmployee_whenGetEmployeeById_ThenReturnJson() throws Exception {
        Long employeeId = 201L;
        Employee testEmployee = createSampleEmployee();

        given(service.getEmployeeById(employeeId)).willReturn(testEmployee);

        mvc.perform(get("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())));
    }

    @Test
    public void createEmployee() throws Exception {
        Gson gson = new Gson();

        Long expectedId = 2001L;

        Employee testEmployee = createSampleEmployee();

        Employee createEmployee = createSampleEmployee();
        createEmployee.setId(expectedId);
        given(service.createEmployee(testEmployee)).willReturn(createEmployee);

        String jsonBody = gson.toJson(testEmployee);

        mvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

    private Employee createSampleEmployee() {
        return new Employee("John", "Canon", "John.Canon@mail.com");
    }

}