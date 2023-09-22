package org.youcode.easybank.daoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.db.DBTestConnection;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.exceptions.ClientException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDaoImplTest {
    private ClientDaoImpl clientDao;

    private int testCode;


    @BeforeEach
    public void setUp() throws ClientException {
        Connection testConnection = DBTestConnection.establishTestConnection();

        clientDao = new ClientDaoImpl(testConnection);

        Client client = new Client(
                "Mousta",
                "Delegue",
                LocalDate.of(2001, 11, 17),
                "06473347924",
                "Jrayfat"
        );

        clientDao.create(client);

        testCode = client.get_code();

    }

    void assertClientsEqual(Client expected, Client actual) {
        assertEquals(expected.get_firstName(), actual.get_firstName());
        assertEquals(expected.get_lastName(), actual.get_lastName());
        assertEquals(expected.get_birthDate(), actual.get_birthDate());
        assertEquals(expected.get_phone(), actual.get_phone());
        assertEquals(expected.get_address(), actual.get_address());
    }

    @Test
    public void testCreate() throws ClientException {
        Client client = new Client(
                "Abdelali",
                "Hotgame",
                LocalDate.of(1990, 5, 15),
                "0682332783924",
                "hay anass"
        );

        Optional<Client> createdClient = clientDao.create(client);
        assertTrue(createdClient.isPresent());
        assertEquals(client.get_firstName(), createdClient.get().get_firstName());
        assertEquals(client.get_lastName(), createdClient.get().get_lastName());
        assertEquals(client.get_birthDate(), createdClient.get().get_birthDate());
        assertEquals(client.get_phone(), createdClient.get().get_phone());
        assertEquals(client.get_address(), createdClient.get().get_address());
    }

    @Test
    public void testUpdate() throws ClientException {
        Client updatedClient = new Client(
                "UpdatedName",
                "UpdatedLastName",
                LocalDate.of(1995, 3, 10),
                "061234567890",
                "UpdatedAddress"
        );

        Optional<Client> updatedClientOptional = clientDao.update(testCode, updatedClient);
        assertTrue(updatedClientOptional.isPresent());

        Optional<Client> retrievedUpdatedClient = clientDao.getByCode(testCode);
        assertTrue(retrievedUpdatedClient.isPresent());

        assertEquals(updatedClient.get_firstName(), retrievedUpdatedClient.get().get_firstName());
        assertEquals(updatedClient.get_lastName(), retrievedUpdatedClient.get().get_lastName());
        assertEquals(updatedClient.get_birthDate(), retrievedUpdatedClient.get().get_birthDate());
        assertEquals(updatedClient.get_phone(), retrievedUpdatedClient.get().get_phone());
        assertEquals(updatedClient.get_address(), retrievedUpdatedClient.get().get_address());
    }

    @Test
    public void testDelete() throws ClientException {
        boolean isDeleted = clientDao.delete(testCode);

        assertTrue(isDeleted);
        Optional<Client> deletedClient = clientDao.getByCode(testCode);
        assertFalse(deletedClient.isPresent());
    }

    @Test
    public void testGetByCode() throws ClientException {
        Client client = new Client(
                "Salah",
                "Mohammed",
                LocalDate.of(2003, 4, 23),
                "064782487924",
                "Jrayfat"
        );

        Optional<Client> createdClient = clientDao.create(client);
        assertTrue(createdClient.isPresent());

        Optional<Client> retrievedClient = clientDao.getByCode(createdClient.get().get_code());
        assertTrue(retrievedClient.isPresent());
        assertClientsEqual(createdClient.get(), retrievedClient.get());

    }

    @Test
    public void testGetAll() throws ClientException {
        List<Client> allClients = clientDao.getAll();
        assertNotNull(allClients);
        assertFalse(allClients.isEmpty());

        assertTrue(allClients.stream().anyMatch(e -> e.get_lastName().equals("Mousta")));
    }

    @Test
    public void testFindByAttribute() throws ClientException {

        Client client1 = new Client(
                "John",
                "Doe",
                LocalDate.of(1990, 1, 15),
                "1234567890",
                "123 Main St"
        );

        Client client2 = new Client(
                "Jane",
                "Doe",
                LocalDate.of(1995, 5, 20),
                "9876543210",
                "456 Elm St"
        );

        clientDao.create(client1);
        clientDao.create(client2);

        List<Client> foundClients = clientDao.findByAttribute("John");
        assertNotNull(foundClients);
        assertEquals(1, foundClients.size());
        assertEquals("Doe", foundClients.get(0).get_firstName());

        foundClients = clientDao.findByAttribute("456 Elm St");
        assertNotNull(foundClients);
        assertEquals(1, foundClients.size());
        assertEquals("456 Elm St", foundClients.get(0).get_address());

        foundClients = clientDao.findByAttribute("NonExistent");
        assertNotNull(foundClients);
        assertEquals(0, foundClients.size());
    }

    @AfterEach
    public void tearDown() {
        clientDao.deleteAll();
    }
}
