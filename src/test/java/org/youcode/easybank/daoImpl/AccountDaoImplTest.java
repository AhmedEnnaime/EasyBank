package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AccountDaoImpl;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.OperationDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.enums.OPERATION;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.OperationException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDaoImplTest {

    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private OperationDaoImpl operationDao;

    private Employee employee;

    private Operation operation;

    private Client client;
    private Client client2;

    private int testOperationNumber;
    private int testAccountNumber;

    @BeforeEach
    public void setUp() throws ClientException, EmployeeException, AccountException, OperationException {
        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        accountDao = new AccountDaoImpl(testConnection);

        operationDao = new OperationDaoImpl(testConnection);

        employee = new Employee(
                "Aymen",
                "Servoy",
                LocalDate.of(2000, 1, 26),
                "06823347924",
                "sidi bouzid",
                LocalDate.of(2023, 9, 21),
                "servoy@gmail.com"
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

        client2 = new Client(
                "Mouad",
                "Charaf",
                LocalDate.of(1997, 7, 17),
                "0632738247924",
                "Jrayfat",
                employee
        );

        clientDao.create(client);
        clientDao.create(client2);

        Account account = new Account(
                8700,
                employee,
                client
        );

        accountDao.create(account);

        testAccountNumber = account.get_accountNumber();

        operation = new Operation(
                300,
                OPERATION.DEPOSIT,
                employee,
                account
        );

        operationDao.create(operation);

        testOperationNumber = operation.get_operationNumber();
    }

    public void assertAccountsEqual(Account expected, Account actual) {
        assertEquals(expected.get_balance(), actual.get_balance());
        assertEquals(expected.get_status(), actual.get_status());
        assertEquals(expected.get_creationDate(), actual.get_creationDate());
    }

    @Test
    public void testCreate() throws AccountException {
        Account account = new Account(
                1000,
                employee,
                client
        );

        Optional<Account> createdAccount = accountDao.create(account);
        assertTrue(createdAccount.isPresent());
        assertEquals(account.get_balance(), createdAccount.get().get_balance());
        assertEquals(account.get_creationDate(), createdAccount.get().get_creationDate());
        assertEquals(account.get_status(), createdAccount.get().get_status());

        assertNotNull(account.get_employee());
        assertNotNull(account.get_client());
    }

    @Test
    public void testDelete() throws AccountException {
        boolean isDeleted = accountDao.delete(testAccountNumber);
        assertTrue(isDeleted);

        Optional<Account> deletedAccount = accountDao.getByAccountNumber(testAccountNumber);
        assertFalse(deletedAccount.isPresent());
    }


    @Test
    public void testGetByCreationDate() throws AccountException {
        LocalDate today = LocalDate.now();

        Account accountToday = new Account(8700, employee, client);

        accountDao.create(accountToday);

        Optional<Account> retrievedAccount = accountDao.getByAccountNumber(accountToday.get_accountNumber());
        assertTrue(retrievedAccount.isPresent());
        List<Account> accountsCreatedToday = accountDao.getByCreationDate(today);
        assertNotNull(accountsCreatedToday);

        assertEquals(2, accountsCreatedToday.size());
        assertAccountsEqual(retrievedAccount.get(), accountsCreatedToday.get(1));
    }

    @Test
    public void testUpdate() throws AccountException {

        Account account = new Account(
                1000,
                employee,
                client
        );

        Optional<Account> createdAccount = accountDao.create(account);
        assertTrue(createdAccount.isPresent());

        int accountNumber = createdAccount.get().get_accountNumber();

        Account updatedAccount = new Account(
                2000
        );

        Optional<Account> updatedOptional = accountDao.update(accountNumber, updatedAccount);
        assertTrue(updatedOptional.isPresent());

        Optional<Account> retrievedAccount = accountDao.getByAccountNumber(accountNumber);
        assertTrue(retrievedAccount.isPresent());

        assertEquals(updatedAccount.get_balance(), retrievedAccount.get().get_balance());
    }


    @Test
    public void testGetByStatus() throws AccountException {

        Account activeAccount1 = new Account(1000, employee, client);
        activeAccount1.set_status(STATUS.ACTIVE);

        Account activeAccount2 = new Account(1500, employee, client2);
        activeAccount2.set_status(STATUS.ACTIVE);

        accountDao.create(activeAccount1);
        accountDao.create(activeAccount2);

        List<Account> activeAccounts = accountDao.getByStatus(STATUS.ACTIVE);
        assertNotNull(activeAccounts);

        assertEquals(3, activeAccounts.size());
        assertTrue(activeAccounts.stream().allMatch(a -> a.get_status().equals(STATUS.ACTIVE)));
    }

    @Test
    public void testUpdateStatus() throws AccountException {
        Account account = new Account(1000, employee, client);
        Optional<Account> createdAccount = accountDao.create(account);
        assertTrue(createdAccount.isPresent());

        int accountNumber = createdAccount.get().get_accountNumber();
        boolean isUpdated = accountDao.updateStatus(accountNumber, STATUS.INACTIVE);
        assertTrue(isUpdated);

        Optional<Account> updatedAccount = accountDao.getByAccountNumber(accountNumber);
        assertTrue(updatedAccount.isPresent());

        assertEquals(STATUS.INACTIVE, updatedAccount.get().get_status());
    }

    @Test
    public void testGetClientAccounts() throws AccountException, ClientException, EmployeeException {

        Employee employee = new Employee(
                "Aymen",
                "Servoy",
                LocalDate.of(2000, 1, 26),
                "06823347924",
                "sidi bouzid",
                LocalDate.of(2023, 9, 21),
                "servoy@gmail.com"
        );

        employeeDao.create(employee);

        Client client = new Client(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat",
                employee
        );

        clientDao.create(client);

        Account account = new Account(
                8700,
                employee,
                client
        );

        accountDao.create(account);

        List<Optional<Account>> clientAccounts = accountDao.getClientAccounts(client);
        assertNotNull(clientAccounts);
        assertEquals(1, clientAccounts.size());

        Optional<Account> retrievedAccount = clientAccounts.get(0);
        assertTrue(retrievedAccount.isPresent());
        assertEquals(account.get_balance(), retrievedAccount.get().get_balance());
        assertEquals(LocalDate.now(), retrievedAccount.get().get_creationDate());
        assertEquals(STATUS.ACTIVE, retrievedAccount.get().get_status());
    }

    @Test
    public void testUpdateBalance() throws AccountException {
        double initialBalance = 1000.0;
        Account account = new Account(initialBalance, employee, client);
        Optional<Account> createdAccount = accountDao.create(account);
        assertTrue(createdAccount.isPresent());

        double depositAmount = 500.0;
        Operation depositOperation = new Operation(depositAmount, OPERATION.DEPOSIT, employee, createdAccount.get());

        boolean isUpdated = accountDao.updateBalance(createdAccount.get(), depositOperation);
        assertTrue(isUpdated);

        Optional<Account> updatedAccount = accountDao.getByAccountNumber(createdAccount.get().get_accountNumber());
        assertTrue(updatedAccount.isPresent());

        double expectedBalance = initialBalance + depositAmount;
        assertEquals(expectedBalance, updatedAccount.get().get_balance());

        double withdrawalAmount = 300.0;
        Operation withdrawalOperation = new Operation(withdrawalAmount, OPERATION.WITHDRAWAL, employee, updatedAccount.get());

        isUpdated = accountDao.updateBalance(updatedAccount.get(), withdrawalOperation);
        assertTrue(isUpdated);

        updatedAccount = accountDao.getByAccountNumber(createdAccount.get().get_accountNumber());
        assertTrue(updatedAccount.isPresent());

        expectedBalance -= withdrawalAmount;
        assertEquals(expectedBalance, updatedAccount.get().get_balance());
    }

    @Test
    public void testGetByOperationNumber() throws AccountException {
        Optional<Account> retrievedAccount = accountDao.getByOperationNumber(testOperationNumber);

        assertTrue(retrievedAccount.isPresent());
        assertEquals(8700, retrievedAccount.get().get_balance());
        assertEquals(STATUS.ACTIVE, retrievedAccount.get().get_status());
        assertEquals(LocalDate.now(), retrievedAccount.get().get_creationDate());
    }


    @AfterEach
    public void tearDown() {
        accountDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
        operationDao.deleteAll();
    }

}
