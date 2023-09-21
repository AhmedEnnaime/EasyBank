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
import java.util.Optional;

public class EmployeeDaoImplTest {
    private EmployeeDaoImpl employeeDao;

    @BeforeEach
    public void setUp() {
        Connection testConnection = DBTestConnection.establishTestConnection();

        employeeDao = new EmployeeDaoImpl(testConnection);

    }

    @Test
    public void testCreate() throws EmployeeException {
        Employee employee = new Employee();
        employee.set_firstName("Abdelali");
        employee.set_lastName("Hotgame");
        employee.set_birthDate(LocalDate.of(1990, 5, 15));
        employee.set_phone("0682332783924");
        employee.set_address("hay anass");
        employee.set_recruitmentDate(LocalDate.of(2023, 9, 21));
        employee.set_email("hotgam@gmail.com");

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
        Employee employee = new Employee();
        employee.set_firstName("Aymen");
        employee.set_lastName("Servoy");
        employee.set_birthDate(LocalDate.of(2000, 1, 26));
        employee.set_phone("06823347924");
        employee.set_address("sidi bouzid");
        employee.set_recruitmentDate(LocalDate.of(2023, 9, 21));
        employee.set_email("servoy@gmail.com");

        Optional<Employee> createdEmployee = employeeDao.create(employee);
        assertTrue(createdEmployee.isPresent());
        int matricule = createdEmployee.get().get_matricule();

        Employee updatedEmployee = new Employee();
        updatedEmployee.set_firstName("UpdatedName");
        updatedEmployee.set_lastName("UpdatedLastName");
        updatedEmployee.set_birthDate(LocalDate.of(1995, 3, 10));
        updatedEmployee.set_phone("061234567890");
        updatedEmployee.set_address("UpdatedAddress");
        updatedEmployee.set_recruitmentDate(LocalDate.of(2022, 8, 15));
        updatedEmployee.set_email("updated.email@example.com");

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
import java.util.Optional;

public class EmployeeDaoImplTest {
    private EmployeeDaoImpl employeeDao;

    @BeforeEach
    public void setUp() {
        Connection testConnection = DBTestConnection.establishTestConnection();

        employeeDao = new EmployeeDaoImpl(testConnection);

    }

    @Test
    public void testCreate() throws EmployeeException {
        Employee employee = new Employee();
        employee.set_firstName("Abdelali");
        employee.set_lastName("Hotgame");
        employee.set_birthDate(LocalDate.of(1990, 5, 15));
        employee.set_phone("0682332783924");
        employee.set_address("hay anass");
        employee.set_recruitmentDate(LocalDate.of(2023, 9, 21));
        employee.set_email("hotgam@gmail.com");

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
        Employee employee = new Employee();
        employee.set_firstName("Aymen");
        employee.set_lastName("Servoy");
        employee.set_birthDate(LocalDate.of(2000, 1, 26));
        employee.set_phone("06823347924");
        employee.set_address("sidi bouzid");
        employee.set_recruitmentDate(LocalDate.of(2023, 9, 21));
        employee.set_email("servoy@gmail.com");

        Optional<Employee> createdEmployee = employeeDao.create(employee);
        assertTrue(createdEmployee.isPresent());
        int matricule = createdEmployee.get().get_matricule();

        Employee updatedEmployee = new Employee();
        updatedEmployee.set_firstName("UpdatedName");
        updatedEmployee.set_lastName("UpdatedLastName");
        updatedEmployee.set_birthDate(LocalDate.of(1995, 3, 10));
        updatedEmployee.set_phone("061234567890");
        updatedEmployee.set_address("UpdatedAddress");
        updatedEmployee.set_recruitmentDate(LocalDate.of(2022, 8, 15));
        updatedEmployee.set_email("updated.email@example.com");

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


}
