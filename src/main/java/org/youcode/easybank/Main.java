package org.youcode.easybank;

import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.RequestDaoImpl;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.OperationException;
import org.youcode.easybank.services.*;
import org.youcode.easybank.utils.*;
import org.youcode.easybank.views.AgencyView;
import org.youcode.easybank.views.MissionAssignmentView;
import org.youcode.easybank.views.MissionView;
import org.youcode.easybank.views.RequestView;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Welcome to Your Bank Application!");
                System.out.println("1. Employee Management");
                System.out.println("2. Client Management");
                System.out.println("3. Account Management");
                System.out.println("4. Operation Management");
                System.out.println("5. Mission Management");
                System.out.println("6. Mission Assignment Management");
                System.out.println("7. Agency Management");
                System.out.println("8. Requests Management");
                System.out.println("9. Payments Management");
                System.out.println("10. Transfer employee Management");
                System.out.println("11. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        EmployeeUtils.employeeManagementMenu();
                        break;
                    case 2:
                        ClientUtils.clientManagementMenu();
                        break;
                    case 3:
                        AccountUtils.accountManagementMenu();
                        break;
                    case 4:
                        OperationUtils.operationManagementMenu();
                        break;
                    case 5:
                        MissionUtils.missionManagementMenu();
                        break;
                    case 6:
                        MissionAssignmentUtils.missionAssignmentManagementMenu();
                        break;
                    case 7:
                        AgencyUtils.agencyManagementMenu();
                        break;
                    case 8:
                        RequestUtils.requestsManagementMenu();
                        break;
                    case 9:
                        PaymentUtils.PaymentManagementMenu();
                        break;
                    case 10:
                        TransferEmployeeUtils.transferEmployeeAgencyManagement();
                        break;
                    case 11:
                        System.out.println("Exiting the application. Goodbye!");
                        DBConnection.closeConnection();
                        sc.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }
}