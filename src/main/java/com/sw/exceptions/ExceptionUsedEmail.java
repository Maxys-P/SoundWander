package com.sw.exceptions;

public class ExceptionUsedEmail extends Exception {

    /**
     * Default constructor
     */
    public ExceptionUsedEmail(String message) {
        super("L'email est déjà utilisé : " + message);
    }

}