package com.example.apiemployeetest.services;

import com.example.apiemployeetest.models.Employee;
import com.example.apiemployeetest.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .id(1L)
                .firstName("Marcos")
                .lastName("Gonzales")
                .email("masn@mail.com")
                .build();
    }

    @Test
    @DisplayName("Junit test for saveEmployee")
    void givenEmployeeObject() {
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);
        // when - action
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // then - verify
        assertNotNull(savedEmployee);
    }


    @Test
    @DisplayName("ejUnit test for List employe")
    void givenEmployeeList() {

        Employee  employee1 = Employee.builder()
                .id(2L)
                .firstName("Ana")
                .lastName("Perez")
                .email("asnper@mail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
        // when - action
        List<Employee> employeeList = employeeService.getAllEmployees();
        // then - verify the output
        assertAll(
                () -> assertNotNull(employeeList),
                () -> assertEquals(employeeList.size(),2)
        );
    }

    @Test
    @DisplayName("JUnit test for update employee")
    void givenEmployeeObject_WhenUpdate() {
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@mail.com");
        employee.setFirstName("Jose");
        // when - action
        Employee updateEmployee = employeeService.updateEmployee(employee);

        // then - verify
        assertAll(
                () -> assertEquals(updateEmployee.getEmail(), "ram@mail.com"),
                () -> assertEquals(updateEmployee.getFirstName(), "Jose")

        );
    }

    @Test
    @DisplayName("JUnit test for employee delete")
    void givenEmployeeId_whenDelete() {
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when - action
        employeeService.deleteEmployee(employeeId);
        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}