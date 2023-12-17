package com.sw.exceptions;

public class ExceptionFormIncomplete extends Exception {
    public ExceptionFormIncomplete(String message) {
        super("Le formulaire est incomplet : " + message);
    }

}
