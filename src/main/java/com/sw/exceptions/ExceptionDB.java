package com.sw.exceptions;

public class ExceptionDB extends Exception {

    /**
     * Default constructor
     */
    public ExceptionDB(String message) {
        super("Erreur de base de données : " + message);
    }

    public ExceptionDB(String message, Throwable cause) {
        super(message, cause);
    }
}