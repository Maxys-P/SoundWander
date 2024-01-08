package com.sw.dao;

import com.sw.classes.Message;
import com.sw.classes.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DAOMessage extends DAO {

    public DAOMessage() {
        super("message");
    }

    public abstract Message createMessage(String content, User sender, User receiver, LocalDateTime creationDate) throws Exception;

    public abstract void deleteMessage(UUID messageId) throws Exception;

    public abstract Message getMessage(UUID messageId) throws Exception;

    public abstract List<Message> getMessagesByConversation(String conversationId) throws Exception;

}
