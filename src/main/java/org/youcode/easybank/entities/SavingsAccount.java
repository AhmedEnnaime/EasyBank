package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.STATUS;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccount extends Account{

    private double _interestRate;

    public SavingsAccount(Account account, double interestRate) {
        super(account);
        this._interestRate = interestRate;
    }

}
