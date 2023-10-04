package org.youcode.easybank.views;

import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.services.SimulationService;

import java.util.Optional;
import java.util.Scanner;

public class SimulationView {

    private SimulationService simulationService;

    public SimulationView(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public void createSimulation() {
        Scanner sc = new Scanner(System.in);

//        while (true) {
//            System.out.println("Enter employee's matricule ");
//            String matriculeInput = sc.nextLine();
//
//            if (matriculeInput.equalsIgnoreCase("q")) {
//                break;
//            }
//
//            EmployeeDao employeeDao = new EmployeeDaoImpl();
//
//            int matricule = Integer.parseInt(matriculeInput);
//            Optional<Employee> retrievedEmployee = employeeDao.findByID(matricule);
//
//            if (retrievedEmployee.isPresent()) {
//                System.out.println("Is the beneficiary a client ? if yes click 'y' if not click 'n' to create a client");
//
//            }
//        }

        System.out.println("Enter the amount you want to borrow");
    }
}
