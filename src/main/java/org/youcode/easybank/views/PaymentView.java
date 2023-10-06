package org.youcode.easybank.views;

import org.youcode.easybank.dao.daoImpl.AccountDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.OperationDaoImpl;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.entities.Payment;
import org.youcode.easybank.services.PaymentService;

import java.util.Optional;
import java.util.Scanner;

public class PaymentView {

    private PaymentService paymentService;

    private EmployeeDaoImpl employeeDao;

    private AccountDaoImpl accountDao;

    private OperationDaoImpl operationDao;

    private Operation operation;

    public PaymentView(PaymentService paymentService, EmployeeDaoImpl employeeDao, AccountDaoImpl accountDao, Operation operation, OperationDaoImpl operationDao) {
        this.paymentService = paymentService;
        this.employeeDao = employeeDao;
        this.accountDao = accountDao;
        this.operation = operation;
        this.operationDao = operationDao;
    }

    public void createPayment() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter employee's matricule or 'q' to quit ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);

                Optional<Employee> retrievedEmployee = employeeDao.findByID(matricule);
                System.out.println("Enter the number of account you want to send money from: ");
                int number = sc.nextInt();
                sc.nextLine();
                Optional<Account> retrievedAccount = accountDao.findByID(number);
                System.out.println("Enter the amount of money you want to transfer: ");
                double amount = sc.nextDouble();
                sc.nextLine();
                System.out.println("Enter the number of destination account: ");
                int destinationNumber = sc.nextInt();
                sc.nextLine();
                Optional<Account> retrievedDestinationAccount = accountDao.findByID(destinationNumber);

                operation.set_amount(amount);
                operation.set_employee(retrievedEmployee.get());

                operationDao.create(operation);

                Payment payment = new Payment(operation, retrievedAccount.get(), retrievedDestinationAccount.get());

                try {
                    Payment createdPayment = paymentService.createPayment(payment, operation);
                    if (createdPayment != null) {
                        System.out.println("Payment passed successfully");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void deletePayment() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter payment's id or 'q' to quit ");
            String idInput = sc.nextLine();

            if (idInput.equalsIgnoreCase("q")) {
                break;
            }
            int id = Integer.parseInt(idInput);

            if (paymentService.deletePayment(id)) {
                System.out.println("Payment deleted successfully");
            }else {
                System.out.println("Payment failed to delete");
            }
        }
    }

}
