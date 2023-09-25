package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.util.List;
import java.util.Optional;

public interface SavingsAccountDao {
    public Optional<SavingsAccount> create(SavingsAccount savingsAccount) throws SavingsAccountException;

    public Optional<SavingsAccount> update(int accountNumber, SavingsAccount savingsAccount) throws SavingsAccountException;
<<<<<<< HEAD

    public List<SavingsAccount> getAll() throws SavingsAccountException;

    public boolean deleteAll();
=======
>>>>>>> parent of a8abe09 (EAS-10 completed create account,savingsAccount, currentAccount and their testing)
}
