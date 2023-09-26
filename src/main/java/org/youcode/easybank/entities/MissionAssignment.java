package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionAssignment {

    private int id;

    private LocalDate _debutDate;

    private LocalDate _endDate;

    private List<Employee> _employees;

    private Mission _mission;

    public MissionAssignment(LocalDate debutDate, LocalDate endDate, List<Employee> employees, Mission mission) {
        this._debutDate = debutDate;
        this._endDate = endDate;
        this._employees = employees;
        this._mission = mission;
    }
}
