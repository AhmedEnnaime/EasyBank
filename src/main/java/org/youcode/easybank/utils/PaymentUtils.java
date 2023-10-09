package org.youcode.easybank.utils;

import org.youcode.easybank.dao.daoImpl.*;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.services.PaymentService;
import org.youcode.easybank.services.TransferService;
import org.youcode.easybank.views.PaymentView;
import org.youcode.easybank.views.TransferView;

import java.util.Scanner;

public class PaymentUtils {

    public static void PaymentManagementMenu() {
        Scanner sc = new Scanner(System.in);
        PaymentDaoImpl paymentDao = new PaymentDaoImpl();
        OperationDaoImpl operationDao = new OperationDaoImpl();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        AccountDaoImpl accountDao = new AccountDaoImpl();
        Operation operation = new Operation();
        PaymentService paymentService = new PaymentService(paymentDao, operationDao);
        PaymentView paymentView = new PaymentView(paymentService, employeeDao, accountDao, operation, operationDao);

        while (true) {
            System.out.println("Transfer Employee Agency Management Menu:");
            System.out.println("1. Make a payment ");
            System.out.println("2. Delete a payment");
            System.out.println("3. Get payment by id");
            System.out.println("4. Display all payments");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    paymentView.createPayment();
                    break;
                case 2:
                    paymentView.deletePayment();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
