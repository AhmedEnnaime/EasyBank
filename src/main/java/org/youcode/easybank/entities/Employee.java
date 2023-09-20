package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int _matricule;

    private String _lastName;

    private String _firstName;

    private SimpleDateFormat _recruitmentDate;

    private SimpleDateFormat _birthDate;

    private String _email;

    private String _phone;

    private Account _account;

    private List<Operation> _operations;

    private List<Mission> _missions;
}
