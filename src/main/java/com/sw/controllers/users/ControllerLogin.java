package com.sw.controllers.users;

import com.sw.classes.Admin;
import com.sw.classes.Artist;
import com.sw.classes.MusicalExpert;
import com.sw.classes.User;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.exceptions.ExceptionFormIncomplete;
import com.sw.facades.Facade;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Contrôleur pour la gestion de la vue de connexion.
 * @see ControllerUser
 */
public class ControllerLogin extends ControllerUser {

    // Déclarations des composants de l'interface utilisateur liés au FXML.

    @FXML
    private TextField mail; // Champ de saisie pour le mail.

    @FXML
    private PasswordField motDePasse; // Champ de saisie pour le mot de passe.

    @FXML
    private Text errorText; // Label pour afficher les messages d'erreur.

    @FXML
    private Label labelRegistration; // Label pour afficher le lien vers la page d'inscription.

    @FXML
    private Button boutonValider;

    @FXML
    private Button boutonVisitor;

    // =================================================================================================================

    /**
     * Initialisation du contrôleur. Cette méthode est appelée automatiquement après le chargement du FXML.
     */
    @FXML
    private void initialize() {
        // Masque le message d'erreur.
        super.hideError(errorText);
    }


    /**
     * Méthode appelée lors de la tentative de connexion.
     */
    @FXML
    private void handleButtonLogin() {
        System.out.println("Connexion en cours ...");
        try {
            // Vérifie que tous les champs sont remplis.
            verifForm();
            // Vérifie que le mot de passe est correct.
            User user = super.userFacade.connexion(mail.getText(), motDePasse.getText());
            if (user != null) {
                super.hideError(errorText);
                String scope;
                if (user instanceof MusicalExpert) {
                    scope = "musical-expert";
                } else if (user instanceof Admin) {
                    scope = "admin";
                } else {
                    scope = "view";
                }

                Facade.currentUser = user;
                System.out.println("Connexion réussie !");
                goToHome(scope);
            }
        } catch (Exception e) {
            super.displayError(errorText, e.getMessage());
        }
    }

    /**
     * Redirige vers la page d'accueil.
     */
    @FXML
    private void goToHome(String scope) {
        System.out.println("[controller Login] Redirection vers la page d'accueil ...");
        try {
            super.hideError(errorText);
            super.goToHome(boutonValider, scope);
        } catch (ExceptionBadPage e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    @FXML
    private void goToRegister() {
        try {
            super.hideError(errorText);
            super.goToPage(labelRegistration, "users/register-view.fxml", "Inscription");
        } catch (ExceptionBadPage e) {
            super.displayError(errorText, e.getMessage());
        }
    }


    /**
     * Gestionnaire d'événements pour la pression de la touche Entrée.
     * Si la touche Entrée est pressée, déclenche l'action de validation.
     * @param event L'événement de pression de touche.
     */
    @FXML
    private void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleButtonLogin();
        }
    }

    /**
     * Gestionnaire d'événements pour la pression de la touche Echap.
     * @param event L'événement de pression de touche.
     */
    @FXML
    private void handleEscapePressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            try {
                super.hideError(errorText);
                Scene currentScene = boutonValider.getScene();

                Stage currentStage = (Stage) currentScene.getWindow();
                currentStage.close();
                super.goToPage("users/visitor-view.fxml", "Bienvenue sur SoundWander");
            } catch (ExceptionBadPage e) {
                super.displayError(errorText, e.getMessage());
            }
        }
    }


    /**
     * Méthode qui vérifie que tous les champs sont remplis
     * @throws ExceptionFormIncomplete si un champ n'est pas rempli
     */
    private void verifForm() throws ExceptionFormIncomplete {
        if (mail.getText().isEmpty() || motDePasse.getText().isEmpty()) {
            throw new ExceptionFormIncomplete("Veuillez remplir tous les champs");
        }
    }

}
