package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.exceptions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CurrentAccountDaoImplTest {

    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private CurrentAccountDaoImpl currentAccountDao;

    private AgencyDaoImpl agencyDao;

    private Employee employee;

    private Client client;

    private Agency agency;

    private int testAccountNumber;

    @BeforeEach
    public void setUp() {

        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        accountDao = new AccountDaoImpl(testConnection);

        currentAccountDao = new CurrentAccountDaoImpl(testConnection);

        agencyDao = new AgencyDaoImpl(testConnection);

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

        Account account = new Account(
                8700,
                employee,
                client,
                agency
        );

        accountDao.create(account);

        testAccountNumber = account.get_accountNumber();

    }

    @Test
    public void testCreate() throws CurrentAccountException {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.set_accountNumber(testAccountNumber);
        currentAccount.set_overdraft(0.03);

        Optional<CurrentAccount> result = currentAccountDao.create(currentAccount);

        assertTrue(result.isPresent());

        CurrentAccount createdAccount = result.get();

        assertEquals(testAccountNumber, createdAccount.get_accountNumber());
        assertEquals(0.03, createdAccount.get_overdraft());
    }

    public void testGetAll() throws CurrentAccountException {
        List<CurrentAccount> allCurrentAccounts = currentAccountDao.getAll();
        assertNotNull(allCurrentAccounts);

        assertFalse(allCurrentAccounts.isEmpty());

    }

    @Test
    public void testUpdate() throws CurrentAccountException {
        // Create a new current account
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.set_accountNumber(testAccountNumber);
        currentAccount.set_overdraft(1000.0);

        Optional<CurrentAccount> createdAccount = currentAccountDao.create(currentAccount);
        assertTrue(createdAccount.isPresent());

        Optional<CurrentAccount> retrievedAccount = currentAccountDao.update(testAccountNumber, currentAccount);
        assertTrue(retrievedAccount.isPresent());

        double newOverdraft = 1500.0;
        retrievedAccount.get().set_overdraft(newOverdraft);

        Optional<CurrentAccount> updatedAccount = currentAccountDao.update(testAccountNumber, retrievedAccount.get());
        assertTrue(updatedAccount.isPresent());

        Optional<CurrentAccount> finalAccount = currentAccountDao.update(testAccountNumber, retrievedAccount.get());
        assertTrue(finalAccount.isPresent());

        assertEquals(newOverdraft, finalAccount.get().get_overdraft());
    }




    @AfterEach
    public void tearDown() {
        accountDao.deleteAll();
        currentAccountDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
    }
}
