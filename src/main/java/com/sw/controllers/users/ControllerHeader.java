package com.sw.controllers.users;

import com.sw.classes.Admin;
import com.sw.classes.Artist;
import com.sw.classes.MusicalExpert;
import com.sw.classes.User;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.Facade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControllerHeader extends ControllerUser {
    @FXML
    private Button searchButton;
    @FXML
    private Button profilButton;
    @FXML
    private Button privatePlaylistButton;
    @FXML
    private Button homeButton;
    @FXML
    private Text errorText;


    @FXML
    private void goToPrivatePlaylist() {
        try {
            super.goToPage(privatePlaylistButton, "users/privatePlaylistView.fxml", "playlist privée");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    private void goSearching() {
        try {
            // Charger le fichier FXML pour la recherche
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/components/search/search.fxml"));
            Parent searchRoot = fxmlLoader.load();

            // Créer une nouvelle scène avec le composant de recherche chargé
            Scene searchScene = new Scene(searchRoot);

            // Créer une nouvelle fenêtre modale (Stage)
            Stage searchStage = new Stage();
            searchStage.initModality(Modality.APPLICATION_MODAL); // Définir la modalité
            searchStage.initOwner(searchButton.getScene().getWindow()); // Définir la fenêtre propriétaire
            searchStage.setScene(searchScene);

            // Afficher la fenêtre modale et attendre jusqu'à ce qu'elle soit fermée
            searchStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception ici
        }
    }

    @FXML
    private void goToProfil() {
        User user = Facade.currentUser;
        if (user != null) {
            String scope;
            if (user instanceof Artist) {
                scope = "artist";
            } else if (user instanceof MusicalExpert) {
                scope = "musical-expert";
            } else if (user instanceof Admin) {
                scope = "admin";
            } else {
                scope = "user";
            }

            try {
                String pathUser = scope + "s/";
                super.goToPage(profilButton, pathUser + "profil-" + scope + ".fxml", "Mon Profil");
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }

    @FXML
    private void goToHome() {
        User user = Facade.currentUser;
        if (user != null) {
            String scope;
            if (user instanceof MusicalExpert) {
                scope = "musical-expert";
            } else if (user instanceof Admin) {
                scope = "admin";
            } else {
                scope = "view";
            }

            try {
                String pathUser = scope + "s/";
                super.goToHome(homeButton, scope);
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
}
