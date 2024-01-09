package com.sw.dao.daoMysql;

import com.sw.classes.Conversation;
import com.sw.classes.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DAOConversationMySQLTest {

    private DAOConversationMySQL daoConversationMySQL;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        daoConversationMySQL = new DAOConversationMySQL();

        user1 = new User(1, "User1", "user1@example.com", "password1", LocalDate.now(), "user1Photo", "user");
        user2 = new User(2, "User2", "user2@example.com", "password2", LocalDate.now(), "user2Photo", "user");
    }

    @Test
    void createConversation() {
        try {
            Conversation conversation = daoConversationMySQL.createConversation(user1, user2);

            assertNotNull(conversation);
            assertEquals(user1.getId(), conversation.getUser1().getId());
            assertEquals(user2.getId(), conversation.getUser2().getId());
        } catch (SQLException e) {
            fail("Création de la conversation a échoué : " + e.getMessage());
        }
    }

    @Test
    void getConversation() {
        try {
            Conversation createdConversation = daoConversationMySQL.createConversation(user1, user2);
            UUID conversationId = createdConversation.getId();

            Conversation fetchedConversation = daoConversationMySQL.getConversation(conversationId);

            assertNotNull(fetchedConversation);
            assertEquals(conversationId, fetchedConversation.getId());
        } catch (SQLException e) {
            fail("Récupération de la conversation a échoué : " + e.getMessage());
        }
    }

    @Test
    void getConversationsByUser() {
        try {
            // Supposons que la base de données a déjà des conversations pour user1
            List<Conversation> conversations = daoConversationMySQL.getConversationsByUser(user1);

            assertNotNull(conversations);
            assertFalse(conversations.isEmpty());
        } catch (SQLException e) {
            fail("Récupération des conversations de l'utilisateur a échoué : " + e.getMessage());
        }
    }

    @Test
    void getOtherParticipant() {
        try {
            Conversation conversation = daoConversationMySQL.createConversation(user1, user2);
            User otherParticipant = daoConversationMySQL.getOtherParticipant(conversation, user1);

            assertNotNull(otherParticipant);
            assertEquals(user2.getId(), otherParticipant.getId());
        } catch (SQLException e) {
            fail("Récupération de l'autre participant de la conversation a échoué : " + e.getMessage());
        }
    }

    @Test
    void findConversationByUsers() {
        try {
            Conversation createdConversation = daoConversationMySQL.createConversation(user1, user2);
            UUID foundConversationId = daoConversationMySQL.findConversationByUsers(user1, user2);

            assertNotNull(foundConversationId);
            assertEquals(createdConversation.getId(), foundConversationId);
        } catch (SQLException e) {
            fail("Recherche de la conversation par utilisateurs a échoué : " + e.getMessage());
        }
    }
}
