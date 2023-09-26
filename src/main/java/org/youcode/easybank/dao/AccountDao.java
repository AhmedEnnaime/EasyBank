package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.AccountException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountDao {

    public Optional<Account> create(Account account) throws AccountException;

    public Optional<Account> update(int accountNumber, Account account) throws AccountException;

    public boolean delete(int accountNumber);

    public Optional<Account> getByAccountNumber(int accountNumber) throws AccountException;

    public List<Account> getByCreationDate(LocalDate date) throws AccountException;

    public List<Account> getByStatus(STATUS status) throws AccountException;

    public boolean updateStatus(int accountNumber, STATUS newStatus);

    public List<Optional<Account>> getClientAccounts(Client client) throws AccountException;

    public boolean updateBalance(Account account, Operation operation);

    public boolean deleteAll();
}
