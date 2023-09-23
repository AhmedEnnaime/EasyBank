package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AccountDaoImpl;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDaoImplTest {

    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private Employee employee;

    private Client client;

    private int testAccountNumber;

    @BeforeEach
    public void setUp() throws ClientException, EmployeeException {
        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        accountDao = new AccountDaoImpl(testConnection);

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

        clientDao.create(client);

        Account account = new Account(
                8700,
                employee,
                client
        );

        testAccountNumber = account.get_accountNumber();
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
    public void testupdate() throws AccountException {
        Account updatedAccount = new Account(
                4000,
                employee,
                client
        );

        Optional<Account> updatedAccountOptional = accountDao.update(testAccountNumber, updatedAccount);
        assertTrue(updatedAccountOptional.isPresent());

        Optional<Account> retrievedUpdatedAccount = accountDao.getByAccountNumber(testAccountNumber);
        assertTrue(retrievedUpdatedAccount.isPresent());

        assertEquals(updatedAccount.get_balance(), retrievedUpdatedAccount.get().get_balance());

    }

    public void testDelete() {

    }


}
