package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.dao.daoImpl.EmployeeDaoImpl;
import org.youcode.easybank.dao.daoImpl.MissionAssignmentDaoImpl;
import org.youcode.easybank.dao.daoImpl.MissionDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.entities.MissionAssignment;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.MissionAssignmentException;
import org.youcode.easybank.exceptions.MissionException;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MissionAssignmentDaoImplTest {

    private MissionDaoImpl missionDao;

    private EmployeeDaoImpl employeeDao;

    private MissionAssignmentDaoImpl missionAssignmentDao;

    private AgencyDaoImpl agencyDao;

    private Employee employee1;

    private Agency agency;

    private int testMissionCode;

    private int testID;

    @BeforeEach
    public void setUp() throws MissionException, MissionAssignmentException {

        Connection testConnection = DBTestConnection.establishTestConnection();

        missionDao = new MissionDaoImpl(testConnection);

        employeeDao = new EmployeeDaoImpl(testConnection);

        missionAssignmentDao = new MissionAssignmentDaoImpl(testConnection);

        agencyDao = new AgencyDaoImpl(testConnection);


        Mission mission = new Mission(
                "Test Mission",
                "Test description"
        );

        missionDao.create(mission);
        testMissionCode = mission.get_code();

        agency = new Agency(
                "YouCode",
                "test address",
                "05248137133"
        );

        agencyDao.create(agency);

        employee1 = new Employee(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat",
                LocalDate.of(2023, 9, 21),
                "mousta@gmail.com",
                agency
        );

        Employee employee2 = new Employee(
                "Abdelali",
                "Hotgame",
                LocalDate.of(1990, 5, 15),
                "0682332783924",
                "hay anass",
                LocalDate.of(2023, 9, 21),
                "hotgam@gmail.com",
                agency
        );

        employeeDao.create(employee1);
        employeeDao.create(employee2);

        ArrayList<Employee> employeesList = new ArrayList<>();
        employeesList.add(employee1);
        employeesList.add(employee2);

        MissionAssignment missionAssignment = new MissionAssignment(
                LocalDate.of(2023, 9, 21),
                LocalDate.of(2023, 12, 21),
                employeesList,
                mission
        );

        missionAssignmentDao.create(missionAssignment);

        testID = missionAssignment.getId();

    }

    public void assertMissionAssignmentEquals(MissionAssignment expected, MissionAssignment actual) {
        assertEquals(expected.get_debutDate(), actual.get_debutDate());
        assertEquals(expected.get_endDate(), actual.get_endDate());
    }

    @Test
    public void testCreatet() throws MissionAssignmentException, MissionException {
        Mission mission = new Mission("Test Mission", "Test description");
        missionDao.create(mission);

        Employee employee3 = new Employee(
                "Hamza",
                "Kamal",
                LocalDate.of(2000, 8, 21),
                "06400347924",
                "jjjj",
                LocalDate.of(2023, 9, 21),
                "hamza@gmail.com",
                agency
        );

        Employee employee2 = new Employee(
                "Mouad",
                "Othmane",
                LocalDate.of(1990, 5, 15),
                "0645683924",
                "hhhh",
                LocalDate.of(2023, 9, 21),
                "mouad@gmail.com",
                agency
        );

        employeeDao.create(employee3);
        employeeDao.create(employee2);

        ArrayList<Employee> employeesList = new ArrayList<>();
        employeesList.add(employee3);
        employeesList.add(employee2);

        MissionAssignment missionAssignment = new MissionAssignment(
                LocalDate.of(2023, 9, 21),
                LocalDate.of(2023, 12, 21),
                employeesList,
                mission
        );

        Optional<MissionAssignment> createdAssignment = missionAssignmentDao.create(missionAssignment);

        assertTrue(createdAssignment.isPresent());
        assertNotNull(createdAssignment.get().getId());
        assertEquals(LocalDate.of(2023, 9, 21), createdAssignment.get().get_debutDate());
        assertEquals(LocalDate.of(2023, 12, 21), createdAssignment.get().get_endDate());
    }

    @Test
    public void testGetByID() throws MissionAssignmentException, MissionException, EmployeeException {
        Mission mission = new Mission("Test Mission", "Test description");
        missionDao.create(mission);

        Employee employee = new Employee(
                "Hamza",
                "Kamal",
                LocalDate.of(2000, 8, 21),
                "06400347924",
                "jjjj",
                LocalDate.of(2023, 9, 21),
                "hamza@gmail.com",
                agency
        );

        employeeDao.create(employee);
        ArrayList<Employee> employeesList = new ArrayList<>();
        employeesList.add(employee);

        MissionAssignment missionAssignment = new MissionAssignment(
                LocalDate.of(2023, 9, 21),
                LocalDate.of(2023, 12, 21),
                employeesList,
                mission
        );

        Optional<MissionAssignment> createdAssignment = missionAssignmentDao.create(missionAssignment);

        assertTrue(createdAssignment.isPresent());

        Optional<MissionAssignment> retrievedMissionAssignment = missionAssignmentDao.getByID(testID);
        assertTrue(retrievedMissionAssignment.isPresent());

       assertMissionAssignmentEquals(createdAssignment.get(), retrievedMissionAssignment.get());
    }

    @Test
    public void testGetEmployeeAssignments() throws MissionAssignmentException {
        List<MissionAssignment> assignments = missionAssignmentDao.getEmployeeAssignments(employee1);
        assertTrue(assignments.size() > 0);

    }

    @Test
    public void testDelete() throws MissionAssignmentException {

        boolean isDeleted = missionAssignmentDao.delete(testID);
        assertTrue(isDeleted);

        Optional<MissionAssignment> retrievedMissionAssignment = missionAssignmentDao.getByID(testID);
        assertFalse(retrievedMissionAssignment.isPresent());
    }

    @Test
    public void testGetStats() throws MissionAssignmentException {
        assertNotNull(missionAssignmentDao);
        Optional<HashMap<Integer, Integer>> stats = missionAssignmentDao.getStats();
        assertTrue(stats.isPresent());

        HashMap<Integer, Integer> missionStats = stats.get();
        assertNotNull(missionStats);

        assertTrue(missionStats.containsKey(testMissionCode));
        assertTrue(missionStats.get(testMissionCode) > 0);
    }


    @AfterEach
    public void tearDown() {
        missionDao.deleteAll();
        employeeDao.deleteAll();
        missionAssignmentDao.deleteAll();
    }

}
