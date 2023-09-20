package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.exceptions.OperationException;

public interface OperationDao {
    public Operation create(Operation operation) throws OperationException;

    public boolean delete(int operationNumber);

    public Operation getByNumber(int operationNumber) throws OperationException;
}
