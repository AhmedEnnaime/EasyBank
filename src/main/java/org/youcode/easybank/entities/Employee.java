package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends Person{
    private int _matricule;

    private LocalDate _recruitmentDate;

    private String _email;

    private List<Operation> _operations;

    private List<Mission> _missions;

    private List<Account> _accounts;

    private List<Client> _clients;

    public Employee(String lastName, String firstName, LocalDate birthDate, String phone, String address,
                    int matricule, LocalDate recruitmentDate, String email,
                    List<Operation> operations, List<Mission> missions, List<Account> accounts, List<Client> clients) {
        super(lastName, firstName, birthDate, phone, address);
        this._matricule = matricule;
        this._recruitmentDate = recruitmentDate;
        this._email = email;
        this._operations = operations;
        this._missions = missions;
        this._accounts = accounts;
        this._clients = clients;
    }

    public Employee(String lastName, String firstName, LocalDate birthDate, String phone, String address,
                    LocalDate recruitmentDate, String email) {
        super(lastName, firstName, birthDate, phone, address);
        this._recruitmentDate = recruitmentDate;
        this._email = email;
    }
}
