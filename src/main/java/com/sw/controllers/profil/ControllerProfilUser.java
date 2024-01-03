package com.sw.controllers.profil;

import com.sw.classes.User;
import com.sw.exceptions.ExceptionFormIncomplete;
import com.sw.exceptions.ExceptionUsedEmail;
import com.sw.facades.Facade;
import com.sw.facades.FacadeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;

public class ControllerProfilUser extends ControllerProfil {

    @FXML private TextField pseudo;
    @FXML private TextField mail;
    @FXML private DatePicker dateNaissance;
    @FXML private ImageView imageProfil;
    @FXML private Button boutonModifier;
    @FXML private Button boutonSave;
    @FXML private Text errorText;
    @FXML private Button goToPlaylistButton;


    private User currentUser;
    private String tempImagePath;
    private int taillepdp = 150;
    @FXML
    private void initialize() {
        currentUser = Facade.currentUser;
        setupUserDetails();
        setupImageView();
        boutonModifier.requestFocus();
        dateNaissance.setDisable(true);
    }

    private void setupUserDetails() {
        if (currentUser != null) {
            pseudo.setText(currentUser.getPseudo());
            mail.setText(currentUser.getMail());
            dateNaissance.setValue(currentUser.getDateNaissance());
        }
    }

    @FXML
    private void handleEditAction(ActionEvent actionEvent) {
        setFieldsEditable(true);
        imageProfil.setDisable(false);
        boutonModifier.setVisible(false);
        boutonSave.setVisible(true);
    }

    @FXML
    private void handleButtonSave(ActionEvent event) throws Exception {
        if (validateInput()) {
            updateUserData();
            setFieldsEditable(false);
            imageProfil.setDisable(true);
            boutonModifier.setVisible(true);
            boutonSave.setVisible(false);
        }
    }

    private boolean validateInput() {
        try {
            if (pseudo.getText().isEmpty() || mail.getText().isEmpty() || dateNaissance.getValue() == null) {
                throw new ExceptionFormIncomplete("Veuillez remplir tous les champs");
            }
            if (!mail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new ExceptionFormIncomplete("Adresse mail non valide");
            }
            if (!mail.getText().equals(currentUser.getMail())) {
                User userWithMail = FacadeUser.getInstance().getUserByMail(mail.getText());
                if (userWithMail != null && userWithMail.getId() != currentUser.getId()) {
                    throw new ExceptionUsedEmail("L'adresse mail est déjà utilisée");
                }
            }
            return true;
        } catch (Exception e) {
            errorText.setText(e.getMessage());
            return false;
        }
    }

    private void updateUserData() throws Exception {
        FacadeUser facade = FacadeUser.getInstance();
        facade.updateUserPseudo(currentUser.getId(), pseudo.getText());
        facade.updateUserMail(currentUser.getId(), mail.getText());
        facade.updateUserDateNaissance(currentUser.getId(), dateNaissance.getValue());

        if (tempImagePath != null && !tempImagePath.isEmpty()) {
            facade.updateUserPhoto(currentUser.getId(), tempImagePath);
            currentUser.setPhoto(tempImagePath);
        }

        currentUser.setPseudo(pseudo.getText());
        currentUser.setMail(mail.getText());
        currentUser.setDateNaissance(dateNaissance.getValue());
    }

    private void setFieldsEditable(boolean editable) {
        pseudo.setEditable(editable);
        mail.setEditable(editable);
        pseudo.setDisable(!editable);
        mail.setDisable(!editable); // Disable the field if not editable
        dateNaissance.setEditable(editable);
        dateNaissance.setDisable(!editable);
    }



    private void setupImageView() {
        // Determine the correct image path
        String imagePath = (currentUser != null && currentUser.getPhoto() != null && !currentUser.getPhoto().isEmpty())
                ? currentUser.getPhoto()
                : getClass().getResource("/images/photodeprofil.png").toExternalForm();

        // Create the image and set it on the ImageView
        Image profileImage = new Image(imagePath, true);
        profileImage.progressProperty().addListener((obs, oldProgress, newProgress) -> {
            if (newProgress.doubleValue() == 1.0) {
                centerImageInCircle(imageProfil, profileImage);
                applyCircleClip(imageProfil);
            }
        });

        // Set the image on the ImageView
        imageProfil.setImage(profileImage);
        imageProfil.setDisable(true);
    }


    private void centerImageInCircle(ImageView imageView, Image image) {
        double smallestSide = Math.min(image.getWidth(), image.getHeight());
        double x = (image.getWidth() - smallestSide) / 2;
        double y = (image.getHeight() - smallestSide) / 2;
        Rectangle2D viewportRect = new Rectangle2D(x, y, smallestSide, smallestSide);
        imageView.setViewport(viewportRect);
        imageView.setFitWidth(taillepdp);
        imageView.setFitHeight(taillepdp);
    }

    private void applyCircleClip(ImageView imageView) {
        Circle circleClip = new Circle(taillepdp / 2, taillepdp / 2, taillepdp / 2);
        imageView.setClip(circleClip);
    }

    @FXML
    private void handleChangePhoto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(imageProfil.getScene().getWindow());
        if (file != null) {
            tempImagePath = file.toURI().toString();
            Image newImage = new Image(tempImagePath, true);
            newImage.progressProperty().addListener((obs, oldProgress, newProgress) -> {
                if (newProgress.doubleValue() == 1.0) {
                    imageProfil.setImage(newImage);
                    centerImageInCircle(imageProfil, newImage);
                    applyCircleClip(imageProfil);
                }
            });
        }
    }

    @FXML
    private void handleGoToPlaylist(ActionEvent event) {
        try {
            super.goToPage(goToPlaylistButton, "users/privatePlaylistView.fxml", "playlist privée");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}
