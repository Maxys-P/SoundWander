package com.sw.exceptions;

public class ExceptionBadLogin extends Exception {
    public ExceptionBadLogin(String message) {
        super("Le login est incorrect : " + message);
    }
}
