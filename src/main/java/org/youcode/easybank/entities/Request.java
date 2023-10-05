package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.STATE;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    private Integer _number;

    private LocalDate _credit_date;

    private Double _amount;

    private STATE _state;

    private String _remarks;

    private Integer _duration;

    private Simulation _simulation;

    public Request(LocalDate credit_date, Double amount, STATE state, String remarks, Integer duration, Simulation simulation) {
        this._credit_date = credit_date;
        this._amount = amount;
        this._state = state;
        this._remarks = remarks;
        this._duration = duration;
        this._simulation = simulation;
    }
}
