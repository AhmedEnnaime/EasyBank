package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.AccountDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.AccountException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final Connection conn;

    public AccountDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public AccountDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Account> create(Account account) throws AccountException {
        return Optional.empty();
    }

    @Override
    public Optional<Account> update(int accountNumber, Account account) throws AccountException {
        return Optional.empty();
    }

    @Override
    public boolean delete(int accountNumber) {
        return false;
    }

    @Override
    public Optional<Account> getByAccountNumber(int accountNumber) throws AccountException {
        return Optional.empty();
    }

    @Override
    public List<Account> getAll() throws AccountException {
        return null;
    }

    @Override
    public List<Account> getByCreationDate() throws AccountException {
        return null;
    }

    @Override
    public List<Account> getByStatus(STATUS status) throws AccountException {
        return null;
    }

    @Override
    public boolean updateStatus(int accountNumber) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
