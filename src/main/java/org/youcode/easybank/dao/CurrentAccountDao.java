package org.youcode.easybank.dao;

import org.youcode.easybank.entities.CurrentAccount;
import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.CurrentAccountException;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.util.List;
import java.util.Optional;

public interface CurrentAccountDao {
    public Optional<CurrentAccount> create(CurrentAccount currentAccount) throws CurrentAccountException;

    public Optional<CurrentAccount> update(int accountNumber, CurrentAccount currentAccount) throws CurrentAccountException;

    public List<CurrentAccount> getAll() throws CurrentAccountException;

    public boolean deleteAll();

}
