package com.sw.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private Message message;
    private User sender;
    private User receiver;
    private LocalDateTime testDateTime;
    private String testContent;
    private String testConversationId;

    @BeforeEach
    void setUp() {
        sender = new User(1, "Sender", "sender@example.com", "password", LocalDate.now(), "senderPhoto", "user");
        receiver = new User(2, "Receiver", "receiver@example.com", "password", LocalDate.now(), "receiverPhoto", "user");
        testDateTime = LocalDateTime.now();
        testContent = "Test message content";
        testConversationId = UUID.randomUUID().toString();
        message = new Message(testContent, sender, receiver, testConversationId, testDateTime);
    }

    @Test
    void getId() {
        assertNotNull(message.getId());
    }

    @Test
    void getContent() {
        assertEquals(testContent, message.getContent());
    }

    @Test
    void getSender() {
        assertEquals(sender, message.getSender());
    }

    @Test
    void getReceiver() {
        assertEquals(receiver, message.getReceiver());
    }

    @Test
    void getConversation() {
        assertEquals(testConversationId, message.getConversation());
    }

    @Test
    void getCreationDate() {
        assertEquals(testDateTime, message.getCreationDate());
    }

    @Test
    void setContent() {
        String newContent = "New content";
        message.setContent(newContent);
        assertEquals(newContent, message.getContent());
    }

    @Test
    void setSender() {
        User newSender = new User(3, "NewSender", "newsender@example.com", "newpassword", LocalDate.now(), "newSenderPhoto", "user");
        message.setSender(newSender);
        assertEquals(newSender, message.getSender());
    }

    @Test
    void setReceiver() {
        User newReceiver = new User(4, "NewReceiver", "newreceiver@example.com", "newpassword", LocalDate.now(), "newReceiverPhoto", "user");
        message.setReceiver(newReceiver);
        assertEquals(newReceiver, message.getReceiver());
    }

    @Test
    void setCreationDate() {
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        message.setCreationDate(newDateTime);
        assertEquals(newDateTime, message.getCreationDate());
    }
}
