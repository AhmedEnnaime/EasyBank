package org.youcode.easybank.utils;

import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.services.AgencyService;
import org.youcode.easybank.views.AgencyView;

import java.util.Scanner;

public class AgencyUtils {
    public static void agencyManagementMenu() {
        Scanner sc = new Scanner(System.in);
        AgencyDaoImpl agencyDao = new AgencyDaoImpl();
        AgencyService agencyService = new AgencyService(agencyDao);
        AgencyView agencyView = new AgencyView(agencyService, agencyDao);

        System.out.println("Agency Management Menu:");
        System.out.println("1. Create Agency");
        System.out.println("2. Get Agency By code");
        System.out.println("3. Delete Agency");
        System.out.println("4. Get All agencies");
        System.out.println("5. Update agency");
        System.out.println("6. Get agency by address");
        System.out.println("7. Back to Main menu");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                agencyView.createAgency();
                break;
            case 2:
                agencyView.getAgencyByID();
                break;
            case 3:
                agencyView.deleteAgencyByID();
                break;
            case 4:
                agencyView.getAllAgencies();
                break;
            case 5:
                agencyView.updateAgency();
                break;
            case 6:
                agencyView.getAgenciesByAddress();
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
    }
}
