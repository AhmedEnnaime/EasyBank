package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.enums.OPERATION;
import org.youcode.easybank.exceptions.OperationException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentDaoImplTest {
    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private OperationDaoImpl operationDao;

    private AgencyDaoImpl agencyDao;

    private Employee employee;

    private Client client;

    private Account account;

    private Agency agency;

    private PaymentDaoImpl paymentDao;

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

        paymentDao = new PaymentDaoImpl(testConnection);

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
    public void testCreate() throws OperationException {
        Operation operation = new Operation(
                600,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);
        assertTrue(createdOperation.isPresent());

        Account destination_account = new Account(
                8700,
                employee,
                client,
                agency
        );

        Account from_account = new Account(
                10000,
                employee,
                client,
                agency
        );

        Optional<Account> createdFromAccount = accountDao.create(from_account);
        Optional<Account> createdDestinationAccount = accountDao.create(destination_account);

        assertTrue(createdFromAccount.isPresent());
        assertTrue(createdDestinationAccount.isPresent());

        Payment payment = new Payment(
                createdOperation.get(),
                createdFromAccount.get(),
                createdDestinationAccount.get()
        );

        Optional<Payment> createdPayment = paymentDao.create(payment);
        assertTrue(createdPayment.isPresent());
    }

    @Test
    public void testUpdateDestinationBalance() {

        Operation operation = new Operation(
                300,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);


        Account destinationAccount = new Account(
                5000,
                employee,
                client,
                agency
        );

        Optional<Account> createdDestinationAccount = accountDao.create(destinationAccount);
        assertTrue(createdDestinationAccount.isPresent());

        Account fromAccount = new Account(
                5000,
                employee,
                client,
                agency
        );

        Optional<Account> createdFromAccount = accountDao.create(fromAccount);
        assertTrue(createdFromAccount.isPresent());

        Payment payment = new Payment(
                createdOperation.get(),
                createdFromAccount.get(),
                createdDestinationAccount.get()
        );

        Optional<Payment> createdPayment = paymentDao.create(payment);
        assertTrue(createdPayment.isPresent());

        boolean updated = paymentDao.updateDestinationBalance(createdPayment.get());

        assertTrue(updated);
    }

    @Test
    public void testUpdateFromAccountBalance() {

        Operation operation = new Operation(
                300,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);


        Account destinationAccount = new Account(
                5000,
                employee,
                client,
                agency
        );

        Optional<Account> createdDestinationAccount = accountDao.create(destinationAccount);
        assertTrue(createdDestinationAccount.isPresent());

        Account fromAccount = new Account(
                5000,
                employee,
                client,
                agency
        );

        Optional<Account> createdFromAccount = accountDao.create(fromAccount);
        assertTrue(createdFromAccount.isPresent());

        Payment payment = new Payment(
                createdOperation.get(),
                createdFromAccount.get(),
                createdDestinationAccount.get()
        );

        Optional<Payment> createdPayment = paymentDao.create(payment);
        assertTrue(createdPayment.isPresent());

        boolean updated = paymentDao.updateFromAccountBalance(createdPayment.get());

        assertTrue(updated);
    }

    @Test
    public void testDelete() {
        Operation operation = new Operation(
                300,
                employee
        );

        Optional<Operation> createdOperation = operationDao.create(operation);

        Account fromAccount = new Account(
                5000,
                employee,
                client,
                agency
        );

        Optional<Account> createdFromAccount = accountDao.create(fromAccount);
        assertTrue(createdFromAccount.isPresent());

        Account destinationAccount = new Account(
                5000,
                employee,
                client,
                agency
        );

        Optional<Account> createdDestinationAccount = accountDao.create(destinationAccount);
        assertTrue(createdDestinationAccount.isPresent());

        Payment payment = new Payment(
                createdOperation.get(),
                createdFromAccount.get(),
                createdDestinationAccount.get()
        );

        Optional<Payment> createdPayment = paymentDao.create(payment);
        assertTrue(createdPayment.isPresent());

        boolean deleted = paymentDao.delete(createdPayment.get().getId());

        assertTrue(deleted);

    }


    @AfterEach
    public void tearDown() {
        operationDao.deleteAll();
        paymentDao.deleteAll();
        accountDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
    }
}
