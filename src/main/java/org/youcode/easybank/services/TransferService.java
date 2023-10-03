package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.TransferDaoImpl;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Transfer;

import java.util.Optional;

public class TransferService {

    private TransferDaoImpl transferDao;

    private EmployeeDaoImpl employeeDao;

    public TransferService(TransferDaoImpl transferDao, EmployeeDaoImpl employeeDao) {
        this.transferDao = transferDao;
        this.employeeDao = employeeDao;
    }

    public boolean transferEmployee(Employee employee, Integer agency_code, Transfer transfer) {
        Optional<Employee> transferredEmployee = employeeDao.changeAgency(employee, agency_code);
        if (transferredEmployee.isPresent()) {
            Optional<Transfer> agencyTransfer = transferDao.transferEmployee(transfer);
            return agencyTransfer.isPresent();
        }else {
            return false;
        }
    }
}
