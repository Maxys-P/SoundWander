package com.sw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;

/**
 * Classe principale de l'application JavaFX.
 * Hérite de javafx.application.Application.
 */
public class App extends Application {

    /**
     * Démarre et initialise la scène principale de l'application.
     *
     * @param stage Le stage (fenêtre) principal sur lequel la scène est définie.
     * @throws IOException Si le chargement du fichier FXML échoue.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Chargement de la police personnalisée
        Font.loadFont(getClass().getResourceAsStream("fonts/Mirza-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("fonts/Mirza-Bold.ttf"), 14);

        // Chargement du fichier FXML.
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/login-view.fxml"));

        // Création d'une nouvelle scène à partir du contenu FXML.
        Scene scene = new Scene(fxmlLoader.load());

        // Ajout d'une feuille de style CSS à la scène pour la personnalisation de l'interface utilisateur.
        scene.getStylesheets().add(getClass().getResource("/com/styles/loginStyle.css").toExternalForm());

        // Configuration des propriétés du stage (fenêtre).
        stage.setTitle("SoundWander"); // Définit le titre de la fenêtre.

        // Ajout de la scène au stage et ajustement de la taille du stage pour correspondre à la scène.
        stage.setScene(scene);
        stage.sizeToScene(); // Ajuste la taille du stage pour correspondre à la scène.
        stage.setMinWidth(420); // Définit la largeur minimale du stage.
        stage.setMinHeight(250);

        // Affichage du stage (fenêtre).
        stage.show();
    }

    /**
     * Point d'entrée principal de l'application JavaFX.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        // Lance l'application.
        launch(args);
    }
}