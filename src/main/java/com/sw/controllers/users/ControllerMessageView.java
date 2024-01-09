package com.sw.controllers.users;

import com.sw.classes.Conversation;
import com.sw.classes.Message;
import com.sw.classes.User;
import com.sw.facades.FacadeConversation;
import com.sw.facades.FacadeMessage;
import com.sw.facades.FacadeUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ControllerMessageView {

    @FXML
    private TextField receiverField;
    @FXML
    private TextField contentField;
    @FXML
    private TextField messageIdField;
    @FXML
    private TextField conversationIdField;
    @FXML
    private ListView<String> conversationListView; // Nouveau ListView pour les conversations



    private FacadeMessage facadeMessage = FacadeMessage.getInstance();

    private FacadeUser userFacade = FacadeUser.getInstance();
    private User currentUser = userFacade.getCurrentUser(); // Vous devez initialiser cet utilisateur (par exemple, l'utilisateur connecté)

    private FacadeConversation facadeConversation = FacadeConversation.getInstance();

    private Map<String, UUID> conversationMap = new HashMap<>();



    @FXML
    public void initialize() {
        currentUser = userFacade.getCurrentUser(); // Initialiser l'utilisateur courant
        loadConversations(); // Charger les conversations au démarrage
        setupConversationListView();

    }


    @FXML
    private void handleSendMessage() {
        try {
            String receiverPseudo = receiverField.getText(); // L'ID du destinataire doit être au format UUID
            String content = contentField.getText();

            User receiver = userFacade.getUserByPseudo(receiverPseudo); // Vous devez adapter cette partie pour récupérer l'objet User réel
            facadeMessage.createMessage(content, currentUser, receiver);

            // Vous pouvez ensuite nettoyer les champs ou afficher un message de confirmation
            receiverField.clear();
            contentField.clear();


        } catch (Exception e) {
            // Gestion des erreurs: afficher une alerte ou un message à l'utilisateur
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteMessage() {
        try {
            UUID messageId = UUID.fromString(messageIdField.getText());
            facadeMessage.deleteMessage(messageId);
            // Afficher une confirmation ou mettre à jour l'interface utilisateur
        } catch (Exception e) {
            e.printStackTrace(); // Gestion des erreurs
        }
    }

    @FXML
    private void handleShowMessages() {
        try {
            UUID conversationId = UUID.fromString(conversationIdField.getText());
            List<Message> messages = facadeMessage.getMessagesByConversation(conversationId);
            // Afficher les messages de la conversation
        } catch (Exception e) {
            e.printStackTrace(); // Gestion des erreurs
        }
    }

    private void loadConversations() {
        try {
            List<Conversation> conversations = facadeConversation.getConversationsByUser(currentUser);
            conversationListView.getItems().clear(); // Assurez-vous de vider la liste avant de l'actualiser
            for (Conversation conversation : conversations) {
                User otherUser = facadeConversation.getOtherParticipant(conversation, currentUser);
                String displayText = otherUser.getPseudo();
                conversationListView.getItems().add(displayText); // Ajoutez le texte combiné dans le ListView
                conversationMap.put(displayText, conversation.getId()); // Utilisez le texte complet comme clé dans la Map
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs
        }
    }

    private void setupConversationListView() {
        conversationListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !conversationListView.getSelectionModel().isEmpty()) {
                String selectedConversation = conversationListView.getSelectionModel().getSelectedItem();
                openConversationPage(selectedConversation);
            }
        });
    }

    private void openConversationPage(String conversationDisplayText) {
        try {
            UUID conversationId = conversationMap.get(conversationDisplayText);
            if (conversationId != null) {
                Conversation conversation = facadeConversation.getConversation(conversationId);
                User otherUser = facadeConversation.getOtherParticipant(conversation, currentUser);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/users/conversationView.fxml"));
                Parent root = loader.load();

                ControllerConversationView controller = loader.getController();
                controller.setOtherUser(otherUser); // Définir l'utilisateur
                controller.setOtherUserName(conversationDisplayText); // Définir le nom d'utilisateur
                controller.setConversationId(conversationId.toString()); // Définir l'ID de la conversation

                Stage stage = new Stage();
                stage.setTitle("Conversation");
                stage.setScene(new Scene(root));
                stage.show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






    // TODO: Ajoutez les méthodes pour initialiser le currentUser, afficher les messages, etc.
}