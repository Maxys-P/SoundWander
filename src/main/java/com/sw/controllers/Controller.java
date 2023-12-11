package com.sw.controllers;

import com.sw.App;
import com.sw.exceptions.ExceptionBadPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Control;

import java.io.IOException;


public abstract class Controller {

    /**
     * Previous page
     */
    private static String previousPageName;

    /**
     * Getter pour la page précédente
     * @return String, la page précédente
     */
    public String getPreviousPageName() {
        return previousPageName;
    }

    /**
     * Setter pour la page précédente
     * @param previousPageName String, la page précédente
     */
    public static void setPreviousPageName(String previousPageName) {
        Controller.previousPageName = previousPageName;
    }

    //Méthodes :

    /**
     * Méthode static pour démarrer l'application JavaFX.
     * @param stage Stage, fenêtre de l'application.
     * @throws IOException si la vue n'a pas pu être chargée.
     */
    public static void startAppFX (Stage stage) throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/login-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Bienvenue sur SoundWander");
        stage.setScene(scene);
        stage.show();
        */

        // Chargement de la police personnalisée
        Font.loadFont(Controller.class.getResourceAsStream("/com/styles/fonts/Mirza-Regular.ttf"), 14);
        Font.loadFont(Controller.class.getResourceAsStream("fonts/Mirza-Bold.ttf"), 14);

        // Chargement du fichier FXML.
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/login-view.fxml"));

        // Création d'une nouvelle scène à partir du contenu FXML.
        Scene scene = new Scene(fxmlLoader.load());

        // Ajout d'une feuille de style CSS à la scène pour la personnalisation de l'interface utilisateur.
        scene.getStylesheets().add(Controller.class.getResource("/com/styles/loginStyle.css").toExternalForm());

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
     * Méthode static pour changer de page.
     * @param controlEl Control, élément de contrôle de la page
     * @param viewName String, nom de la vue dans les ressources
     * @param pageName String, nom de la page
     * @throws ExceptionBadPage si la vue n'existe pas
     */
    public void goToPage(Control controlEl, String viewName, String pageName) throws ExceptionBadPage {
        Stage stage = (Stage) controlEl.getScene().getWindow();
        Scene scene;
        try {
            scene = getScene(viewName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionBadPage("La page " + pageName + " n'existe pas");
        }
        stage.setResizable(false);
        stage.setTitle(pageName);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Méthode pour récupérer une scène à partir d'un nom de vue.
     * @param viewName String, nom de la vue dans les ressources.
     * @return Scene, la scène.
     * @throws IOException
     */
    private Scene getScene(String viewName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + viewName));
        System.out.println("views/" + viewName);
        return new Scene(fxmlLoader.load());
    }

}



