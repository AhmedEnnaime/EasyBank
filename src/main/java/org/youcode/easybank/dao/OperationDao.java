package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.exceptions.OperationException;

import java.util.Optional;

public interface OperationDao {
    public Optional<Operation> create(Operation operation) throws OperationException;

    public boolean delete(int operationNumber);

    public Optional<Operation> getByNumber(int operationNumber) throws OperationException;
}
