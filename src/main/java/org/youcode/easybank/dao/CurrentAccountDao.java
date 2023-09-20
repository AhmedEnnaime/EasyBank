package org.youcode.easybank.dao;

import org.youcode.easybank.entities.CurrentAccount;
import org.youcode.easybank.exceptions.CurrentAccountException;

public interface CurrentAccountDao {
    public CurrentAccount create(CurrentAccount currentAccount) throws CurrentAccountException;

    public CurrentAccount update(int accountNumber, CurrentAccount currentAccount) throws CurrentAccountException;
}
