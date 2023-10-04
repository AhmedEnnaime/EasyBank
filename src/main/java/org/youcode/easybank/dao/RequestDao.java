package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Request;
import org.youcode.easybank.enums.STATE;

import java.util.List;
import java.util.Optional;

public interface RequestDao {

    public Optional<Request> create(Request request);

    public Optional<Request> findByID(Integer number);

    public List<Request> getAll();

    public boolean updateState(Integer number, STATE state);

    public List<Request> getByState(STATE state);

    public boolean deleteAll();

}
