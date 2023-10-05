package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.OPERATION;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    protected int _operationNumber;

    protected LocalDateTime _creationDate;

    protected double _amount;

    protected Employee _employee;

    public Operation(double amount, Employee employee) {
        this._amount = amount;
        this._employee = employee;
    }

    public Operation(Operation operation) {
        this._operationNumber = operation.get_operationNumber();
        this._creationDate = operation.get_creationDate();
        this._amount = operation.get_amount();
        this._employee = operation.get_employee();
    }
}
