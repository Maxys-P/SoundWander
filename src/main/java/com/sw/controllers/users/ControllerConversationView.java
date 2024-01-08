package com.sw.controllers.users;

import com.sw.classes.Message;
import com.sw.classes.User;
import com.sw.dao.daoMysql.DAOMessageMySQL;
import com.sw.facades.FacadeMessage;
import com.sw.facades.FacadeUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ControllerConversationView {

    @FXML
    private VBox messageBox; // Ajouté pour contenir les messages
    @FXML
    private Label otherUserLabel; // Ajouté pour afficher les messages
    @FXML
    private TextField contentField; // Ajouté pour afficher les messages
    @FXML
    private ScrollPane scrollPane;

    private String otherUserName;
    private String conversationId; // L'ID de la conversation (à définir)

    private FacadeUser userFacade = FacadeUser.getInstance();
    private User otherUser;
    private Timeline refreshMessagesTimeline;

    public void initialize() {
        setupAutoRefresh();
    }

    private void setupAutoRefresh() {
        refreshMessagesTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> loadMessages()));
        refreshMessagesTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshMessagesTimeline.play();
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
        loadMessages(); // Chargez les messages après avoir défini le nom d'utilisateur
        scrollToBottom();
    }

    private void loadMessages() {
        DAOMessageMySQL daoMessage = new DAOMessageMySQL();
        User currentUser = userFacade.getCurrentUser(); // Obtenir l'utilisateur courant

        try {
            List<Message> messages = daoMessage.getMessagesByConversation(conversationId);
            messages.sort((m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));

            // Effacer les messages actuels avant de charger les nouveaux
            messageBox.getChildren().clear();

            for (Message message : messages) {
                VBox singleMessageBox = new VBox(5);

                // Appliquer le style en fonction de l'expéditeur du message
                if (message.getSender().getId() == currentUser.getId()) {
                    VBox.setMargin(singleMessageBox, new Insets(0, 10, 0, 80));
                    singleMessageBox.getStyleClass().add("message-box"); // Style pour les messages envoyés
                } else {
                    VBox.setMargin(singleMessageBox, new Insets(0, 80, 0, 10));
                    singleMessageBox.getStyleClass().add("message-box-received"); // Style pour les messages reçus
                }

                Label messageLabel = new Label(message.getContent());
                messageLabel.getStyleClass().add("h2"); // Ajouter la classe de style h2

                //Label messageDate = new Label(message.getCreationDate().toString());
                Label messageSender = new Label(message.getSender().getPseudo());
                messageSender.getStyleClass().add("h3"); // Ajouter la classe de style h3

                singleMessageBox.getChildren().addAll(messageSender, messageLabel);
                messageBox.getChildren().add(singleMessageBox);
            }

            String cssPath = "/com/styles/Style.css"; // Assurez-vous que le chemin est correct
            messageBox.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSendMessage() {
    }


    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
        otherUserLabel.setText("Conversation avec " + otherUser.getPseudo());
    }

    @FXML
    private void handleSendNewMessage() {
        try {
            int receiverId = otherUser.getId(); // L'ID du destinataire doit être au format UUID
            String content = contentField.getText();
            User receiver = userFacade.getUserById(receiverId); // Vous devez adapter cette partie pour récupérer l'objet User réel

            FacadeMessage facadeMessage = FacadeMessage.getInstance();
            facadeMessage.createMessage(content, userFacade.getCurrentUser(), receiver);

            // Vous pouvez ensuite nettoyer les champs ou afficher un message de confirmation
            contentField.clear();
            loadMessages();
            scrollToBottom();



            // TODO: Ajoutez ici la logique pour mettre à jour l'affichage des messages

        } catch (Exception e) {
            // Gestion des erreurs: afficher une alerte ou un message à l'utilisateur
            e.printStackTrace();
        }
    }

    public void stopAutoRefresh() {
        if (refreshMessagesTimeline != null) {
            refreshMessagesTimeline.stop();
        }
    }

    private void scrollToBottom() {
        if (scrollPane != null) {
            // Défilement vers le bas
            scrollPane.setVvalue(1.0);
        }
    }


}
