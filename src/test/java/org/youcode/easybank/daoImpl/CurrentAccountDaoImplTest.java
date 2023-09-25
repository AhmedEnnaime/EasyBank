package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.CurrentAccountException;
import org.youcode.easybank.exceptions.EmployeeException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentAccountDaoImplTest {

    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private CurrentAccountDaoImpl currentAccountDao;

    private Employee employee;

    private Client client;

    private int testAccountNumber;

    @BeforeEach
    public void setUp() throws EmployeeException, ClientException, AccountException {

        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        accountDao = new AccountDaoImpl(testConnection);

        currentAccountDao = new CurrentAccountDaoImpl(testConnection);

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

    @AfterEach
    public void tearDown() {
        accountDao.deleteAll();
        currentAccountDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
    }
}
