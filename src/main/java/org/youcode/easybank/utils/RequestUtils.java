package org.youcode.easybank.utils;

import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.RequestDaoImpl;
import org.youcode.easybank.services.RequestService;
import org.youcode.easybank.services.SimulationService;
import org.youcode.easybank.views.RequestView;

import java.util.Scanner;

public class RequestUtils {

    public static void requestsManagementMenu() {
        Scanner sc = new Scanner(System.in);
        RequestDaoImpl requestDao = new RequestDaoImpl();
        RequestService requestService = new RequestService(requestDao);
        ClientDaoImpl clientDao = new ClientDaoImpl();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        SimulationService simulationService = new SimulationService(employeeDao);
        RequestView requestView = new RequestView(requestService, clientDao, simulationService);

        while (true) {
            System.out.println("Requests Management Menu:");
            System.out.println("1. Create request");
            System.out.println("2. Display request By number");
            System.out.println("3. Display all requests");
            System.out.println("4. Display requests by state");
            System.out.println("5. Update state of a request");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    requestView.createRequest();
                    break;
                case 2:
                    requestView.getRequestByID();
                    break;
                case 3:
                    requestView.getAllRequests();
                    break;
                case 4:
                    requestView.getRequestsByState();
                    break;
                case 5:
                    requestView.updateRequestState();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
