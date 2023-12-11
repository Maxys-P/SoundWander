package com.sw.controllers.users;

import com.sw.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Contrôleur pour la gestion de la vue de connexion.
 */
public class ControllerLogin extends ControllerUser {

    // Déclarations des composants de l'interface utilisateur liés au FXML.
    @FXML
    private VBox conteneurConnexion; // Conteneur pour les champs de connexion et le bouton.

    @FXML
    private TextField textFieldPseudo; // Champ de saisie pour l'identifiant.

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
        textFieldPseudo.setOnKeyPressed(event -> handleEnterPressed(event));
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
        String identifiant = textFieldPseudo.getText();
        String motDePasse = passwordField.getText();

        if ("admin".equals(identifiant) && "admin".equals(motDePasse)) {
            // Connexion réussie, ouvrir la vue home-view
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/home-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) conteneurConnexion.getScene().getWindow();
                stage.setScene(scene);
                stage.sizeToScene();
            } catch (IOException e) {
                e.printStackTrace();
                // Gérez l'exception ici (par exemple, affichez une boîte de dialogue d'erreur)
            }
        } else {
            // Connexion échouée, afficher le message d'erreur
            labelErreur.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> labelErreur.setVisible(false));
            pause.play();
            textFieldPseudo.clear();
            passwordField.clear();
        }
    }


    @FXML
    private void handleRegistrationLink() {
        try {
            // Chargez le fichier FXML pour la vue d'inscription
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/register-view.fxml"));

            // Création d'une nouvelle scène à partir du contenu FXML.
            Scene scene = new Scene(fxmlLoader.load());

            // Ajout d'une feuille de style CSS à la scène pour la personnalisation de l'interface utilisateur.
            scene.getStylesheets().add(getClass().getResource("/com/styles/registerStyle.css").toExternalForm());

            // Obtenez le stage actuel (fenêtre) à partir d'un des composants de la scène
            Stage stage = (Stage) conteneurConnexion.getScene().getWindow();

            // Définissez la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez l'exception ici (par exemple, affichez une boîte de dialogue d'erreur)
        }
    }

}
