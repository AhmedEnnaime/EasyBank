package org.youcode.easybank.utils;

import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.TransferDaoImpl;
import org.youcode.easybank.services.MissionAssignmentService;
import org.youcode.easybank.services.TransferService;
import org.youcode.easybank.views.TransferView;

import java.util.Scanner;

public class TransferEmployeeUtils {

    public static void transferEmployeeAgencyManagement() {
        Scanner sc = new Scanner(System.in);
        TransferDaoImpl transferDao = new TransferDaoImpl();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        AgencyDaoImpl agencyDao = new AgencyDaoImpl();
        TransferService transferService = new TransferService(transferDao, employeeDao);
        TransferView transferView = new TransferView(transferService, employeeDao, agencyDao);

        while (true) {
            System.out.println("Transfer Employee Agency Management Menu:");
            System.out.println("1. Transfer Employee to new Agency");
            System.out.println("2. Display historical of employee's transfers between agencies");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    transferView.transferEmployeeToAgency();
                    break;
                case 2:
                    transferView.getEmployeeHistoricalTransfers();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
