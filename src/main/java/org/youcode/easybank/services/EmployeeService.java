package org.youcode.easybank.services;

import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.EmployeeException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployeeService {

    public static void createEmployee() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter first name: ");
            String firstName = sc.nextLine();

            System.out.println("Enter last name: ");
            String lastName = sc.nextLine();

            System.out.println("Enter birthday (yyyy-MM-dd): ");
            String birthdateStr = sc.nextLine();

            System.out.println("Enter phone number: ");
            String phone = sc.nextLine();

            System.out.println("Enter address: ");
            String address = sc.nextLine();

            System.out.println("Enter recruitment date (yyyy-MM-dd): ");
            String recruitmentDateStr = sc.nextLine();

            System.out.println("Enter email: ");
            String email = sc.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
                LocalDate recruitmentDate = LocalDate.parse(recruitmentDateStr, formatter);

                int age = Period.between(birthdate, LocalDate.now()).getYears();

                if (age < 18) {
                    System.out.println("Employee must be at least 18 years old. Please try again.");
                    continue;
                }

                Employee newEmployee = new Employee(firstName, lastName, birthdate, phone, address, recruitmentDate, email);
                EmployeeDao dao = new EmployeeDaoImpl();

                try {
                    dao.create(newEmployee);
                    System.out.println("Employee created successfully.");
                } catch (EmployeeException e) {
                    System.out.println("Employee creation failed: " + e.getMessage());
                }
                break;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format. Please try again.");
            }
        }
    }

    public static void updateEmployee() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the matricule of the employee you want to update (or enter 'q' to quit): ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                // User wants to quit
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);
                EmployeeDao dao = new EmployeeDaoImpl();
                Optional<Employee> existingEmployee = dao.getByMatricule(matricule);

                if (existingEmployee.isPresent()) {
                    Employee employeeToUpdate = existingEmployee.get();

                    System.out.println("Enter new first name (leave empty to keep the current value): ");
                    String newFirstName = sc.nextLine();
                    if (!newFirstName.isEmpty()) {
                        employeeToUpdate.set_firstName(newFirstName);
                    }

                    System.out.println("Enter new last name (leave empty to keep the current value): ");
                    String newLastName = sc.nextLine();
                    if (!newLastName.isEmpty()) {
                        employeeToUpdate.set_lastName(newLastName);
                    }

                    System.out.println("Enter new birthday (yyyy-MM-dd) (leave empty to keep the current value): ");
                    String newBirthdateStr = sc.nextLine();
                    if (!newBirthdateStr.isEmpty()) {
                        LocalDate newBirthdate = LocalDate.parse(newBirthdateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        employeeToUpdate.set_birthDate(newBirthdate);
                    }

                    System.out.println("Enter new phone number (leave empty to keep the current value): ");
                    String newPhone = sc.nextLine();
                    if (!newPhone.isEmpty()) {
                        employeeToUpdate.set_phone(newPhone);
                    }

                    System.out.println("Enter new address (leave empty to keep the current value): ");
                    String newAddress = sc.nextLine();
                    if (!newAddress.isEmpty()) {
                        employeeToUpdate.set_address(newAddress);
                    }

                    System.out.println("Enter new recruitment date (yyyy-MM-dd) (leave empty to keep the current value): ");
                    String newRecruitmentDateStr = sc.nextLine();
                    if (!newRecruitmentDateStr.isEmpty()) {
                        LocalDate newRecruitmentDate = LocalDate.parse(newRecruitmentDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        employeeToUpdate.set_recruitmentDate(newRecruitmentDate);
                    }

                    System.out.println("Enter new email (leave empty to keep the current value): ");
                    String newEmail = sc.nextLine();
                    if (!newEmail.isEmpty()) {
                        employeeToUpdate.set_email(newEmail);
                    }

                    try {
                        dao.update(matricule, employeeToUpdate);
                        System.out.println("Employee updated successfully.");
                        break;
                    } catch (EmployeeException e) {
                        System.out.println("Employee update failed: " + e.getMessage());
                    }
                } else {
                    System.out.println("Employee not found with matricule: " + matricule);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            } catch (EmployeeException e) {
                System.out.println("Error retrieving employee: " + e.getMessage());
            }
        }
    }

    public static void deleteEmployee() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the matricule of the employee you want to delete (or enter 'q' to quit): ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);
                EmployeeDao dao = new EmployeeDaoImpl();

                if (dao.delete(matricule)) {
                    System.out.println("Employee with matricule " + matricule + " deleted successfully.");
                    break;
                } else {
                    System.out.println("Employee not found with matricule: " + matricule);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            }
        }
    }

    public static void getEmployeeByMatricule() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the matricule of the employee you want to retrieve (or enter 'q' to quit): ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int matricule = Integer.parseInt(matriculeInput);
                EmployeeDao dao = new EmployeeDaoImpl();
                Optional<Employee> existingEmployee = dao.getByMatricule(matricule);

                if (existingEmployee.isPresent()) {
                    Employee employee = existingEmployee.get();
                    System.out.println("Employee found:");
                    System.out.println("Matricule: " + employee.get_matricule());
                    System.out.println("First Name: " + employee.get_firstName());
                    System.out.println("Last Name: " + employee.get_lastName());
                    System.out.println("Birthdate: " + employee.get_birthDate());
                    System.out.println("Phone: " + employee.get_phone());
                    System.out.println("Address: " + employee.get_address());
                    System.out.println("Recruitment Date: " + employee.get_recruitmentDate());
                    System.out.println("Email: " + employee.get_email());
                    break;
                } else {
                    System.out.println("Employee not found with matricule: " + matricule);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid matricule or 'q' to quit.");
            } catch (EmployeeException e) {
                System.out.println("Error retrieving employee: " + e.getMessage());
            }
        }
    }

    public static void getAllEmployees() {
        EmployeeDao dao = new EmployeeDaoImpl();

        try {
            List<Employee> employees = dao.getAll();

            if (!employees.isEmpty()) {
                System.out.println("List of all employees:");
                for (Employee employee : employees) {
                    System.out.println("Matricule: " + employee.get_matricule());
                    System.out.println("First Name: " + employee.get_firstName());
                    System.out.println("Last Name: " + employee.get_lastName());
                    System.out.println("Birthdate: " + employee.get_birthDate());
                    System.out.println("Phone: " + employee.get_phone());
                    System.out.println("Address: " + employee.get_address());
                    System.out.println("Recruitment Date: " + employee.get_recruitmentDate());
                    System.out.println("Email: " + employee.get_email());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No employees found.");
            }
        } catch (EmployeeException e) {
            System.out.println("Error retrieving employees: " + e.getMessage());
        }
    }

    public static void findEmployeeByAttribute() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value you want to search for: ");
        String searchValue = sc.nextLine();

        EmployeeDao dao = new EmployeeDaoImpl();

        try {
            List<Employee> foundEmployees = dao.findByAttribute(searchValue);

            if (!foundEmployees.isEmpty()) {
                System.out.println("Employees found:");
                for (Employee employee : foundEmployees) {
                    System.out.println("Matricule: " + employee.get_matricule());
                    System.out.println("First Name: " + employee.get_firstName());
                    System.out.println("Last Name: " + employee.get_lastName());
                    System.out.println("Birthdate: " + employee.get_birthDate());
                    System.out.println("Phone: " + employee.get_phone());
                    System.out.println("Address: " + employee.get_address());
                    System.out.println("Recruitment Date: " + employee.get_recruitmentDate());
                    System.out.println("Email: " + employee.get_email());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No employees found with the specified value.");
            }
        } catch (EmployeeException e) {
            System.out.println("Error searching for employees: " + e.getMessage());
        }
    }

}
