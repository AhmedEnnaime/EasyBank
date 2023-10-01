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
}
