package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Agency;

import java.sql.Connection;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class AgencyDaoImplTest {

    private AgencyDaoImpl agencyDao;

    private Agency agency;

    private Integer agency_code;

    @BeforeEach
    public void setUp() {

        Connection testConnection = DBTestConnection.establishTestConnection();

        agencyDao = new AgencyDaoImpl(testConnection);

        agency = new Agency(
                "Trionix",
                "biyada",
                "0728673822442"
        );

        agencyDao.create(agency);

        agency_code = agency.get_code();

    }

    @Test
    public void testCreate() {
        Agency agency1 = new Agency(
                "Zerpin",
                "safi",
                "052471236132"
        );

        Optional<Agency> createdAgency = agencyDao.create(agency1);
        assertTrue(createdAgency.isPresent());
        assertEquals(agency1.get_name(), createdAgency.get().get_name());
        assertEquals(agency1.get_address(), createdAgency.get().get_address());
        assertEquals(agency1.get_phone(), createdAgency.get().get_phone());
    }
}
