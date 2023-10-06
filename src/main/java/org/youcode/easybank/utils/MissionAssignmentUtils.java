package org.youcode.easybank.utils;

import org.youcode.easybank.services.MissionAssignmentService;
import org.youcode.easybank.views.MissionAssignmentView;

import java.util.Scanner;

public class MissionAssignmentUtils {
    public static void missionAssignmentManagementMenu() {
        Scanner sc = new Scanner(System.in);
        MissionAssignmentView missionAssignmentView = new MissionAssignmentView();

        while (true) {
            System.out.println("Mission Assignment Management Menu:");
            System.out.println("1. Assign Mission");
            System.out.println("2. Get Assignment By ID");
            System.out.println("3. Delete Mission Assignment");
            System.out.println("4. Get Employees Assignments");
            System.out.println("5. Display Statistics");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    MissionAssignmentService.createMissionAssignment();
                    break;
                case 2:
                    MissionAssignmentService.getAssignmentByID();
                    break;
                case 3:
                    MissionAssignmentService.deleteAssignment();
                    break;
                case 4:
                    MissionAssignmentService.getEmployeesAssignments();
                    break;
                case 5:
                    missionAssignmentView.displayStats();
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
