package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.util.List;
import java.util.Optional;

public interface SavingsAccountDao extends IData<SavingsAccount, Integer>{

}
