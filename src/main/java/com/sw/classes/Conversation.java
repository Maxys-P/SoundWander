package com.sw.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conversation {
    private UUID id;
    private User user1;
    private User user2;
    private List<Message> messages;

    public Conversation(User user1, User user2) {
        this.id = UUID.randomUUID();
        this.user1 = user1;
        this.user2 = user2;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    // Setters
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setId(UUID conversationId) { this.id = conversationId;
    }
}
