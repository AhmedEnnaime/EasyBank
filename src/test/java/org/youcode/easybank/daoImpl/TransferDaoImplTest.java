package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.TransferDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Transfer;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

public class TransferDaoImplTest {

    private TransferDaoImpl transferDao;

    private AgencyDaoImpl agencyDao;

    private EmployeeDaoImpl employeeDao;

    private Agency agency;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        Connection testConnection = DBTestConnection.establishTestConnection();

        employeeDao = new EmployeeDaoImpl(testConnection);

        agencyDao = new AgencyDaoImpl(testConnection);

        transferDao = new TransferDaoImpl(testConnection);

        agency = new Agency(
                "Trionix",
                "biyada",
                "072867442"
        );

        agencyDao.create(agency);

        employee = new Employee(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat",
                LocalDate.of(2023, 9, 21),
                "mousta@gmail.com",
                agency
        );

        employeeDao.create(employee);
    }

    @Test
    public void testTransferEmployee() {
        Agency newAgency = new Agency(
                "NewAgency",
                "New Agency Address",
                "1234567890"
        );
        agencyDao.create(newAgency);

        Transfer transfer = new Transfer(
                LocalDate.now(),
                employee,
                newAgency
        );

        Optional<Transfer> createdTransfer = transferDao.transferEmployee(transfer);
        assertTrue(createdTransfer.isPresent());

        assertNotNull(createdTransfer.get().get_id());
    }

    @AfterEach
    public void tearDown() {
        employeeDao.deleteAll();
        agencyDao.deleteAll();
        transferDao.deleteAll();
    }
}
