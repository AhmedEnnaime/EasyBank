package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.OPERATION;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private int _operationNumber;

    private LocalDate _creationDate;

    private double _amount;

    private OPERATION _type;

    private Employee _employee;

    private Account _account;

    public Operation(double amount, OPERATION type, Employee employee, Account account) {
        this._amount = amount;
        this._type = type;
        this._employee = employee;
        this._account = account;
    }
}
