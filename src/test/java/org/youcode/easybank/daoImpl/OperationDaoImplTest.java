package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.enums.OPERATION;
import org.youcode.easybank.exceptions.OperationException;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class OperationDaoImplTest {

    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private OperationDaoImpl operationDao;

    private AgencyDaoImpl agencyDao;

    private Employee employee;

    private Client client;

    private Account account;

    private Agency agency;

    private SimpleOperationDaoImpl simpleOperationDao;

    private int testAccountNumber;

    private int testOperationNumber;

    @BeforeEach
    public void setUp() throws OperationException {
        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        accountDao = new AccountDaoImpl(testConnection);

        operationDao = new OperationDaoImpl(testConnection);

        agencyDao = new AgencyDaoImpl(testConnection);

        simpleOperationDao = new SimpleOperationDaoImpl(testConnection);

        agency = new Agency(
                "YouCode",
                "test address",
                "05248137133"
        );

        agencyDao.create(agency);

        employee = new Employee(
                "Aymen",
                "Servoy",
                LocalDate.of(2000, 1, 26),
                "06823347924",
                "sidi bouzid",
                LocalDate.of(2023, 9, 21),
                "servoy@gmail.com",
                agency
        );

        employeeDao.create(employee);

        client = new Client(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat",
                employee
        );

        clientDao.create(client);

        account = new Account(
                8700,
                employee,
                client,
                agency
        );

        accountDao.create(account);

        Operation operation = new Operation(
                300,
                employee
        );

        operationDao.create(operation);

        testAccountNumber = account.get_accountNumber();
        testOperationNumber = operation.get_operationNumber();

    }

    @Test
    public void testCreateSimpleOperation() throws OperationException {
        Operation operation = new Operation(
                600,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);
        assertTrue(createdOperation.isPresent());

        SimpleOperation simpleOperation = new SimpleOperation(
                createdOperation.get(),
                OPERATION.DEPOSIT,
                account
        );

        Optional<SimpleOperation> createdSimpleOperation = simpleOperationDao.create(simpleOperation);
        assertTrue(createdSimpleOperation.isPresent());

    }

    @Test
    public void testCreate() throws OperationException {
        Operation operation = new Operation(
                600,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);
        assertTrue(createdOperation.isPresent());

        assertEquals(operation.get_amount(), createdOperation.get().get_amount());
    }

    @Test
    public void testDelete() throws OperationException {
        boolean isDeleted = operationDao.delete(testOperationNumber);

        System.out.println("Operation number : " + testOperationNumber);
        assertTrue(isDeleted);
        Optional<Operation> deletedOperation = operationDao.getByNumber(testOperationNumber);
        assertFalse(deletedOperation.isPresent());
    }

    @Test
    public void testGetByNumber() throws OperationException {
        Operation operation = new Operation(
                1300,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);
        assertTrue(createdOperation.isPresent());

        Optional<Operation> retrievedOperation = operationDao.getByNumber(createdOperation.get().get_operationNumber());
        assertTrue(retrievedOperation.isPresent());
    }


    @AfterEach
    public void tearDown() {
        operationDao.deleteAll();
        accountDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
    }
}
