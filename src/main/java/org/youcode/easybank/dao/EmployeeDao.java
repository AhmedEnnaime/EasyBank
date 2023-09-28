package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.EmployeeException;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends IData<Employee, Integer>{

    public List<Employee> findByAttribute(String attr) throws EmployeeException;

    public boolean validateMatricule(int matricule) throws EmployeeException;
}
