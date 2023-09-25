package org.youcode.easybank.services;

import org.youcode.easybank.dao.AccountDao;
import org.youcode.easybank.dao.ClientDao;
import org.youcode.easybank.dao.CurrentAccountDao;
import org.youcode.easybank.dao.SavingsAccountDao;
import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.*;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AccountService {

    public static void createAccount() throws ClientException, EmployeeException {
        Scanner sc = new Scanner(System.in);

        int matricule = EmployeeService.validateMatricule();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        Optional<Employee> retrievedEmployee = employeeDao.getByMatricule(matricule);


        if (matricule > 0) {
            while (true) {
                System.out.println("Do you have already a client created? [yes|no]");
                String answer = sc.nextLine();

                if (answer.equalsIgnoreCase("no")) {
                    System.out.println("Do you want to create a client? Type 'yes' if you want to or 'q' to quit");
                    String clientAnswer = sc.nextLine();
                    if (clientAnswer.equalsIgnoreCase("yes")) {
                        ClientService.createClient();
                    } else if (clientAnswer.equalsIgnoreCase("q")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }
                } else if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("Enter client's code: ");
                    int clientCode = sc.nextInt();
                    sc.nextLine();
                    ClientDao dao = new ClientDaoImpl();
                    Optional<Client> retrievedClient = dao.getByCode(clientCode);

                    if (retrievedClient.isPresent()) {
                        System.out.println("Do you want to create a savings account or current account? Type 's' for savings or 'c' for current");
                        String choice = sc.nextLine();

                        if (choice.equalsIgnoreCase("s")) {
                            AccountDao accountDao = new AccountDaoImpl();
                            SavingsAccountDao savingsAccountDao = new SavingsAccountDaoImpl();
                            System.out.println("Enter balance of the account: ");
                            double balance = sc.nextDouble();
                            System.out.println("Enter the interest rate: ");
                            double interestRate = sc.nextDouble();
                            sc.nextLine();
                            Account account = new Account(balance, retrievedEmployee.get(), retrievedClient.get());

                            try {
                                Optional<Account> createdAccount = accountDao.create(account);
                                if (createdAccount.isPresent()) {
                                    SavingsAccount savingsAccount = new SavingsAccount(createdAccount.get(), interestRate);
                                    savingsAccountDao.create(savingsAccount);
                                }
                            } catch (AccountException | SavingsAccountException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        } else if (choice.equalsIgnoreCase("c")) {
                            AccountDao accountDao = new AccountDaoImpl();
                            CurrentAccountDao currentAccountDao = new CurrentAccountDaoImpl();
                            System.out.println("Enter balance of the account: ");
                            double balance = sc.nextDouble();
                            System.out.println("Enter the overdraft : ");
                            double overdraft = sc.nextDouble();
                            sc.nextLine();
                            Account account = new Account(balance, retrievedEmployee.get(), retrievedClient.get());

                            try {
                                Optional<Account> createdAccount = accountDao.create(account);
                                if (createdAccount.isPresent()) {
                                    CurrentAccount currentAccount = new CurrentAccount(createdAccount.get(), overdraft);
                                    currentAccountDao.create(currentAccount);
                                }
                            } catch (AccountException | CurrentAccountException e) {
                                throw new RuntimeException(e);
                            }

                            break;
                        } else {
                            System.out.println("Invalid input. Try again.");
                        }
                    } else {
                        System.out.println("Client not found with code: " + clientCode);
                    }
                } else {
                    System.out.println("Invalid input. Try again.");
                }
            }
        }
    }

    public static void updateAccountStatus() {
        Scanner sc = new Scanner(System.in);

        boolean validAccountNumber = false;
        int accountNumber = 0;

        while (!validAccountNumber) {
            try {
                System.out.println("Enter the account number you want to update: ");
                accountNumber = sc.nextInt();
                sc.nextLine();
                validAccountNumber = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid account number format. Please enter a valid integer.");
                sc.nextLine();
            }
        }

        try {
            AccountDao accountDao = new AccountDaoImpl();
            Optional<Account> retrievedAccount = accountDao.getByAccountNumber(accountNumber);

            if (retrievedAccount.isPresent()) {

                System.out.println("Current Account Status: " + retrievedAccount.get().get_status());

                boolean validStatus = false;
                STATUS newStatus = null;
                while (!validStatus) {
                    System.out.println("Enter the new account status (ACTIVE or INACTIVE): ");
                    String newStatusStr = sc.nextLine().toUpperCase();
                    try {
                        newStatus = STATUS.valueOf(newStatusStr);
                        validStatus = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status. Please enter 'ACTIVE' or 'INACTIVE'.");
                    }
                }

                if (accountDao.updateStatus(accountNumber, newStatus)) {
                    System.out.println("Account status updated successfully!");
                } else {
                    System.out.println("Failed to update account status.");
                }
            } else {
                System.out.println("Account not found with account number: " + accountNumber);
            }
        } catch (Exception e) {
            System.out.println("Error updating account status: " + e.getMessage());
        }
    }

    public static void getAllSavingsAccounts() {
        SavingsAccountDao accountDao = new SavingsAccountDaoImpl();

        try {
            List<SavingsAccount> accounts = accountDao.getAll();

            if (!accounts.isEmpty()) {
                System.out.println("List of all accounts:");
                for (SavingsAccount account : accounts) {
                    System.out.println("Account Number: " + account.get_accountNumber());
                    System.out.println("Balance: " + account.get_balance());
                    System.out.println("Creation Date: " + account.get_creationDate());
                    System.out.println("Status: " + account.get_status());
                    System.out.println("Interest Rate: " + account.get_interestRate());
                    System.out.println("Client : " + account.get_client().get_lastName());
                    System.out.println("Employee : " + account.get_employee().get_lastName());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No accounts found.");
            }
        } catch (SavingsAccountException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }
    }

    public static void getAllCurrentAccounts() {
        CurrentAccountDao accountDao = new CurrentAccountDaoImpl();

        try {
            List<CurrentAccount> accounts = accountDao.getAll();

            if (!accounts.isEmpty()) {
                System.out.println("List of all accounts:");
                for (CurrentAccount account : accounts) {
                    System.out.println("Account Number: " + account.get_accountNumber());
                    System.out.println("Balance: " + account.get_balance());
                    System.out.println("Creation Date: " + account.get_creationDate());
                    System.out.println("Status: " + account.get_status());
                    System.out.println("Overdraft: " + account.get_overdraft());
                    System.out.println("Client : " + account.get_client().get_lastName());
                    System.out.println("Employee : " + account.get_employee().get_lastName());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No accounts found.");
            }
        } catch (CurrentAccountException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
        }
    }

    public static void getAllAccountsByCreationDate() {
        Scanner sc = new Scanner(System.in);

        try {

            System.out.println("Enter the creation date (yyyy-MM-dd): ");
            String dateStr = sc.nextLine();
            LocalDate date = LocalDate.parse(dateStr);

            AccountDao accountDao = new AccountDaoImpl();
            List<Account> accounts = accountDao.getByCreationDate(date);

            if (!accounts.isEmpty()) {
                System.out.println("Accounts created on " + date + ":");
                for (Account account : accounts) {
                    System.out.println("Account Number: " + account.get_accountNumber());
                    System.out.println("Balance: " + account.get_balance());
                    System.out.println("Creation Date: " + account.get_creationDate());
                    System.out.println("Status: " + account.get_status());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No accounts found with creation date: " + date);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving accounts by creation date: " + e.getMessage());
        }
    }

    public static void deleteAccount() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Enter the account number you want to delete: ");
            int accountNumber = sc.nextInt();
            sc.nextLine();

            AccountDao accountDao = new AccountDaoImpl();

            if (accountDao.delete(accountNumber)) {
                System.out.println("Account with account number " + accountNumber + " deleted successfully.");
            } else {
                System.out.println("Account with account number " + accountNumber + " not found or failed to delete.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }

    public static void getAccountsByStatus() {
        Scanner sc = new Scanner(System.in);

        try {
            STATUS status = null;

            while (status == null) {
                System.out.println("Enter the account status (ACTIVE or INACTIVE): ");
                String statusStr = sc.nextLine().toUpperCase();

                if (statusStr.equals("ACTIVE") || statusStr.equals("INACTIVE")) {
                    status = STATUS.valueOf(statusStr);
                } else {
                    System.out.println("Invalid status. Please enter 'ACTIVE' or 'INACTIVE'.");
                }
            }

            AccountDao accountDao = new AccountDaoImpl();
            List<Account> accounts = accountDao.getByStatus(status);

            if (!accounts.isEmpty()) {
                System.out.println("Accounts with status " + status + ":");
                for (Account account : accounts) {
                    System.out.println("Account Number: " + account.get_accountNumber());
                    System.out.println("Balance: " + account.get_balance());
                    System.out.println("Creation Date: " + account.get_creationDate());
                    System.out.println("Status: " + account.get_status());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No accounts found with status: " + status);
            }
        } catch (AccountException e) {
            System.out.println("Error retrieving accounts by status: " + e.getMessage());
        }
    }


}