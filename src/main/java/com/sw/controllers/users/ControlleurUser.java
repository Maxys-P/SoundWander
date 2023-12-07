package com.sw.controllers.users;

import com.sw.facades.FacadeUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.*;
public class ControlleurUser {

    // Déclarations des composants de l'interface utilisateur liés au FXML.
    @FXML
    private VBox conteneurConnexion; // Conteneur pour les champs de connexion et le bouton.

    @FXML
    private TextField textFieldIdentifiant; // Champ de saisie pour l'identifiant.

    @FXML
    private PasswordField passwordField; // Champ de saisie pour le mot de passe.

    @FXML
    private Label labelErreur; // Label pour afficher les messages d'erreur.

    @FXML
    private Label labelBienvenue; // Label pour afficher le message de bienvenue après la connexion réussie.

    private final FacadeUser userFacade;

    public ControlleurUser() {
        userFacade = new FacadeUser();
    }

    /**
     * Initialisation du contrôleur. Cette méthode est appelée automatiquement après le chargement du FXML.
     */
    @FXML
    private void initialize() {
        // Associe un gestionnaire d'événements aux champs de texte pour réagir à la pression de la touche Entrée.
        textFieldIdentifiant.setOnKeyPressed(event -> handleEnterPressed(event));
        passwordField.setOnKeyPressed(event -> handleEnterPressed(event));

    }

    /**
     * Gestionnaire d'événements pour la pression de la touche Entrée.
     * Si la touche Entrée est pressée, déclenche l'action de validation.
     *
     * @param event L'événement de pression de touche.
     */
    private void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleButtonAction();
        }
    }

    /**
     * Gestionnaire d'événements pour le clic sur le bouton de validation.
     * Déclenche la validation des identifiants.
     */
    @FXML
    private void handleButtonAction() {
        validateLogin();
    }

    /**
     * Valide les informations de connexion.
     * Si les identifiants sont corrects, affiche le message de bienvenue.
     * Sinon, affiche un message d'erreur.
     */

    private void validateLogin() {
        String identifiant = textFieldIdentifiant.getText();
        String motDePasse = passwordField.getText();

        boolean isValid = userFacade.validateCredentials(identifiant, motDePasse);

        if (isValid) {
            conteneurConnexion.setVisible(false);
            labelErreur.setVisible(false);
            labelBienvenue.setVisible(true);
        } else {
            labelErreur.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> labelErreur.setVisible(false));
            pause.play();
            textFieldIdentifiant.clear();
            passwordField.clear();
        }
    }


}