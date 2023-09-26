package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {
    private int _code;

    private String _name;

    private String _description;

    public Mission(String name, String description) {
        this._name = name;
        this._description = description;
    }
}
