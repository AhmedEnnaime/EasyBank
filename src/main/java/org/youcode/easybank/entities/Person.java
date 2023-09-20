package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String _lastName;

    private String _firstName;

    private LocalDate _birthDate;

    private String _phone;

    private String _address;
}
