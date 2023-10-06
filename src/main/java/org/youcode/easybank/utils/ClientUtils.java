package org.youcode.easybank.utils;

import org.youcode.easybank.services.ClientService;

import java.util.Scanner;

public class ClientUtils {

    public static void clientManagementMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Client Management Menu:");
            System.out.println("1. Create Client");
            System.out.println("2. Update Client");
            System.out.println("3. Delete Client");
            System.out.println("4. Get Client by Code");
            System.out.println("5. Get All Clients");
            System.out.println("6. Find Client by Attribute");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    ClientService.createClient();
                    break;
                case 2:
                    ClientService.updateClient();
                    break;
                case 3:
                    ClientService.deleteClient();
                    break;
                case 4:
                    ClientService.getClientByCode();
                    break;
                case 5:
                    ClientService.getAllClients();
                    break;
                case 6:
                    ClientService.findClientByAttribute();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
