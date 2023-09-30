package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agency {

    private Integer _code;

    private String _name;

    private String _address;

    private String _phone;

    private List<Employee> _employees;

    private List<Account> _accounts;

    public Agency(String name, String address, String phone) {
        this._name = name;
        this._address = address;
        this._phone = phone;
    }
}
