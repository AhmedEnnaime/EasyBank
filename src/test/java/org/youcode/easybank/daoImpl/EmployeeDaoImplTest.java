package org.youcode.easybank.daoImpl;

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
        Optional<Employee> retrievedUpdatedEmployee = employeeDao.getByMatricule(testMatricule);
        assertTrue(retrievedUpdatedEmployee.isPresent());

        assertEquals("Mousta", retrievedUpdatedEmployee.get().get_firstName());
        assertEquals("Delegue", retrievedUpdatedEmployee.get().get_lastName());
        assertEquals("06473347924", retrievedUpdatedEmployee.get().get_phone());
        assertEquals("Jrayfat", retrievedUpdatedEmployee.get().get_address());
        assertEquals("mousta@gmail.com", retrievedUpdatedEmployee.get().get_email());
    }

    @Test
    public void testGetAll() throws EmployeeException {
        List<Employee> allEmployees = employeeDao.getAll();
        assertNotNull(allEmployees);
        assertFalse(allEmployees.isEmpty());

        assertTrue(allEmployees.stream().anyMatch(e -> e.get_firstName().equals("Mousta")));
        assertTrue(allEmployees.stream().anyMatch(e -> e.get_lastName().equals("Delegue")));
    }

}