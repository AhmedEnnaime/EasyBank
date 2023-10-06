package org.youcode.easybank.utils;

import org.youcode.easybank.views.MissionView;

import java.util.Scanner;

public class MissionUtils {

    public static void missionManagementMenu() {
        Scanner sc = new Scanner(System.in);
        MissionView missionView = new MissionView();
        while (true) {
            System.out.println("Mission Management Menu:");
            System.out.println("1. Create Mission");
            System.out.println("2. Get Mission By Number");
            System.out.println("3. Delete Mission");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    missionView.createMission();
                    break;
                case 2:
                    missionView.getMissionByNumber();
                    break;
                case 3:
                    missionView.deleteMission();
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
