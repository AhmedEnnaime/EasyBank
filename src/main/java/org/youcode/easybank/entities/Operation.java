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
}
