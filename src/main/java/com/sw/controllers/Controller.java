package com.sw.controllers;

import com.sw.App;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadeProposal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Control;

import java.io.IOException;


public abstract class Controller {


    //Méthodes :

    /**
     * Méthode static pour démarrer l'application JavaFX.
     * @param stage Stage, fenêtre de l'application.
     * @throws IOException si la vue n'a pas pu être chargée.
     */
    public static void startAppFX (Stage stage) throws IOException {

        // Chargement du fichier FXML.
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/users/login-view.fxml"));

        // Création d'une nouvelle scène à partir du contenu FXML.
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setTitle("SoundWander"); // Définit le titre de la fenêtre.

        // Ajout de la scène au stage et ajustement de la taille du stage pour correspondre à la scène.
        stage.setScene(scene);
        stage.sizeToScene(); // Ajuste la taille du stage pour correspondre à la scène.

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
        stage.setResizable(true);
        stage.setTitle(pageName);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Méthode static pour changer de page sans élément de controle
     * @param viewName String, nom de la vue dans les ressources
     * @param pageName String, nom de la page
     * @throws ExceptionBadPage si la vue n'existe pas
     */
    protected void goToPage(String viewName, String pageName) throws ExceptionBadPage {
        Stage stage = new Stage();
        Scene scene;
        try {
            scene = getScene(viewName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionBadPage("La page " + pageName + " n'existe pas");
        }
        stage.setResizable(true);
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/views/" + viewName));
        System.out.println("views/" + viewName);
        return new Scene(fxmlLoader.load());
    }

    /**
     * Affiche un message d'erreur
     * @param textEl Text, élément texte
     * @param message String, message d'erreur
     */
    protected void displayError(Text textEl, String message) {
        textEl.setText(message);
    }

    /**
     * Supprime le message d'erreur
     * @param textEl Text, élément texte
     */
    protected void hideError(Text textEl) {
        textEl.setText("");
    }


}



