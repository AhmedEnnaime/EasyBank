package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.EmployeeDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.EmployeeException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection conn;

    public EmployeeDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public EmployeeDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Employee> create(Employee employee) throws EmployeeException {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> update(int matricule, Employee employee) throws EmployeeException {
        return Optional.empty();
    }

    @Override
    public boolean delete(int matricule) {
        return false;
    }

    @Override
    public Optional<Employee> getByMatricule(int matricule) throws EmployeeException {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() throws EmployeeException {
        return null;
    }
}
