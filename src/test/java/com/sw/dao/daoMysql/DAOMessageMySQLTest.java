package com.sw.dao.daoMysql;

import com.sw.classes.Message;
import com.sw.classes.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DAOMessageMySQLTest {

    private DAOMessageMySQL daoMessageMySQL;
    private User sender;
    private User receiver;
    private UUID messageIdTest;
    private UUID conversationIdTest;

    @BeforeEach
    void setUp() {
        daoMessageMySQL = new DAOMessageMySQL();

        sender = new User(1, "Sender", "sender@example.com", "password", LocalDate.now(), "senderPhoto", "user");
        receiver = new User(2, "Receiver", "receiver@example.com", "password", LocalDate.now(), "receiverPhoto", "user");

        //mettre des valeurs réelles de la base de données
        messageIdTest = UUID.randomUUID();
        conversationIdTest = UUID.randomUUID();
    }

    @Test
    void createMessage() {
        String content = "Test message";
        LocalDateTime creationDate = LocalDateTime.now();

        try {
            Message message = daoMessageMySQL.createMessage(content, sender, receiver, creationDate);

            assertNotNull(message);
            assertEquals(content, message.getContent());
            assertEquals(sender.getId(), message.getSender().getId());
            assertEquals(receiver.getId(), message.getReceiver().getId());

        } catch (Exception e) {
            fail("Création du message a échoué : " + e.getMessage());
        }
    }

    @Test
    void getMessage() {
        UUID messageId = messageIdTest;

        try {
            Message message = daoMessageMySQL.getMessage(messageId);

            assertNotNull(message);
            assertEquals(messageId, message.getId());
        } catch (Exception e) {
            fail("Récupération du message a échoué : " + e.getMessage());
        }
    }

    @Test
    void getMessagesByConversation() {
        UUID conversationId = conversationIdTest;

        try {
            List<Message> messages = daoMessageMySQL.getMessagesByConversation(conversationId.toString());

            assertNotNull(messages);
            assertFalse(messages.isEmpty());
        } catch (Exception e) {
            fail("Récupération des messages de la conversation a échoué : " + e.getMessage());
        }
    }

    @Test
    void deleteMessage() {
        UUID messageId = messageIdTest;

        try {
            daoMessageMySQL.deleteMessage(messageId);
            Message deletedMessage = daoMessageMySQL.getMessage(messageId);

            assertNull(deletedMessage);
        } catch (Exception e) {
            fail("Suppression du message a échoué : " + e.getMessage());
        }
    }
}
