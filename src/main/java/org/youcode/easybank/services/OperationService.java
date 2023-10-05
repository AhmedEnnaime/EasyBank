package org.youcode.easybank.services;

import org.youcode.easybank.dao.AccountDao;
import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.OperationDao;
import org.youcode.easybank.dao.SimpleOperationDao;
import org.youcode.easybank.dao.daoImpl.AccountDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.OperationDaoImpl;
import org.youcode.easybank.dao.daoImpl.SimpleOperationDaoImpl;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.entities.SimpleOperation;
import org.youcode.easybank.enums.OPERATION;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.OperationException;

import java.util.Optional;
import java.util.Scanner;

public class OperationService {

    public static void createOperation() throws AccountException, EmployeeException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your employee matricule: ");
            int employeeMatricule;

            try {
                employeeMatricule = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid employee matricule. Please enter a valid matricule.");
                continue;
            }

            EmployeeDao employeeDao = new EmployeeDaoImpl();
            Optional<Employee> employeeOptional = employeeDao.findByID(employeeMatricule);

            if (employeeOptional.isPresent()) {
                while (true) {
                    System.out.println("Enter 'D' for deposit or 'W' for withdrawal (or enter 'q' to quit): ");
                    String operationTypeInput = sc.nextLine();

                    if (operationTypeInput.equalsIgnoreCase("q")) {
                        break;
                    }

                    if (!operationTypeInput.equalsIgnoreCase("D") && !operationTypeInput.equalsIgnoreCase("W")) {
                        System.out.println("Invalid operation type. Please enter 'D' for deposit or 'W' for withdrawal.");
                        continue;
                    }
                    OPERATION operationType = null;
                    if (operationTypeInput.equalsIgnoreCase("D")) {
                        operationType = OPERATION.DEPOSIT;
                    } else if (operationTypeInput.equalsIgnoreCase("W")) {
                        operationType = OPERATION.WITHDRAWAL;
                    } else if (operationTypeInput.equalsIgnoreCase("P")) {
                        operationType = OPERATION.PAYMENT;
                    }

                    System.out.println("Enter the account number: ");
                    int accountNumber;

                    try {
                        accountNumber = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid account number. Please enter a valid account number.");
                        continue;
                    }

                    AccountDao accountDao = new AccountDaoImpl();
                    Optional<Account> accountOptional = accountDao.findByID(accountNumber);

                    if (!accountOptional.isPresent()) {
                        System.out.println("Account not found with account number: " + accountNumber);
                        continue;
                    }

                    System.out.println("Enter the operation amount: ");
                    double amount;

                    try {
                        amount = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid operation amount. Please enter a valid amount.");
                        continue;
                    }

                    try {
                        Operation operation = new Operation();
                        operation.set_amount(amount);
                        operation.set_employee(employeeOptional.get());

                        Account account = accountOptional.get();
                        double currentBalance = account.get_balance();

                        if (operationType == OPERATION.WITHDRAWAL && amount > currentBalance) {
                            System.out.println("Insufficient balance. Withdrawal cannot be processed.");
                            continue;
                        }

                        try {
                            OperationDao operationDao = new OperationDaoImpl();

                            Optional<Operation> createdOperation = operationDao.create(operation);


                            if (createdOperation.isPresent()) {
                                SimpleOperation simpleOperation = new SimpleOperation(createdOperation.get(), operationType, accountOptional.get());
                                SimpleOperationDao simpleOperationDao = new SimpleOperationDaoImpl();
                                Optional<SimpleOperation> createdSimpleOperation = simpleOperationDao.create(simpleOperation);
                                if(createdSimpleOperation.isPresent()) {
                                    boolean updated = accountDao.updateBalance(account, createdSimpleOperation.get());
                                    if (updated) {
                                        System.out.println("Operation completed successfully.");
                                        break;
                                    }
                                }

                            } else {
                                System.out.println("Operation failed to update the account balance.");
                            }
                        } catch (OperationException e) {
                            System.out.println("Error creating the operation: " + e.getMessage());
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid account number and operation amount.");
                    }
                }
                break;
            } else {
                System.out.println("Invalid employee matricule. Please enter a valid matricule.");
            }
        }
    }


    public static void getOperationByNumber() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the operation number you want to retrieve (or enter 'q' to quit): ");
            String operationNumberInput = sc.nextLine();

            if (operationNumberInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int operationNumber = Integer.parseInt(operationNumberInput);
                OperationDao dao = new OperationDaoImpl();
                Optional<Operation> existingOperation = dao.getByNumber(operationNumber);

                if (existingOperation.isPresent()) {
                    Operation operation = existingOperation.get();
                    System.out.println("Operation found:");
                    System.out.println("Operation Number: " + operation.get_operationNumber());
                    System.out.println("Amount: " + operation.get_amount());
                    System.out.println("Creation Date: " + operation.get_creationDate());
                    System.out.println("---------------------------");
                } else {
                    System.out.println("Operation not found with operation number: " + operationNumber);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid operation number or 'q' to quit.");
            } catch (OperationException e) {
                System.out.println("Error retrieving operation: " + e.getMessage());
            }
        }
    }

    public static void deleteOperation() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the operation number you want to delete (or enter 'q' to quit): ");
            String operationNumberInput = sc.nextLine();

            if (operationNumberInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int operationNumber = Integer.parseInt(operationNumberInput);
                OperationDao dao = new OperationDaoImpl();

                if (dao.delete(operationNumber)) {
                    System.out.println("Operation with operation number " + operationNumber + " deleted successfully.");
                } else {
                    System.out.println("Operation not found with operation number: " + operationNumber);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid operation number or 'q' to quit.");
            }
        }
    }
}
