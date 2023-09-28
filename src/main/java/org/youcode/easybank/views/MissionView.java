package org.youcode.easybank.views;

import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.services.MissionService;

import java.util.Scanner;

public class MissionView {

    private final MissionService missionService;

    public MissionView() {
        missionService = new MissionService();
    }

    public void createMission() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter mission name: ");
        String name = sc.nextLine();

        System.out.println("Enter mission description: ");
        String description = sc.nextLine();

        Mission mission = new Mission(name, description);
        missionService.createMission(mission);
    }

    public void getMissionByNumber() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the mission you want to retrieve (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            int code = Integer.parseInt(codeInput);
            Mission mission = missionService.getMissionByNumber(code);
            if (mission != null) {
                break;
            }
        }
    }

    public void deleteMission() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the code of the mission you want to delete (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }
            int code = Integer.parseInt(codeInput);
            boolean isDeleted = missionService.deleteMission(code);
            if (isDeleted) {
                break;
            }
        }
    }
}
