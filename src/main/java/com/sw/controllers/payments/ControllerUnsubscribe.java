package com.sw.controllers.payments;

import com.sw.classes.Payment;
import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.Facade;
import com.sw.facades.FacadePayment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ControllerUnsubscribe extends Controller {

    @FXML public Text subscriptionText;
    @FXML public Text delaiText;
    @FXML public Button boutonRetour;

    FacadePayment facadePayment = FacadePayment.getInstance();

    @FXML
    public void initialize() throws Exception {
        Date subscriptionDate = facadePayment.getPaymentByUserId(Facade.currentUser.getId()).getSubscriptionDate();

        // Formatage de la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(subscriptionDate);
        subscriptionText.setText("Vous avez démarré votre abonnement le : " + formattedDate);

        int daysRemaining = facadePayment.getDaysRemainingInDelayPeriod(Facade.currentUser.getId());
        if (daysRemaining > 0) {
            delaiText.setText("En vous désabonnant aujourd'hui, vous pourrez donc proposer vos musiques encore " + daysRemaining + " jours.");
        } else {
            delaiText.setText("Vous n'êtes actuellement pas dans votre période de grâce. En vous désabonnant, votre abonnement prendra fin immédiatement.");
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton d'annulation de l'abonnement.
     * @param actionEvent Evénement de clic sur le bouton.
     */
    @FXML
    private void cancelSubscription(ActionEvent actionEvent) {
        try {
            // Obtenez le paiement le plus récent pour l'utilisateur
            Payment currentPayment = facadePayment.getPaymentByUserId(Facade.currentUser.getId());

            if (currentPayment != null) {
                facadePayment.cancelSubscription(currentPayment.getId());
                Stage currentStage = (Stage) delaiText.getScene().getWindow(); // ou un autre élément FXML de la fenêtre actuelle
                currentStage.close();
                goToPage( "artists/profil-artist.fxml", "Mon Profil d'Artiste");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }

    @FXML private void goBack(ActionEvent actionEvent) throws ExceptionBadPage {
        goToPage( boutonRetour, "artists/profil-artist.fxml", "Mon Profil d'Artiste");
    }
}
