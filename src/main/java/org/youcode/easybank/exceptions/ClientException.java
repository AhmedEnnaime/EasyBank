package org.youcode.easybank.exceptions;

public class ClientException extends Exception{
    public ClientException() {}

    public ClientException(String str) {
        super(str);
    }
}
