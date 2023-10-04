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
                    createAgenciesTable(conn);
                    createEmployeesTable(conn);
                    createClientsTable(conn);
                    createAccountsTable(conn);
                    createCurrentAccountsTable(conn);
                    createSavingsAccountsTable(conn);
                    createOperationsTable(conn);
                    createMissionsTable(conn);
                    createMissionAssignmentsTable(conn);
                    createTransfersTable(conn);
                    createSimulationsTable(conn);
                    createRequestsTable(conn);
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
                + "address VARCHAR(255),"
                + "agency_code INT"
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
                + "address VARCHAR(255),"
                + "employeeMatricule INT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createAccountsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS accounts ("
                + "accountNumber SERIAL PRIMARY KEY,"
                + "balance DOUBLE PRECISION,"
                + "creationDate DATE DEFAULT CURRENT_DATE,"
                + "status VARCHAR(255) DEFAULT 'ACTIVE',"
                + "clientCode INT,"
                + "employeeMatricule INT,"
                + "agency_code INT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createSavingsAccountsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS savingsAccounts ("
                + "accountNumber INT PRIMARY KEY,"
                + "interestRate DOUBLE PRECISION"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createCurrentAccountsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS currentAccounts ("
                + "accountNumber INT PRIMARY KEY,"
                + "overdraft DOUBLE PRECISION"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createOperationsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS operations ("
                + "operationNumber SERIAL PRIMARY KEY,"
                + "creationDate DATE DEFAULT CURRENT_DATE,"
                + "amount DOUBLE PRECISION,"
                + "type VARCHAR(255),"
                + "accountNumber INT,"
                + "employeeMatricule INT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createMissionsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS missions ("
                + "code SERIAL PRIMARY KEY,"
                + "nom VARCHAR(255),"
                + "description VARCHAR(255)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createMissionAssignmentsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS missionAssignments ("
                + "id SERIAL PRIMARY KEY,"
                + "debut_date TIMESTAMP,"
                + "end_date TIMESTAMP,"
                + "employee_matricule INT,"
                + "mission_code INT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createAgenciesTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS agencies ("
                + "code SERIAL PRIMARY KEY,"
                + "name VARCHAR(255),"
                + "address VARCHAR(255),"
                + "phone VARCHAR(12)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createTransfersTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transfers ("
                + "id SERIAL PRIMARY KEY,"
                + "transfer_date DATE,"
                + "employee_matricule INT,"
                + "agency_code INT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createSimulationsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS simulations ("
                + "id SERIAL PRIMARY KEY,"
                + "monthly_payment DOUBLE PRECISION,"
                + "borrowed_capital DOUBLE PRECISION,"
                + "monthly_payment_num INT,"
                + "state VARCHAR(255) DEFAULT 'PENDING',"
                + "result DOUBLE PRECISION"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createRequestsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS requests ("
                + "number SERIAL PRIMARY KEY,"
                + "credit_date DATE DEFAULT CURRENT_DATE,"
                + "amount DOUBLE PRECISION,"
                + "remarks VARCHAR(255),"
                + "duration VARCHAR(255),"
                + "simulation_id INT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }

    public static void createPaymentsTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS payments ("
                + "id SERIAL PRIMARY KEY,"
                + "transaction_time TIMESTAMP NOT NULL,"
                + "from_account INT NOT NULL,"
                + "to_account INT NOT NULL,"
                + "operationNumber INT NOT NULL"
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
