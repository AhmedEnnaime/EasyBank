package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.OPERATION;

import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private int _operationNumber;

    private SimpleDateFormat _creationDate;

    private double _amount;

    private OPERATION _type;

    private Employee _employee;

    private Account _account;
}
