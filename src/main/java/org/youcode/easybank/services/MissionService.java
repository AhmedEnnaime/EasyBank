package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.MissionDaoImpl;
import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.exceptions.MissionException;

import java.util.Optional;

public class MissionService {
    private final MissionDaoImpl missionDao;

    public MissionService() {
        missionDao = new MissionDaoImpl();
    }

    public void createMission(Mission mission) {

        try {
            Optional<Mission> optionalMission = missionDao.create(mission);
            if (optionalMission.isPresent()) {
                System.out.println("Mission created successfully.");
            }
        } catch (MissionException e) {
            System.out.println("Mission creation failed: " + e.getMessage());
        }

    }

    public Mission getMissionByNumber(int code) {
        try {
            Optional<Mission> existingMission = missionDao.getByNumber(code);

            if (existingMission.isPresent()) {
                Mission mission = existingMission.get();
                System.out.println("Mission found:");
                System.out.println("Code: " + mission.get_code());
                System.out.println("Name: " + mission.get_name());
                System.out.println("Description: " + mission.get_description());
                return existingMission.get();
            } else {
                System.out.println("Mission not found with code: " + code);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
        } catch (MissionException e) {
            System.out.println("Error retrieving mission: " + e.getMessage());
        }
        return null;
    }

    public boolean deleteMission(int code) {

        try {

            if (missionDao.delete(code)) {
                System.out.println("Mission with code " + code + " deleted successfully.");
                return true;
            } else {
                System.out.println("Mission not found with code: " + code);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
        } catch (MissionException e) {
            System.out.println("Error deleting mission: " + e.getMessage());
        }
        return false;
    }
}
