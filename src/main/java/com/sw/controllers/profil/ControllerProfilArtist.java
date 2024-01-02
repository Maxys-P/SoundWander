package com.sw.controllers.profil;

import com.sw.classes.Artist;
import com.sw.classes.Payment;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.Facade;
import com.sw.facades.FacadeArtist;
import com.sw.facades.FacadePayment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;


public class ControllerProfilArtist extends ControllerProfil {

    final FacadeArtist artistFacade = FacadeArtist.getInstance();
    final FacadePayment paymentFacade = FacadePayment.getInstance();


    @FXML private Button subscriptionButton;
    @FXML private Button unsubscribeButton;


    @FXML
    private void initialize() throws Exception {
        updateSubscriptionButtons();
    }

    /**
     * Méthode qui gère quel bouton est affiché en fonction de l'abonnement de l'artiste ou pas.
     * @throws Exception si il y a un problème lors de la récupération.
     */
    private void updateSubscriptionButtons() throws Exception {
        int userId = Facade.currentUser.getId();
        boolean isSubscribed = artistFacade.isArtistSubscribed(userId);
        subscriptionButton.setVisible(!isSubscribed);
        unsubscribeButton.setVisible(isSubscribed);
    }


    /**
     * Méthode appelée lors du clic sur le bouton d'abonnement.
     * @throws ExceptionBadPage si il y a un problème lors de l'accès à la page.
     */
    @FXML
    private void goToSubscription() throws ExceptionBadPage {
        try {
            if (paymentFacade.isInDelayPeriod(Facade.currentUser.getId())) {
                int daysRemaining = paymentFacade.getDaysRemainingInDelayPeriod(Facade.currentUser.getId());
                showAlert("Vous avez résilié votre abonnement récemment. Vous ne pouvez pas vous réabonner pour le moment mais vous pouvez toujours proposer vos musiques aux playlists. Il vous reste " + daysRemaining + " jours avant de pouvoir vous réabonner.");
            } else {
                goToPage(subscriptionButton, "artists/subscription-view.fxml", "Abonnement");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionBadPage("Impossible d'accéder à la page d'abonnement");

        }
    }


    private Payment getPaymentByUserId(int userId) {
        try {
            return FacadePayment.getInstance().getPaymentByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void showAlert(String message) {
        // Utilisez cette méthode pour afficher un message d'alerte à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Méthode appelée lors du clic sur le bouton de désabonnement.
     * @throws ExceptionBadPage si il y a un problème lors de l'accès à la page.
     */
    @FXML
    private void goToUnsubscribe() throws ExceptionBadPage {
        goToPage(unsubscribeButton, "artists/unsubscribe-view.fxml", "Désabonnement");
    }
}
