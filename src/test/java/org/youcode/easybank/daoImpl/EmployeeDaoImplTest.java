package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.EmployeeException;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImplTest {
    private EmployeeDaoImpl employeeDao;

    private int testMatricule;
    @BeforeEach
    public void setUp() throws EmployeeException {
        Connection testConnection = DBTestConnection.establishTestConnection();

        employeeDao = new EmployeeDaoImpl(testConnection);

        Employee employee = new Employee(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat",
                LocalDate.of(2023, 9, 21),
                "mousta@gmail.com"
        );

        employeeDao.create(employee);

        testMatricule = employee.get_matricule();
    }

    void assertEmployeesEqual(Employee expected, Employee actual) {
        assertEquals(expected.get_firstName(), actual.get_firstName());
        assertEquals(expected.get_lastName(), actual.get_lastName());
        assertEquals(expected.get_birthDate(), actual.get_birthDate());
        assertEquals(expected.get_phone(), actual.get_phone());
        assertEquals(expected.get_address(), actual.get_address());
        assertEquals(expected.get_recruitmentDate(), actual.get_recruitmentDate());
        assertEquals(expected.get_email(), actual.get_email());
    }

    @Test
    public void testCreate() throws EmployeeException {
        Employee employee = new Employee(
                "Abdelali",
                "Hotgame",
                LocalDate.of(1990, 5, 15),
                "0682332783924",
                "hay anass",
                LocalDate.of(2023, 9, 21),
                "hotgam@gmail.com"
        );

        Optional<Employee> createdEmployee = employeeDao.create(employee);
        assertTrue(createdEmployee.isPresent());
        assertEquals(employee.get_firstName(), createdEmployee.get().get_firstName());
        assertEquals(employee.get_lastName(), createdEmployee.get().get_lastName());
        assertEquals(employee.get_birthDate(), createdEmployee.get().get_birthDate());
        assertEquals(employee.get_phone(), createdEmployee.get().get_phone());
        assertEquals(employee.get_address(), createdEmployee.get().get_address());
        assertEquals(employee.get_recruitmentDate(), createdEmployee.get().get_recruitmentDate());
        assertEquals(employee.get_email(), createdEmployee.get().get_email());

    }

    @Test
    public void update() throws EmployeeException {
        Employee employee = new Employee(
                "Aymen",
                "Servoy",
                LocalDate.of(2000, 1, 26),
                "06823347924",
                "sidi bouzid",
                LocalDate.of(2023, 9, 21),
                "servoy@gmail.com"
        );

        Optional<Employee> createdEmployee = employeeDao.create(employee);
        assertTrue(createdEmployee.isPresent());
        int matricule = createdEmployee.get().get_matricule();

        Employee updatedEmployee = new Employee(
                "UpdatedName",
                "UpdatedLastName",
                LocalDate.of(1995, 3, 10),
                "061234567890",
                "UpdatedAddress",
                LocalDate.of(2022, 8, 15),
                "updated.email@example.com"
        );

        Optional<Employee> updatedEmployeeOptional = employeeDao.update(matricule, updatedEmployee);
        assertTrue(updatedEmployeeOptional.isPresent());

        Optional<Employee> retrievedUpdatedEmployee = employeeDao.getByMatricule(matricule);
        assertTrue(retrievedUpdatedEmployee.isPresent());

        assertEquals(updatedEmployee.get_firstName(), retrievedUpdatedEmployee.get().get_firstName());
        assertEquals(updatedEmployee.get_lastName(), retrievedUpdatedEmployee.get().get_lastName());
        assertEquals(updatedEmployee.get_birthDate(), retrievedUpdatedEmployee.get().get_birthDate());
        assertEquals(updatedEmployee.get_phone(), retrievedUpdatedEmployee.get().get_phone());
        assertEquals(updatedEmployee.get_address(), retrievedUpdatedEmployee.get().get_address());
        assertEquals(updatedEmployee.get_recruitmentDate(), retrievedUpdatedEmployee.get().get_recruitmentDate());
        assertEquals(updatedEmployee.get_email(), retrievedUpdatedEmployee.get().get_email());

    }

    @Test
    public void testDelete() throws EmployeeException {
        boolean isDeleted = employeeDao.delete(testMatricule);

        assertTrue(isDeleted);
        Optional<Employee> deletedEmployee = employeeDao.getByMatricule(testMatricule);
        assertFalse(deletedEmployee.isPresent());
    }

    @Test
    public void testGetByMatricule() throws EmployeeException {
        Employee employee = new Employee(
                "Salah",
                "Mohammed",
                LocalDate.of(2003, 4, 23),
                "064782487924",
                "Jrayfat",
                LocalDate.of(2023, 6, 27),
                "salah@gmail.com"
        );

        Optional<Employee> createdEmployee = employeeDao.create(employee);
        assertTrue(createdEmployee.isPresent());

        Optional<Employee> retrievedEmployee = employeeDao.getByMatricule(createdEmployee.get().get_matricule());
        assertTrue(retrievedEmployee.isPresent());
        assertEmployeesEqual(createdEmployee.get(), retrievedEmployee.get());

    }

    @Test
    public void testGetAll() throws EmployeeException {
        List<Employee> allEmployees = employeeDao.getAll();
        assertNotNull(allEmployees);
        assertFalse(allEmployees.isEmpty());

        assertTrue(allEmployees.stream().anyMatch(e -> e.get_email().equals("mousta@gmail.com")));
    }

    @Test
    public void testFindByAttribute() throws EmployeeException {

        Employee employee1 = new Employee(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 15),
                "1234567890",
                "123 Main St",
                LocalDate.of(2023, 9, 21),
                "john.doe@example.com"
        );

        Employee employee2 = new Employee(
                "Jane",
                "Doe",
                LocalDate.of(1995, 5, 20),
                "9876543210",
                "456 Elm St",
                LocalDate.of(2023, 9, 21),
                "jane.doe@example.com"
        );

        employeeDao.create(employee1);
        employeeDao.create(employee2);

        List<Employee> foundEmployees = employeeDao.findByAttribute("John");
        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        assertEquals("Doe", foundEmployees.get(0).get_firstName());

        foundEmployees = employeeDao.findByAttribute("jane.doe@example.com");
        assertNotNull(foundEmployees);
        assertEquals(1, foundEmployees.size());
        assertEquals("jane.doe@example.com", foundEmployees.get(0).get_email());

        foundEmployees = employeeDao.findByAttribute("NonExistent");
        assertNotNull(foundEmployees);
        assertEquals(0, foundEmployees.size());
    }

    @Test
    public void testValidateMatricule() throws EmployeeException {
        boolean isValid = employeeDao.validateMatricule(testMatricule);

        assertTrue(isValid);

    }

    @AfterEach
    public void tearDown() {
        employeeDao.deleteAll();
    }

}