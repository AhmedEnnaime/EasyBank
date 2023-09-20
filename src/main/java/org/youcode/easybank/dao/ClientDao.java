package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Client;
import org.youcode.easybank.exceptions.ClientException;

import java.util.List;

public interface ClientDao {
    public Client create(Client client) throws ClientException;

    public Client update(int code, Client client) throws ClientException;

    public boolean delete(int code);

    public Client getByCode(int code) throws ClientException;

    public List<Client> getAll() throws ClientException;
}
