package com.sw;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import static com.sw.controllers.Controller.startAppFX;


/**
 * Classe principale qui lance l'application JavaFX.
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
        startAppFX(stage);
    }

    /**
     * Point d'entrée principal de l'application JavaFX.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        // Lance l'application.
        launch();
    }
}