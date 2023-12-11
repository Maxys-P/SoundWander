package com.sw.exceptions;

public class ExceptionSQL extends Exception {

    public ExceptionSQL(String message) {
        super("Problème SQL : " + message);
    }

}
