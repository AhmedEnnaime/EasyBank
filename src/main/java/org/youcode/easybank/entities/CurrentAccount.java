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

    public CurrentAccount(int accountNumber, double balance, LocalDate creationDate, STATUS status, Employee employee, Client client, List<Operation> operations, double overdraft) {
        super(accountNumber, balance, creationDate, status, employee, client, operations);
        this._overdraft = overdraft;
    }
}
