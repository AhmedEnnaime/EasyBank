package org.youcode.easybank.services;

import org.youcode.easybank.dao.ClientDao;
import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientService {

    public static void createClient() {
        Scanner sc = new Scanner(System.in);

        int matricule = EmployeeService.validateMatricule();

        if (matricule > 0) {
            while (true) {
                System.out.println("Enter client's first name: ");
                String firstName = sc.nextLine();

                System.out.println("Enter client's last name: ");
                String lastName = sc.nextLine();

                System.out.println("Enter client's birthday (yyyy-MM-dd): ");
                String birthdateStr = sc.nextLine();

                System.out.println("Enter client's phone number: ");
                String phone = sc.nextLine();

                System.out.println("Enter client's address: ");
                String address = sc.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);

                    int age = Period.between(birthdate, LocalDate.now()).getYears();

                    if (age < 18) {
                        System.out.println("Client must be at least 18 years old. Please try again.");
                        continue;
                    }

                    Employee employee = new Employee();
                    employee.set_matricule(matricule);

                    Client newClient = new Client(firstName, lastName, birthdate, phone, address, employee);
                    ClientDao dao = new ClientDaoImpl();

                    dao.create(newClient);
                    System.out.println("Client created successfully.");
                    break;

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format. Please try again.");
                }
            }
        }
    }

    public static void updateClient() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the client you want to update (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                ClientDao dao = new ClientDaoImpl();
                Optional<Client> existingClient = dao.findByID(code);

                if (existingClient.isPresent()) {
                    Client clientToUpdate = existingClient.get();

                    System.out.println("Enter new first name (leave empty to keep the current value): ");
                    String newFirstName = sc.nextLine();
                    if (!newFirstName.isEmpty()) {
                        clientToUpdate.set_firstName(newFirstName);
                    }

                    System.out.println("Enter new last name (leave empty to keep the current value): ");
                    String newLastName = sc.nextLine();
                    if (!newLastName.isEmpty()) {
                        clientToUpdate.set_lastName(newLastName);
                    }

                    System.out.println("Enter new birthday (yyyy-MM-dd) (leave empty to keep the current value): ");
                    String newBirthdateStr = sc.nextLine();
                    if (!newBirthdateStr.isEmpty()) {
                        LocalDate newBirthdate = LocalDate.parse(newBirthdateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        clientToUpdate.set_birthDate(newBirthdate);
                    }

                    System.out.println("Enter new phone number (leave empty to keep the current value): ");
                    String newPhone = sc.nextLine();
                    if (!newPhone.isEmpty()) {
                        clientToUpdate.set_phone(newPhone);
                    }

                    System.out.println("Enter new address (leave empty to keep the current value): ");
                    String newAddress = sc.nextLine();
                    if (!newAddress.isEmpty()) {
                        clientToUpdate.set_address(newAddress);
                    }

                    dao.update(code, clientToUpdate);
                    System.out.println("Client updated successfully.");
                    break;
                } else {
                    System.out.println("Client not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public static void deleteClient() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the client you want to delete (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                ClientDao dao = new ClientDaoImpl();

                if (dao.delete(code)) {
                    System.out.println("Client with code " + code + " deleted successfully.");
                    break;
                } else {
                    System.out.println("Client not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public static void getClientByCode() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the code of the client you want to retrieve (or enter 'q' to quit): ");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            try {
                int code = Integer.parseInt(codeInput);
                ClientDao dao = new ClientDaoImpl();
                Optional<Client> existingClient = dao.findByID(code);

                if (existingClient.isPresent()) {
                    Client client = existingClient.get();
                    System.out.println("Client found:");
                    System.out.println("Code: " + client.get_code());
                    System.out.println("First Name: " + client.get_firstName());
                    System.out.println("Last Name: " + client.get_lastName());
                    System.out.println("Birthdate: " + client.get_birthDate());
                    System.out.println("Phone: " + client.get_phone());
                    System.out.println("Address: " + client.get_address());
                    System.out.println("Responsible Employee: " + client.get_employee().get_lastName());
                    break;
                } else {
                    System.out.println("Client not found with code: " + code);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }
        }
    }

    public static void getAllClients() {
        ClientDao dao = new ClientDaoImpl();

        List<Client> clients = dao.getAll();

        if (!clients.isEmpty()) {
            System.out.println("List of all clients:");
            for (Client client : clients) {
                System.out.println("Code: " + client.get_code());
                System.out.println("First Name: " + client.get_firstName());
                System.out.println("Last Name: " + client.get_lastName());
                System.out.println("Birthdate: " + client.get_birthDate());
                System.out.println("Phone: " + client.get_phone());
                System.out.println("Address: " + client.get_address());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No clients found.");
        }
    }

    public static void findClientByAttribute() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value you want to search for: ");
        String searchValue = sc.nextLine();

        ClientDao dao = new ClientDaoImpl();

        try {
            List<Client> foundClients = dao.findByAttribute(searchValue);

            if (!foundClients.isEmpty()) {
                System.out.println("Clients found:");
                for (Client client : foundClients) {
                    System.out.println("Code: " + client.get_code());
                    System.out.println("First Name: " + client.get_firstName());
                    System.out.println("Last Name: " + client.get_lastName());
                    System.out.println("Birthdate: " + client.get_birthDate());
                    System.out.println("Phone: " + client.get_phone());
                    System.out.println("Address: " + client.get_address());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No clients found with the specified value.");
            }
        } catch (ClientException e) {
            System.out.println("Error searching for clients: " + e.getMessage());
        }
    }

}
