package org.youcode.easybank.utils;

import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.services.OperationService;

import java.util.Scanner;

public class OperationUtils {

    public static void operationManagementMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Operation Management Menu:");
            System.out.println("1. Create Simple Operation");
            System.out.println("2. Get Operation By Number");
            System.out.println("3. Delete Operation");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    try {
                        OperationService.createOperation();
                    } catch (AccountException | EmployeeException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 2:
                    OperationService.getOperationByNumber();
                    break;
                case 3:
                    OperationService.deleteOperation();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
