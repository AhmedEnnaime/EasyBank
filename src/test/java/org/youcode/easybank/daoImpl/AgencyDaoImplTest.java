package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class AgencyDaoImplTest {

    private AgencyDaoImpl agencyDao;

    private EmployeeDaoImpl employeeDao;

    private Agency agency;

    private Integer agency_code;

    @BeforeEach
    public void setUp() {

        Connection testConnection = DBTestConnection.establishTestConnection();

        agencyDao = new AgencyDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        agency = new Agency(
                "Trionix",
                "biyada",
                "072867442"
        );

        agencyDao.create(agency);

        agency_code = agency.get_code();

    }

    public void assertAgenciesEquals(Agency actual, Agency expected) {
        assertEquals(actual.get_name(), expected.get_name());
        assertEquals(actual.get_address(), expected.get_address());
        assertEquals(actual.get_phone(), expected.get_phone());
    }

    @Test
    public void testCreate() {
        Agency agency1 = new Agency(
                "Zerpin",
                "safi",
                "052471232"
        );

        Optional<Agency> createdAgency = agencyDao.create(agency1);
        assertTrue(createdAgency.isPresent());
        assertEquals(agency1.get_name(), createdAgency.get().get_name());
        assertEquals(agency1.get_address(), createdAgency.get().get_address());
        assertEquals(agency1.get_phone(), createdAgency.get().get_phone());
    }

    @Test
    public void testFindByID() {
        Agency agency1 = new Agency(
                "Zerpin",
                "safi",
                "052471236132"
        );

        Optional<Agency> createdAgency = agencyDao.create(agency1);
        assertTrue(createdAgency.isPresent());

        Optional<Agency> retrievedAgency = agencyDao.findByID(createdAgency.get().get_code());
        assertTrue(retrievedAgency.isPresent());
        assertAgenciesEquals(createdAgency.get(), retrievedAgency.get());

    }

    @Test
    public void testDelete() {
        Agency agency1 = new Agency(
                "Zerpin",
                "safi",
                "0524712332"
        );

        Optional<Agency> createdAgency = agencyDao.create(agency1);
        assertTrue(createdAgency.isPresent());

        boolean isDeleted = agencyDao.delete(createdAgency.get().get_code());
        assertTrue(isDeleted);

        Optional<Agency> deletedAgency = agencyDao.findByID(createdAgency.get().get_code());
        assertFalse(deletedAgency.isPresent());
    }

    @Test
    public void testUpdate() {
        Agency agency1 = new Agency(
                "Zerpin",
                "safi",
                "0524712132"
        );

        Optional<Agency> createdAgency = agencyDao.create(agency1);
        assertTrue(createdAgency.isPresent());

        Agency updatedAgency = new Agency(
                "updated name",
                "updated address",
                "052499432"
        );

        Optional<Agency> optionalAgency = agencyDao.update(createdAgency.get().get_code(), updatedAgency);
        assertTrue(optionalAgency.isPresent());

        Optional<Agency> retrievedAgency = agencyDao.findByID(createdAgency.get().get_code());
        assertTrue(retrievedAgency.isPresent());

        assertEquals(updatedAgency.get_name(), retrievedAgency.get().get_name());
        assertEquals(updatedAgency.get_address(), retrievedAgency.get().get_address());
        assertEquals(updatedAgency.get_phone(), retrievedAgency.get().get_phone());
    }

    @Test
    public void testGetAll() {
        List<Agency> allAgencies = agencyDao.getAll();
        assertNotNull(allAgencies);
        assertFalse(allAgencies.isEmpty());

        assertTrue(allAgencies.stream().anyMatch(e -> e.get_name().equals("Trionix")));
    }

    @Test
    public void testGetByAddress() {
        Agency agency1 = new Agency(
                "Zerpin",
                "safi",
                "0524712132"
        );

        Optional<Agency> createdAgency = agencyDao.create(agency1);
        assertTrue(createdAgency.isPresent());

        List<Agency> agencies = agencyDao.findByAddress("safi");
        assertNotNull(agencies);
        assertFalse(agencies.isEmpty());

    }

//    @Test
//    public void testGetAgencyByEmployee() {
//        Agency agency1 = new Agency(
//                "Zerpin",
//                "safi",
//                "0524712332"
//        );
//
//        Optional<Agency> createdAgency = agencyDao.create(agency1);
//        assertTrue(createdAgency.isPresent());
//
//        Employee employee = new Employee(
//                "Mousta",
//                "Delegue",
//                LocalDate.of(2001, 11, 17),
//                "06473347924",
//                "Jrayfat",
//                LocalDate.of(2023, 9, 21),
//                "mousta@gmail.com",
//                agency1
//        );
//
//        Optional<Employee> createdEmployee = employeeDao.create(employee);
//        assertTrue(createdEmployee.isPresent());
//
//        Agency retrievedAgency = agencyDao.findAgencyByEmployee(employee);
////        assertNotNull(retrievedAgency);
//    }

    @AfterEach
    public void tearDown() {
        agencyDao.deleteAll();
    }
}
