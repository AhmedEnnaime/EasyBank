package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Client;
import org.youcode.easybank.exceptions.ClientException;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    public Optional<Client> create(Client client) throws ClientException;

    public Optional<Client> update(int code, Client client) throws ClientException;

    public boolean delete(int code);

    public Optional<Client> getByCode(int code) throws ClientException;

    public List<Client> getAll() throws ClientException;
}
