package org.youcode.easybank.views;

import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Transfer;
import org.youcode.easybank.services.TransferService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class TransferView {

    TransferService transferService;

    EmployeeDaoImpl employeeDao;

    AgencyDaoImpl agencyDao;

    public TransferView(TransferService transferService, EmployeeDaoImpl employeeDao, AgencyDaoImpl agencyDao) {
        this.transferService = transferService;
        this.employeeDao = employeeDao;
        this.agencyDao = agencyDao;
    }

    public void transferEmployeeToAgency() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter employee's matricule you want to transfer : ");
            String matriculeInput = sc.nextLine();

            if (matriculeInput.equalsIgnoreCase("q")) {
                break;
            }

            int matricule = Integer.parseInt(matriculeInput);

            Optional<Employee> retrievedEmployee = employeeDao.findByID(matricule);

            if (retrievedEmployee.isPresent()) {
                System.out.println("Enter agency's code : ");
                String codeInput = sc.nextLine();

                if (codeInput.equalsIgnoreCase("q")) {
                    break;
                }

                int code = Integer.parseInt(codeInput);

                Optional<Agency> retrievedAgency = agencyDao.findByID(code);

                if (retrievedAgency.isPresent()) {
                    System.out.println("Enter transfer date (yyyy-MM-dd)");
                    String transferDateStr = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate transferDate = LocalDate.parse(transferDateStr, formatter);

                    Transfer transfer = new Transfer(transferDate, retrievedEmployee.get(), retrievedAgency.get());
                    boolean transferred = transferService.transferEmployee(retrievedEmployee.get(), code, transfer);
                    if (transferred) {
                        System.out.println("Employee transferred successfully");
                    }else {
                        System.out.println("Transfer of employee failed");
                    }
                }
            }else {
                System.out.println("Invalid input. Please enter a valid code or 'q' to quit.");
            }

        }

    }
}
