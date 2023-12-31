package org.youcode.easybank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTestConnection {
    private static Connection conn;

    public static Connection establishTestConnection() {
        if (conn == null) {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/easybank";
            String username = "postgres";
            String password = "3ea14367A4";

            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(jdbcUrl, username, password);

                if (conn != null) {
                    System.out.println("Connection to testing PostgreSQL database established.");
                    createEmployeesTable(conn);
                    createClientsTable(conn);
                    createAccountsTable(conn);
                    createCurrentAccountsTable(conn);
                    createSavingsAccountsTable(conn);
                    createOperationsTable(conn);
                    createMissionsTable(conn);
                    createMissionAssignmentsTable(conn);
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void createEmployeesTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employees ("
                + "matricule SERIAL PRIMARY KEY,"
                + "firstName VARCHAR(255),"
                + "lastName VARCHAR(255),"
                + "recruitmentDate DATE,"
                + "birthDate DATE,"
                + "email VARCHAR(255),"
                + "phone VARCHAR(255),"
                + "address VARCHAR(255)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createClientsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS clients ("
                + "code SERIAL PRIMARY KEY,"
                + "firstName VARCHAR(255),"
                + "lastName VARCHAR(255),"
                + "birthDate DATE,"
                + "phone VARCHAR(255),"
                + "address VARCHAR(255)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createAccountsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts ("
                + "accountNumber SERIAL PRIMARY KEY,"
                + "balance DOUBLE PRECISION,"
                + "creationDate DATE,"
                + "status VARCHAR(255),"
                + "clientCode INT,"
                + "employeeMatricule INT,"
                + "FOREIGN KEY (clientCode) REFERENCES clients(code),"
                + "FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createSavingsAccountsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS savingsAccounts ("
                + "accountNumber INT PRIMARY KEY,"
                + "interestRate DOUBLE PRECISION,"
                + "FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createCurrentAccountsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS currentAccounts ("
                + "accountNumber INT PRIMARY KEY,"
                + "overdraft DOUBLE PRECISION,"
                + "FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createOperationsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS operations ("
                + "operationNumber SERIAL PRIMARY KEY,"
                + "creationDate DATE,"
                + "amount DOUBLE PRECISION,"
                + "type VARCHAR(255),"
                + "accountNumber INT,"
                + "employeeMatricule INT,"
                + "FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber),"
                + "FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createMissionsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS missions ("
                + "code SERIAL PRIMARY KEY,"
                + "nom VARCHAR(255),"
                + "description VARCHAR(255),"
                + "employeeMatricule INT,"
                + "FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createMissionAssignmentsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS missionAssignments ("
                + "debut_date TIMESTAMP,"
                + "end_date TIMESTAMP,"
                + "employee_matricule INT REFERENCES employees(matricule),"
                + "mission_code INT PRIMARY KEY REFERENCES missions(code)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
