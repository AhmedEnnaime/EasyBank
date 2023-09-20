package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.STATUS;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccount extends Account{

    private double _interestRate;

    public SavingsAccount(String accountNumber, double balance, SimpleDateFormat creationDate, STATUS status, Employee employee, Client client, List<Operation> operations, double interestRate) {
        super(accountNumber, balance, creationDate, status, employee, client, operations);
        this._interestRate = interestRate;
    }
}
