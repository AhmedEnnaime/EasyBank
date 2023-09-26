package org.youcode.easybank.services;

import org.youcode.easybank.dao.MissionDao;
import org.youcode.easybank.dao.daoImpl.MissionDaoImpl;
import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.exceptions.MissionException;

import java.util.Optional;
import java.util.Scanner;

public class MissionService {

    public static void createMission() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter mission name: ");
        String name = sc.nextLine();

        System.out.println("Enter mission description: ");
        String description = sc.nextLine();

        Mission newMission = new Mission(name, description);
        MissionDao dao = new MissionDaoImpl();

        try {
            dao.create(newMission);
            System.out.println("Mission created successfully.");
        } catch (MissionException e) {
            System.out.println("Mission creation failed: " + e.getMessage());
        }

    }

    public static void getMissionByNumber() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the mission you want to retrieve (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                MissionDao dao = new MissionDaoImpl();
                Optional<Mission> existingMission = dao.getByNumber(code);

                if (existingMission.isPresent()) {
                    Mission mission = existingMission.get();
                    System.out.println("Mission found:");
                    System.out.println("Code: " + mission.get_code());
                    System.out.println("Name: " + mission.get_name());
                    System.out.println("Description: " + mission.get_description());
                } else {
                    System.out.println("Mission not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            } catch (MissionException e) {
                System.out.println("Error retrieving mission: " + e.getMessage());
            }
        }
    }

    public static void deleteMission() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the mission you want to delete (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                MissionDao dao = new MissionDaoImpl();

                if (dao.delete(code)) {
                    System.out.println("Mission with code " + code + " deleted successfully.");
                    break;
                } else {
                    System.out.println("Mission not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            } catch (MissionException e) {
                System.out.println("Error deleting mission: " + e.getMessage());
            }
        }
    }
}
