package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.entities.Simulation;

public class SimulationService {

    private EmployeeDaoImpl employeeDao;
    public SimulationService(EmployeeDaoImpl employeeDao) {
        this.employeeDao = employeeDao;
    }
    public double createSimulation(Simulation simulation) {
        double result = 0;
        try {
            if (employeeDao.findByID(simulation.get_employee().get_matricule()).isPresent()) {
                if (simulation.get_borrowed_capital().toString().isEmpty() || simulation.get_monthly_payment_num().toString().isEmpty()) {
                    System.out.println("All fields needs to be mentioned");
                }else {
                    result = (simulation.get_borrowed_capital() * 0.12/12) / Math.pow((1 - (1 + 0.12/12)), - simulation.get_monthly_payment_num());
                }
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
