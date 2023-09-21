package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.EmployeeException;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    public Optional<Employee> create(Employee employee) throws EmployeeException;

    public Optional<Employee> update(int matricule, Employee employee) throws EmployeeException;

    public boolean delete(int matricule);

    public Optional<Employee> getByMatricule(int matricule) throws EmployeeException;

    public List<Employee> getAll() throws EmployeeException;
}
