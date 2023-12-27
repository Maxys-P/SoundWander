package com.sw.controllers.profil;

import com.sw.classes.Artist;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.Facade;
import com.sw.facades.FacadeArtist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerProfilArtist extends ControllerProfil {

    final FacadeArtist artistFacade = FacadeArtist.getInstance();

    @FXML private Button becomeArtistButton;
    @FXML private Button subscriptionButton;

    @FXML
    private void handleBecomeArtist(ActionEvent actionEvent) throws Exception {
        try {
            Artist newArtist = artistFacade.userBecomeArtist(Facade.currentUser.getId());
            goToPage(becomeArtistButton, "artists/profil-artist.fxml", "Mon Profil d'Artiste");        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToSubscription() {
        try {
            // Création d'un nouveau Stage (fenêtre)
            Stage stage = new Stage();
            stage.setTitle("Abonnement");

            // Chargement du fichier FXML pour la nouvelle vue
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/views/artists/subscription-view.fxml"));
            Parent root = fxmlLoader.load();

            // Configuration de la scène avec la nouvelle vue
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Affichage de la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur, par exemple afficher un message d'erreur
        }
    }

}
