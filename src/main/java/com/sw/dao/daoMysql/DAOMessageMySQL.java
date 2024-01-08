package com.sw.dao.daoMysql;

import com.sw.classes.Conversation;
import com.sw.classes.Message;
import com.sw.classes.User;
import com.sw.dao.DAOMessage;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;
import com.sw.facades.FacadeConversation;
import com.sw.facades.FacadeMusic;
import com.sw.facades.FacadeUser;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Classe DAO concrète pour les messages
 */
public class DAOMessageMySQL extends DAOMessage {

    private FacadeUser userFacade = FacadeUser.getInstance();
    private FacadeConversation conversationFacade = FacadeConversation.getInstance();


    public DAOMessageMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    @Override
    public Message createMessage(String content, User sender, User receiver, LocalDateTime creationDate) throws SQLException {

        // Recherche d'une conversation existante entre sender et receiver
        DAOConversationMySQL daoConversation = new DAOConversationMySQL();
        UUID foundConversationId = daoConversation.findConversationByUsers(sender, receiver);
        String conversationId;
        Conversation conversation;

        if (foundConversationId == null) {
            // Créer une nouvelle conversation si elle n'existe pas
            conversation = daoConversation.createConversation(sender, receiver);
            conversationId = conversation.getId().toString();
        } else {
            conversationId = foundConversationId.toString();
        }

        Message newMessage = new Message(content, sender, receiver, conversationId, creationDate);

        // Préparation des données du message
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("id", newMessage.getId().toString());
        messageData.put("content", content);
        messageData.put("senderId", sender.getId());
        messageData.put("receiverId", receiver.getId());
        messageData.put("conversationId", conversationId);
        messageData.put("creationDate", Timestamp.valueOf(creationDate));


        try {
            // Insérer le message dans la base de données
            ((RequetesMySQL) requetesDB).createNoReturn("message", messageData);
            return newMessage;
        } catch (Exception e) {
            throw new SQLException("Erreur lors de la création du message", e);
        }
    }





    @Override
    public void deleteMessage(UUID messageId) throws SQLException {

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", messageId.toString()); // Convertissez l'UUID en String
        try {
            ((RequetesMySQL) requetesDB).delete("message", conditions);
        } catch (Exception e) {
            throw new SQLException("Erreur lors de la suppression du message", e);
        }
    }


    @Override
    public Message getMessage(UUID messageId) throws SQLException {

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", messageId);

        try {
            MapperResultSet messageData = ((RequetesMySQL) requetesDB).selectWhere("message", conditions);
            if (!messageData.getListData().isEmpty()) {
                Map<String, Object> messageDataMap = messageData.getListData().getFirst();
                User sender;
                User receiver;
                Conversation conversation;
                return new Message(
                        (String) messageDataMap.get("content"),
                        sender = userFacade.getUserById((int) messageDataMap.get("senderId")),
                        receiver = userFacade.getUserById((int) messageDataMap.get("receiverId")),
                        (conversation = conversationFacade.getConversation(UUID.fromString((String) messageDataMap.get("conversationId")))).toString(),
                        ((Timestamp) messageDataMap.get("creationDate")).toLocalDateTime()
                );
            } else {
                throw new SQLException("Message not found with ID: " + messageId);
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération du message", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Message> getMessagesByConversation(String conversationId) throws SQLException {

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("conversationId", conversationId);

        try {
            MapperResultSet messagesData = ((RequetesMySQL) requetesDB).selectWhere("message", conditions);
            List<Message> messages = new ArrayList<>();
            for (Map<String, Object> row : messagesData.getListData()) {
                User sender = userFacade.getUserById((int) row.get("senderId"));
                User receiver = userFacade.getUserById((int) row.get("receiverId"));

                // Récupérer directement la valeur de LocalDateTime
                LocalDateTime creationDate = (LocalDateTime) row.get("creationDate");


                messages.add(new Message(
                        (String) row.get("content"),
                        sender,
                        receiver,
                        conversationId,
                        creationDate
                ));
            }
            return messages;
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération des messages de la conversation", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






}
