package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Client;
import org.youcode.easybank.exceptions.ClientException;

import java.util.List;
import java.util.Optional;

public interface ClientDao extends IData<Client, Integer>{

    public List<Client> findByAttribute(String attr) throws ClientException;

}
