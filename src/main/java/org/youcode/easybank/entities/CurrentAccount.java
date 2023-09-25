package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.STATUS;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount extends Account{
    private double _overdraft;

    public CurrentAccount(Account account, double overdraft) {
        super(account);
        this._overdraft = overdraft;
    }

}
