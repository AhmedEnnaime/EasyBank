package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Account;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.AccountException;

import java.util.List;

public interface AccountDao {
    public boolean delete(int accountNumber);

    public Account getByAccountNumber(int accountNumber) throws AccountException;

    public List<Account> getAll() throws AccountException;

    public List<Account> getByCreationDate() throws AccountException;

    public List<Account> getByStatus(STATUS status) throws AccountException;

    public boolean updateStatus(int accountNumber);
}
