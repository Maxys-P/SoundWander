package com.sw.controllers.payments;

import com.sw.controllers.Controller;
import com.sw.exceptions.ExceptionBadPage;
import com.sw.facades.FacadePayment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

public class ControllerPayment extends Controller {

    @FXML public TextField nameField;
    @FXML public TextField cardNumberField;
    @FXML public TextField expirationDateField;
    @FXML public TextField securityCodeField;
    @FXML public Button boutonRetour;
    @FXML public Text errorText;


    FacadePayment facadePayment = FacadePayment.getInstance();


    @FXML
    public void initialize() {
        // Filtre pour le numéro de carte
        super.hideError(errorText);
        UnaryOperator<TextFormatter.Change> cardFilter = change -> {
            String text = change.getControlNewText();
            if (text.matches("[0-9]*") && text.length() <= 16) {
                return change;
            } else if (change.isDeleted() || change.isReplaced()) {
                return change;
            }
            return null;
        };
        cardNumberField.setTextFormatter(new TextFormatter<>(cardFilter));

        // Filtre pour la date d'expiration
        UnaryOperator<TextFormatter.Change> expiryFilter = change -> {
            String text = change.getControlNewText();
            if (text.matches("([0-9]|/)*") && text.length() <= 5) {
                return change;
            } else if (change.isDeleted() || change.isReplaced()) {
                return change;
            }
            return null;
        };
        expirationDateField.setTextFormatter(new TextFormatter<>(expiryFilter));

        // Filtre pour le CVV
        UnaryOperator<TextFormatter.Change> cvvFilter = change -> {
            String text = change.getControlNewText();
            if (text.matches("[0-9]*") && text.length() <= 4) {
                return change;
            } else if (change.isDeleted() || change.isReplaced()) {
                return change;
            }
            return null;
        };
        securityCodeField.setTextFormatter(new TextFormatter<>(cvvFilter));
    }


    /**
     * Méthode appelée lors du clic sur le bouton de confirmation de l'abonnement.
     * @param actionEvent Evénement de clic sur le bouton.
     */
    @FXML
    private void confirmSubscription(ActionEvent actionEvent) throws Exception {
        String name = nameField.getText();
        String cardNumber = cardNumberField.getText();
        String expirationDate = expirationDateField.getText();
        String securityCode = securityCodeField.getText();

        // Validation basique des champs
        if (name.isEmpty() || !cardNumber.matches("\\d{16}") || !expirationDate.matches("\\d{2}/\\d{2}") || !securityCode.matches("\\d{3,4}")) {
            // Affiche une alerte si les champs ne sont pas valides
            super.displayError(errorText, "Veuillez remplir tous les champs correctement");
        } else {
            // Les données semblent valides, procéder à l'abonnement
            try {
                facadePayment.subscribe(FacadePayment.currentUser.getId(), name, cardNumber, expirationDate, securityCode);
                Stage currentStage = (Stage) nameField.getScene().getWindow();
                currentStage.close();
                goToPage("artists/profil-artist.fxml", "Mon Profil d'Artiste");
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception
            }
        }
    }

    @FXML private void goBack(ActionEvent actionEvent) throws ExceptionBadPage {
        goToPage(boutonRetour,"artists/profil-artist.fxml", "Mon Profil d'Artiste");
    }

}
