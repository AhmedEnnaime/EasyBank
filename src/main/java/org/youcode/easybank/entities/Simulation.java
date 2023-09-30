package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulation {

    private Integer _id;

    private Double _monthly_payment;

    private Double _borrowed_capital;

    private Integer _monthly_payment_num;

    private Double _result;

    private Client _client;

    private Employee _employee;

}
