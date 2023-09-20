package org.youcode.easybank.dao;

import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.SavingsAccountException;

public interface SavingsAccountDao {
    public SavingsAccount create(SavingsAccount savingsAccount) throws SavingsAccountException;

    public SavingsAccount update(int accountNumber, SavingsAccount savingsAccount) throws SavingsAccountException;
}
