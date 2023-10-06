package org.youcode.easybank.utils;

import org.youcode.easybank.services.EmployeeService;

import java.util.Scanner;

public class EmployeeUtils {

    public static void employeeManagementMenu() {
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

}
