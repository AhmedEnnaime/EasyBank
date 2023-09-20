package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.EmployeeException;

import java.util.List;

public interface EmployeeDao {
    public Employee create(Employee employee) throws EmployeeException;

    public Employee update(int matricule, Employee employee) throws EmployeeException;

    public boolean delete(int matricule);

    public Employee getByMatricule(int matricule) throws EmployeeException;

    public List<Employee> getAll() throws EmployeeException;
}
