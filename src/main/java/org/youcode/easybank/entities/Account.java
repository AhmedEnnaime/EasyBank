package org.youcode.easybank.entities;

import lombok.*;
import org.youcode.easybank.enums.STATUS;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String _accountNumber;

    private double _balance;

    private LocalDate _creationDate;

    private STATUS _status;

    private Employee _employee;

    private Client _client;

    private List<Operation> _operations;
}
