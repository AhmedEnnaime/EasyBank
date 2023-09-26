package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.MissionDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.exceptions.MissionException;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.Optional;

public class MissionDaoImplTest {

    private MissionDaoImpl missionDao;

    private int testMissionCode;

    @BeforeEach
    public void setUp() throws MissionException {
        Connection testConnection = DBTestConnection.establishTestConnection();

        missionDao = new MissionDaoImpl(testConnection);

        Mission mission = new Mission(
                "Test Mission",
                "Test description"
        );

        missionDao.create(mission);
        testMissionCode = mission.get_code();

    }

    public void assertMissionsEquals(Mission expected, Mission actual) {
        assertEquals(expected.get_name(), actual.get_name());
        assertEquals(expected.get_description(), actual.get_description());
    }

    @Test
    public void testCreate() throws MissionException {

        Mission mission = new Mission(
                "Test Mission 1",
                "Test description 1"
        );

        Optional<Mission> createdMission = missionDao.create(mission);
        assertTrue(createdMission.isPresent());

        assertEquals(mission.get_name(), createdMission.get().get_name());
        assertEquals(mission.get_description(), createdMission.get().get_description());
    }

    @Test
    public void testDelete() throws MissionException {
        boolean isDeleted = missionDao.delete(testMissionCode);

        assertTrue(isDeleted);
        Optional<Mission> deletedMission = missionDao.getByNumber(testMissionCode);
        assertFalse(deletedMission.isPresent());
    }

    @Test
    public void testGetByNumber() throws MissionException {

        Mission mission = new Mission(
                "Test Mission 2",
                "Test description 2"
        );

        Optional<Mission> createdMission = missionDao.create(mission);
        assertTrue(createdMission.isPresent());

        Optional<Mission> retrievedMission = missionDao.getByNumber(createdMission.get().get_code());
        assertTrue(retrievedMission.isPresent());
        assertMissionsEquals(createdMission.get(), retrievedMission.get());

    }
    @AfterEach
    public void tearDown() {
        missionDao.deleteAll();
    }
}
