package org.youcode.easybank.dao;

import org.youcode.easybank.entities.SimpleOperation;

import java.util.Optional;

public interface SimpleOperationDao {

    public Optional<SimpleOperation> create(SimpleOperation simpleOperation);
}
