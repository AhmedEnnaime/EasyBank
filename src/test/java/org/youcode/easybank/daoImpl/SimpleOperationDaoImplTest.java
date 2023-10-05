package org.youcode.easybank.daoImpl;

import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.OperationDaoImpl;
import org.youcode.easybank.dao.daoImpl.SimpleOperationDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;

import java.sql.Connection;
import java.time.LocalDate;

public class SimpleOperationDaoImplTest {


    private Employee employee;

    private SimpleOperationDaoImpl simpleOperationDao;

    private OperationDaoImpl operationDao;

    private AgencyDaoImpl agencyDao;

    private Agency agency;

    private EmployeeDaoImpl employeeDao;

    public void setUp() {
        Connection testConnection = DBTestConnection.establishTestConnection();

        operationDao = new OperationDaoImpl(testConnection);
        simpleOperationDao = new SimpleOperationDaoImpl(testConnection);
        agencyDao = new AgencyDaoImpl(testConnection);

        agency = new Agency(
                "Trionix",
                "biyada",
                "072867442"
        );

        agencyDao.create(agency);

        employee = new Employee(
                "Aymen",
                "Servoy",
                LocalDate.of(2000, 1, 26),
                "06823347924",
                "sidi bouzid",
                LocalDate.of(2023, 9, 21),
                "servoy@gmail.com",
                agency
        );

        employeeDao.create(employee);

    }
}
