package com.sw.controllers.users;

import com.sw.facades.Facade;
import com.sw.classes.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.exceptions.ExceptionFormIncomplete;

import java.time.LocalDate;



/**
 * Controller pour la page d'incription
 */
public class ControllerRegister extends ControllerUser {

    @FXML
    private TextField mail;

    @FXML
    private TextField pseudo;

    @FXML
    private TextField motDePasse;

    @FXML
    private TextField motDePasseConfirm;

    @FXML
    private DatePicker dateNaissance;

    @FXML
    private Button boutonInscrire;

    @FXML
    private Label labelConnexion;

    @FXML
    private Label labelInscription;

    @FXML
    private Button boutonVisitor;

    @FXML
    private Text errorText;

// =====================================================================================================================

    /**
     * Méthode appelée lors de la tentative d'inscription
     */
    @FXML
    private void handleButtonRegister() {
        try {
            verifForm();
            verifPassword();
            LocalDate localDate = dateNaissance.getValue();
            User user = super.userFacade.inscription(mail.getText(), pseudo.getText(), motDePasse.getText(), localDate);
            if (user != null) {
                super.hideError(errorText);
                Facade.currentUser = user;
                goToHome();
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode qui redirige vers la page d'accueil interne à l'application
     */
    @FXML
    private void goToHome() {
        try {
            super.hideError(errorText);
            super.goToHome(boutonInscrire, "user");
        } catch (ExceptionBadPage e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode qui redirige vers la page de connexion
     */
    @FXML
    private void goToLogin() {
        try {
            super.hideError(errorText);
            super.goToPage(labelConnexion, "users/login-view.fxml", "Connexion");
        } catch (ExceptionBadPage e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode qui redirige vers la page d'accueil externe à l'application
     */
    @FXML
    private void goToVisitor() {
        try {
            super.hideError(errorText);
            super.goToVisitor(boutonVisitor);
        } catch (ExceptionBadPage e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Méthode qui vérifie que le mot de passe est identique à la confirmation
     * @throws ExceptionFormIncomplete si le mot de passe et la confirmation sont identiques
     */
    private void verifPassword() throws ExceptionFormIncomplete {
        if (!motDePasse.getText().equals(motDePasseConfirm.getText()) || motDePasse.getText().isEmpty() || motDePasseConfirm.getText().isEmpty()) {
            throw new ExceptionFormIncomplete("Les mots de passe ne correspondent pas");
        }
    }

    /**
     * Méthode qui vérifie que tous les champs sont remplis
     * @throws ExceptionFormIncomplete si un champ n'est pas rempli
     */
    private void verifForm() throws ExceptionFormIncomplete {
        if (mail.getText().isEmpty() || pseudo.getText().isEmpty() || motDePasse.getText().isEmpty() || motDePasseConfirm.getText().isEmpty()) {
            throw new ExceptionFormIncomplete("Veuillez remplir tous les champs");
        }
        if (!mail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ExceptionFormIncomplete("Adresse mail non valide");
        }
    }



}
