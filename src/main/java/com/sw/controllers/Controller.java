package com.sw.controllers;

import java.util.*;

/**
 *
 */
/*
public abstract class Controller {

    public Controller() {
    }


    public void onAction() {
        // TODO implement here
    }

}
*/

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Contrôleur pour la gestion de la vue de connexion.
 */
public class Controller {

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

        // Validation de l'identifiant et du mot de passe. (À remplacer par une vérification sécurisée dans une application réelle)
        if ("admin".equals(identifiant) && "admin".equals(motDePasse)) {
            // Connexion réussie
            conteneurConnexion.setVisible(false);
            labelErreur.setVisible(false); // Cache le message d'erreur
            labelBienvenue.setVisible(true); // Affiche le message de bienvenue
        } else {
            // Connexion échouée
            labelErreur.setVisible(true); // Affiche le message d'erreur
            // Créer un délai de 2 secondes avant de cacher le label
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> labelErreur.setVisible(false));
            pause.play();
            // Réinitialiser les champs de texte
            textFieldIdentifiant.clear();
            passwordField.clear();

        }
    }
}
