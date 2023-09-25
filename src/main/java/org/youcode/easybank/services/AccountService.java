package org.youcode.easybank.services;

import org.youcode.easybank.dao.AccountDao;
import org.youcode.easybank.dao.ClientDao;
import org.youcode.easybank.dao.CurrentAccountDao;
import org.youcode.easybank.dao.SavingsAccountDao;
import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.exceptions.*;

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
                    sc.nextLine(); // Consume newline character
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

}
