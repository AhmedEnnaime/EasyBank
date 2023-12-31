package org.youcode.easybank.dao;

import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.util.Optional;

public interface SavingsAccountDao {
    public Optional<SavingsAccount> create(SavingsAccount savingsAccount) throws SavingsAccountException;

    public Optional<SavingsAccount> update(int accountNumber, SavingsAccount savingsAccount) throws SavingsAccountException;
}
