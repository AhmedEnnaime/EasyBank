package org.youcode.easybank;

import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.services.AccountService;
import org.youcode.easybank.services.ClientService;
import org.youcode.easybank.services.EmployeeService;

public class Main {
    public static void main(String[] args) {

//        EmployeeService.createEmployee();
//        ClientService.createClient();

//        try {
//            AccountService.createAccount();
//        }catch (ClientException | EmployeeException a) {
//            a.printStackTrace();
//        }

        AccountService.updateAccount();

    }
}