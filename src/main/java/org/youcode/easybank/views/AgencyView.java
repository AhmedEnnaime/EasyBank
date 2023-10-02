package org.youcode.easybank.views;

import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.services.AgencyService;

import java.util.Scanner;

public class AgencyView {

    private AgencyService agencyService;
    public AgencyView(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    public void createAgency() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter name's agency");
        String name = sc.nextLine();

        System.out.println("Enter address's agency");
        String address = sc.nextLine();

        System.out.println("Enter phone's agency");
        String phone = sc.nextLine();

        Agency agency = new Agency(name, address, phone);
        agencyService.createAgency(agency);
    }

    public void getAgencyByID() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter agency's code : ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            int code = Integer.parseInt(codeInput);

            if (agencyService.getAgencyByID(code)) {
                break;
            }else {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public void deleteAgencyByID() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter agency's code you want to delete : ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            int code = Integer.parseInt(codeInput);

            if (agencyService.deleteAgencyByCode(code)) {
                break;
            }else {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public void updateAgency() {
        Scanner sc = new Scanner(System.in);

        while (true) {

        }
    }
}
