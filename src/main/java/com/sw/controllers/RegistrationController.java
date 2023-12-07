package com.sw.controllers;

import com.sw.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.layout.VBox;



public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private VBox conteneurInscription; // Assurez-vous que ceci correspond à l'fx:id dans le fichier FXML


    /**
     * Cette méthode est appelée pour revenir à la vue de connexion.
     */
    @FXML
    private void handleBackToLogin() {
        try {
            // Chargez le fichier FXML pour la vue d'inscription
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/login-view.fxml"));

            // Création d'une nouvelle scène à partir du contenu FXML.
            Scene scene = new Scene(fxmlLoader.load());

            // Obtenez le stage actuel (fenêtre) à partir d'un des composants de la scène
            Stage stage = (Stage) conteneurInscription.getScene().getWindow();

            // Ajout d'une feuille de style CSS à la scène pour la personnalisation de l'interface utilisateur.
            scene.getStylesheets().add(getClass().getResource("/com/styles/loginStyle.css").toExternalForm());

            // Définissez la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez l'exception ici (par exemple, affichez une boîte de dialogue d'erreur)
        }
    }

    @FXML
    private void handleInscriptionAction() {
        // Ici, vous devriez normalement effectuer une validation de l'inscription,
        // par exemple vérifier si les mots de passe correspondent et traiter l'enregistrement de l'utilisateur.
        // Pour cet exemple, nous allons simplement changer de vue.

        try {
            // Chargez le fichier FXML pour la vue d'inscription
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/home-view.fxml"));

            // Création d'une nouvelle scène à partir du contenu FXML.
            Scene scene = new Scene(fxmlLoader.load());

            // Obtenez le stage actuel (fenêtre) à partir d'un des composants de la scène
            Stage stage = (Stage) conteneurInscription.getScene().getWindow();

            // Définissez la nouvelle scène sur le stage
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez l'exception ici (par exemple, affichez une boîte de dialogue d'erreur)
        }
    }}
