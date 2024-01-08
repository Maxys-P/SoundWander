package com.sw.classes;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private UUID id;
    private String content;
    private User sender;
    private User receiver;
    private String conversation;
    private LocalDateTime creationDate;

    public Message(String content, User sender, User receiver, String conversation, LocalDateTime creationDate) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.conversation = conversation;
        this.creationDate = creationDate;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getConversation() {
        return conversation;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = String.valueOf(conversation.getId());
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
