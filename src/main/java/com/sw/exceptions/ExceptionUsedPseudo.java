package com.sw.exceptions;

public class ExceptionUsedPseudo extends Exception {

    /**
     * Default constructor
     */
    public ExceptionUsedPseudo(String message) {
        super("Le pseudo est déjà utilisé : " + message);
    }

}