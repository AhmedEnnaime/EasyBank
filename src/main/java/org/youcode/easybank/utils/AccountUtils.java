package org.youcode.easybank.utils;

import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.services.AccountService;

import java.util.Scanner;

public class AccountUtils {

    public static void accountManagementMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Account Management Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Update Account");
            System.out.println("3. Get Account by Operation Number");
            System.out.println("4. Update Account Status");
            System.out.println("5. Display all savings accounts");
            System.out.println("6. Display all current accounts");
            System.out.println("7. Display accounts by their creation date");
            System.out.println("8. Delete Account");
            System.out.println("9. Get Accounts By Their Status");
            System.out.println("10. Get Account By Account Number");
            System.out.println("11. Get Accounts By Client");
            System.out.println("12. Get All Accounts (Current and Savings)");
            System.out.println("13. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    try {
                        AccountService.createAccount();
                    } catch (ClientException | EmployeeException a) {
                        a.printStackTrace();
                    }
                    break;
                case 2:
                    AccountService.updateAccount();
                    break;
//                    case 3:
//                        AccountService.getAccountByOperationNumber();
//                        break;
                case 4:
                    AccountService.updateAccountStatus();
                    break;
                case 5:
                    AccountService.getAllSavingsAccounts();
                    break;
                case 6:
                    AccountService.getAllCurrentAccounts();
                    break;
                case 7:
                    AccountService.getAllAccountsByCreationDate();
                    break;
                case 8:
                    AccountService.deleteAccount();
                    break;
                case 9:
                    AccountService.getAccountsByStatus();
                    break;
                case 10:
                    AccountService.getAccountByAccountNumber();
                    break;
                case 11:
                    try {
                        AccountService.getAccountsByClient();
                    } catch (AccountException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 12:
                    AccountService.getAllAccounts();
                    break;
                case 13:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
