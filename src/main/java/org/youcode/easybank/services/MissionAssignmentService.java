package org.youcode.easybank.services;

import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.MissionAssignmentDao;
import org.youcode.easybank.dao.MissionDao;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.MissionAssignmentDaoImpl;
import org.youcode.easybank.dao.daoImpl.MissionDaoImpl;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.entities.MissionAssignment;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.MissionAssignmentException;
import org.youcode.easybank.exceptions.MissionException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MissionAssignmentService {


    public static void createMissionAssignment() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Enter start date (yyyy-MM-dd): ");
            String startDateStr = sc.nextLine();
            LocalDate startDate = LocalDate.parse(startDateStr);

            System.out.println("Enter end date (yyyy-MM-dd): ");
            String endDateStr = sc.nextLine();
            LocalDate endDate = LocalDate.parse(endDateStr);

            System.out.println("Enter mission code: ");
            int missionCode = sc.nextInt();
            sc.nextLine();

            List<Employee> employees = new ArrayList<>();
            while (true) {
                System.out.println("Enter employee matricule (or enter 'q' to finish adding employees): ");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    break;
                }

                try {
                    int matricule = Integer.parseInt(input);
                    EmployeeDao employeeDao = new EmployeeDaoImpl();
                    Optional<Employee> employee = employeeDao.findByID(matricule);

                    if (employee.isPresent()) {
                        employees.add(employee.get());
                    } else {
                        System.out.println("Employee not found with Matricule " + matricule + ". Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid matricule or 'q' to finish.");
                }
            }

            MissionDao missionDao = new MissionDaoImpl();
            Optional<Mission> mission = missionDao.getByNumber(missionCode);

            if (mission.isPresent()) {
                MissionAssignmentDao missionAssignmentDao = new MissionAssignmentDaoImpl();
                for (Employee employee : employees) {
                    MissionAssignment missionAssignment = new MissionAssignment(startDate, endDate, List.of(employee), mission.get());
                    try {
                        Optional<MissionAssignment> createdAssignment = missionAssignmentDao.create(missionAssignment);

                        if (createdAssignment.isPresent()) {
                            System.out.println("Mission assignment created successfully with ID: " + createdAssignment.get().getId());
                        } else {
                            System.out.println("Failed to create mission assignment.");
                        }
                    } catch (MissionAssignmentException e) {
                        System.out.println("Error creating mission assignment: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("Mission not found with code: " + missionCode);
            }
        } catch (MissionException e) {
            System.out.println("Error retrieving mission: " + e.getMessage());
        }
    }

    public static void getEmployeesAssignments() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the employee's matricule to retrieve mission assignments (or enter 'q' to quit): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(input);
                EmployeeDao employeeDao = new EmployeeDaoImpl();
                Optional<Employee> employee = employeeDao.findByID(matricule);

                if (employee.isPresent()) {
                    MissionAssignmentDao missionAssignmentDao = new MissionAssignmentDaoImpl();
                    List<MissionAssignment> assignments = missionAssignmentDao.getEmployeeAssignments(employee.get());

                    if (!assignments.isEmpty()) {
                        System.out.println("Mission Assignments for Employee with Matricule " + matricule + ":");
                        for (MissionAssignment assignment : assignments) {
                            System.out.println("Assignment ID: " + assignment.getId());
                            System.out.println("Start Date: " + assignment.get_debutDate());
                            System.out.println("End Date: " + assignment.get_endDate());
                            System.out.println("---------------------------");
                        }
                    } else {
                        System.out.println("No mission assignments found for the employee with Matricule " + matricule + ".");
                    }
                } else {
                    System.out.println("Employee not found with Matricule " + matricule + ". Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            } catch (MissionAssignmentException e) {
                System.out.println("Error retrieving mission assignments: " + e.getMessage());
            }
        }
    }

    public static void deleteAssignment() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the ID of the mission assignment to delete (or enter 'q' to quit): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int assignmentId = Integer.parseInt(input);
                MissionAssignmentDao missionAssignmentDao = new MissionAssignmentDaoImpl();

                if (missionAssignmentDao.delete(assignmentId)) {
                    System.out.println("Mission assignment with ID " + assignmentId + " deleted successfully.");
                } else {
                    System.out.println("Mission assignment with ID " + assignmentId + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ID or 'q' to quit.");
            } catch (MissionAssignmentException e) {
                System.out.println("Error deleting mission assignment: " + e.getMessage());
            }
        }
    }

    public static void getAssignmentByID() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the ID of the mission assignment to retrieve (or enter 'q' to quit): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int assignmentId = Integer.parseInt(input);
                MissionAssignmentDao missionAssignmentDao = new MissionAssignmentDaoImpl();
                Optional<MissionAssignment> assignment = missionAssignmentDao.getByID(assignmentId);

                if (assignment.isPresent()) {
                    MissionAssignment missionAssignment = assignment.get();
                    System.out.println("Mission Assignment with ID " + assignmentId + ":");
                    System.out.println("Start Date: " + missionAssignment.get_debutDate());
                    System.out.println("End Date: " + missionAssignment.get_endDate());
                    System.out.println("---------------------------");
                } else {
                    System.out.println("Mission assignment with ID " + assignmentId + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ID or 'q' to quit.");
            } catch (MissionAssignmentException e) {
                System.out.println("Error retrieving mission assignment: " + e.getMessage());
            }
        }
    }

    public void displayStats() {
        MissionAssignmentDao missionAssignmentDao = new MissionAssignmentDaoImpl();
        try {
            Optional<HashMap<Integer, Integer>> stats = missionAssignmentDao.getStats();

            if (stats.isPresent()) {
                HashMap<Integer, Integer> missionStats = stats.get();

                System.out.println("Mission Statistics:");
                for (Map.Entry<Integer, Integer> entry : missionStats.entrySet()) {
                    int missionCode = entry.getKey();
                    int totalEmployees = entry.getValue();

                    System.out.println("Mission Code: " + missionCode);
                    System.out.println("Total Employees: " + totalEmployees);
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No mission statistics found.");
            }
        } catch (MissionAssignmentException e) {
            System.out.println("Error retrieving mission statistics: " + e.getMessage());
        }
    }

}
