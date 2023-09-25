package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AccountDaoImpl;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.SavingsAccountDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


public class SavingsAccountDaoImplTest {
    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private SavingsAccountDaoImpl savingsAccountDao;

    private Employee employee;

    private Client client;

    private int testAccountNumber;

    @BeforeEach
    public void setUp() throws EmployeeException, ClientException, AccountException {

        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        accountDao = new AccountDaoImpl(testConnection);


        savingsAccountDao = new SavingsAccountDaoImpl(testConnection);

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
    public void testCreate() throws SavingsAccountException {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.set_accountNumber(testAccountNumber);
        savingsAccount.set_interestRate(0.03);

        Optional<SavingsAccount> result = savingsAccountDao.create(savingsAccount);

        assertTrue(result.isPresent());

        SavingsAccount createdAccount = result.get();

        assertEquals(testAccountNumber, createdAccount.get_accountNumber());
        assertEquals(0.03, createdAccount.get_interestRate());

    }


    public void testGetAll() throws SavingsAccountException {
        List<SavingsAccount> allSavingsAccounts = savingsAccountDao.getAll();
        assertNotNull(allSavingsAccounts);

        assertFalse(allSavingsAccounts.isEmpty());

    }

    public void testUpdate() {

    }

    @AfterEach
    public void tearDown() {
        accountDao.deleteAll();
        savingsAccountDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
    }

}
