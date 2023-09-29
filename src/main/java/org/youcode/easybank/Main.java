package org.youcode.easybank;

import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.OperationException;
import org.youcode.easybank.services.*;
import org.youcode.easybank.views.MissionAssignmentView;
import org.youcode.easybank.views.MissionView;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Welcome to Your Bank Application!");
                System.out.println("1. Employee Management");
                System.out.println("2. Client Management");
                System.out.println("3. Account Management");
                System.out.println("4. Operation Management");
                System.out.println("5. Mission Management");
                System.out.println("6. Mission Assignment Management");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        employeeManagementMenu();
                        break;
                    case 2:
                        clientManagementMenu();
                        break;
                    case 3:
                        accountManagementMenu();
                        break;
                    case 4:
                        operationManagementMenu();
                        break;
                    case 5:
                        missionManagementMenu();
                        break;
                    case 6:
                        missionAssignmentManagementMenu();
                        break;
                    case 7:
                        System.out.println("Exiting the application. Goodbye!");
                        DBConnection.closeConnection();
                        sc.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }

        private static void employeeManagementMenu() {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Employee Management Menu:");
                System.out.println("1. Create Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Get Employee by Matricule");
                System.out.println("5. Get All Employees");
                System.out.println("6. Find Employee by Attribute");
                System.out.println("7. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        EmployeeService.createEmployee();
                        break;
                    case 2:
                        EmployeeService.updateEmployee();
                        break;
                    case 3:
                        EmployeeService.deleteEmployee();
                        break;
                    case 4:
                        EmployeeService.getEmployeeByMatricule();
                        break;
                    case 5:
                        EmployeeService.getAllEmployees();
                        break;
                    case 6:
                        EmployeeService.findEmployeeByAttribute();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }

        private static void clientManagementMenu() {
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

        private static void accountManagementMenu() {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Account Management Menu:");
                System.out.println("1. Create Account");
                System.out.println("2. Update Account");
                System.out.println("3. Get Account by Operation Number");
                System.out.println("4. Update Account Status");
                System.out.println("5. Display all savings accounts");
                System.out.println("6. Display all current accounts");
                System.out.println("7. Display accounts by their creation date");
                System.out.println("8. Delete Account");
                System.out.println("9. Get Accounts By Their Status");
                System.out.println("10. Get Account By Account Number");
                System.out.println("11. Get Accounts By Client");
                System.out.println("12. Get All Accounts (Current and Savings)");
                System.out.println("13. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        try {
                            AccountService.createAccount();
                        } catch (ClientException | EmployeeException a) {
                            a.printStackTrace();
                        }
                        break;
                    case 2:
                        AccountService.updateAccount();
                        break;
                    case 3:
                        AccountService.getAccountByOperationNumber();
                        break;
                    case 4:
                        AccountService.updateAccountStatus();
                        break;
                    case 5:
                        AccountService.getAllSavingsAccounts();
                        break;
                    case 6:
                        AccountService.getAllCurrentAccounts();
                        break;
                    case 7:
                        AccountService.getAllAccountsByCreationDate();
                        break;
                    case 8:
                        AccountService.deleteAccount();
                        break;
                    case 9:
                        AccountService.getAccountsByStatus();
                        break;
                    case 10:
                        AccountService.getAccountByAccountNumber();
                        break;
                    case 11:
                        try {
                            AccountService.getAccountsByClient();
                        } catch (AccountException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 12:
                        AccountService.getAllAccounts();
                        return;
                    case 13:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }

        private static void operationManagementMenu() {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("Operation Management Menu:");
                System.out.println("1. Create Operation");
                System.out.println("2. Get Operation By Number");
                System.out.println("3. Delete Operation");
                System.out.println("4. Back to Main Menu");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        try {
                            OperationService.createOperation();
                        } catch (AccountException | EmployeeException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        OperationService.getOperationByNumber();
                        break;
                    case 3:
                        OperationService.deleteOperation();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }

        private static void missionManagementMenu() {
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
    private static void missionAssignmentManagementMenu() {
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