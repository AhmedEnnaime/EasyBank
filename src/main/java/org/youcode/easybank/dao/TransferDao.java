package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Transfer;

import java.util.Optional;

public interface TransferDao {
    public Optional<Transfer> transferEmployee(Transfer transfer);

    public boolean deleteAll();
}
