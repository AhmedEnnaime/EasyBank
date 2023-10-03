package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    private Integer _id;

    private LocalDate _transfer_date;

    private Employee _employee;

    private Agency _agency;

    public Transfer(LocalDate transfer_date, Employee employee, Agency agency) {
        this._transfer_date = transfer_date;
        this._employee = employee;
        this._agency = agency;
    }

}