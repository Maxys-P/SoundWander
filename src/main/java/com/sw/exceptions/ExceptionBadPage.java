package com.sw.exceptions;

public class ExceptionBadPage extends Exception {
    public ExceptionBadPage(String message) {
        super("La page n'existe pas : " + message);
    }
}
