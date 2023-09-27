package org.youcode.easybank.services;

import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.MissionAssignmentDao;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.MissionAssignmentDaoImpl;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.MissionAssignment;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.MissionAssignmentException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MissionAssignmentService {

    public static void createMissionAssignment() {

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
                Optional<Employee> employee = employeeDao.getByMatricule(matricule);

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
            } catch (EmployeeException e) {
                throw new RuntimeException(e);
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



}
