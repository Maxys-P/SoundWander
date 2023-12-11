package com.sw.exceptions;

public class ExceptionBadPassword extends Exception {
    public ExceptionBadPassword(String message) {
        super("Le mot de passe est incorrect : " + message);
    }
}
