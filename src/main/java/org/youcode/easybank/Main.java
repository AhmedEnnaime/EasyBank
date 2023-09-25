package org.youcode.easybank;

import org.youcode.easybank.services.AccountService;
import org.youcode.easybank.services.ClientService;
import org.youcode.easybank.services.EmployeeService;

public class Main {
    public static void main(String[] args) {

        EmployeeService.createEmployee();
        ClientService.createClient();


    }
}