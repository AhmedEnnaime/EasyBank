package org.youcode.easybank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
//                    createUsersTable(conn);
//                    createBooksTable(conn);
//                    createLoansTable(conn);
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
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
