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

    private Client _client;

    private Employee _employee;

    public Simulation(Double monthly_payment, Double borrowed_capital, Integer monthly_payment_num, Client client) {
        this._monthly_payment = monthly_payment;
        this._borrowed_capital = borrowed_capital;
        this._monthly_payment_num = monthly_payment_num;
        this._client = client;
    }

}
