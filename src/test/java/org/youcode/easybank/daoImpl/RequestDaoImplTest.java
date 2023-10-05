package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.RequestDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.enums.STATE;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RequestDaoImplTest {

    private RequestDaoImpl requestDao;

    private ClientDaoImpl clientDao;

    private EmployeeDaoImpl employeeDao;

    private AgencyDaoImpl agencyDao;

    private Employee employee;
    private Agency agency;
    private Client client;

    @BeforeEach
    public void setUp() {

        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        requestDao = new RequestDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        agencyDao = new AgencyDaoImpl(testConnection);

        agency = new Agency(
                "YouCode",
                "test address",
                "05248137133"
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

        employeeDao.create(employee);

        client = new Client(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat",
                employee
        );

        clientDao.create(client);

    }

    @Test
    public void testCreate() {
        Simulation simulation = new Simulation(
                5000.00,
                5,
                client
        );

        Request request = new Request(
                LocalDate.now(),
                simulation.get_borrowed_capital(),
                STATE.PENDING,
                "remarks",
                5,
                simulation
        );

        Optional<Request> createdRequest = requestDao.create(request);
        assertTrue(createdRequest.isPresent());

        assertEquals(request.get_credit_date(), createdRequest.get().get_credit_date());
        assertEquals(request.get_amount(), createdRequest.get().get_amount());
        assertEquals(request.get_remarks(), createdRequest.get().get_remarks());
    }

    @Test
    public void testFindByID() {
        Simulation simulation = new Simulation(
                5000.00,
                5,
                client
        );

        Request request = new Request(
                LocalDate.now(),
                simulation.get_borrowed_capital(),
                STATE.PENDING,
                "remarks",
                5,
                simulation
        );

        Optional<Request> createdRequest = requestDao.create(request);
        assertTrue(createdRequest.isPresent());

        Optional<Request> retrievedRequest = requestDao.findByID(request.get_number());
        assertTrue(retrievedRequest.isPresent());
    }

    @Test
    public void testGetAll() {
        Simulation simulation = new Simulation(
                5000.00,
                5,
                client
        );

        Request request = new Request(
                LocalDate.now(),
                simulation.get_borrowed_capital(),
                STATE.PENDING,
                "remarks",
                5,
                simulation
        );

        Optional<Request> createdRequest = requestDao.create(request);
        assertTrue(createdRequest.isPresent());
        List<Request> allRequests = requestDao.getAll();
        assertNotNull(allRequests);
        assertFalse(allRequests.isEmpty());

        assertTrue(allRequests.stream().anyMatch(e -> e.get_remarks().equals("remarks")));
    }

    public void testUpdateState() {
        Simulation simulation = new Simulation(
                5000.00,
                5,
                client
        );

        Request request = new Request(
                LocalDate.now(),
                simulation.get_borrowed_capital(),
                STATE.PENDING,
                "remarks",
                5,
                simulation
        );

        Optional<Request> createdRequest = requestDao.create(request);
        assertTrue(createdRequest.isPresent());

        boolean isUpdated = requestDao.updateState(createdRequest.get().get_number(), STATE.APPROVED);
        assertTrue(isUpdated);

        Optional<Request> retrievedRequest = requestDao.findByID(createdRequest.get().get_number());
        assertTrue(retrievedRequest.isPresent());

        assertEquals(STATE.APPROVED, retrievedRequest.get().get_state());
    }

    @AfterEach
    public void tearDown() {
        agencyDao.deleteAll();
        clientDao.deleteAll();
        employeeDao.deleteAll();
        requestDao.deleteAll();
    }
}
