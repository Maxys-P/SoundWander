package com.sw.facades;

public class FacadeUser {

    public FacadeUser() {
        // Initialisation si nécessaire
    }

    public boolean validateCredentials(String identifiant, String motDePasse) {
        // Validation des identifiants (remplacez cela par votre logique de validation réelle)
        return "admin".equals(identifiant) && "admin".equals(motDePasse);
    }

    /**
     *
     */
    public void getInfo() {
        // TODO implement here
    }

}

