package org.youcode.easybank.views;

import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.services.AgencyService;

import java.util.Optional;
import java.util.Scanner;

public class AgencyView {

    private AgencyService agencyService;

    private AgencyDaoImpl agencyDao;

    public AgencyView(AgencyService agencyService, AgencyDaoImpl agencyDao) {
        this.agencyService = agencyService;
        this.agencyDao = agencyDao;
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
            System.out.println("Enter agency's code you want to update : ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            int code = Integer.parseInt(codeInput);

            Optional<Agency> retrievedAgency = agencyDao.findByID(code);
            if (retrievedAgency.isPresent()) {
                System.out.println("Enter new agency's name : ");
                String name = sc.nextLine();

                System.out.println("Enter new agency's address : ");
                String address = sc.nextLine();

                System.out.println("Enter new agency's phone : ");
                String phone = sc.nextLine();

                Agency updatedAgency = new Agency(name, address, phone);
                if (agencyService.updateAgency(code, updatedAgency)) {
                    System.out.println("Agency updated successfully");
                }else {
                    System.out.println("Update agency failed");
                }
                break;
            }else {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }

        }
    }
}
