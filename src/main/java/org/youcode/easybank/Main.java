package org.youcode.easybank;

import org.youcode.easybank.db.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection.getInstance().establishConnection();
    }
}