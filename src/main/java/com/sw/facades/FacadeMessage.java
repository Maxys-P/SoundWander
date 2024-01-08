package com.sw.facades;

import com.sw.classes.Message;
import com.sw.classes.User;
import com.sw.dao.DAOMessage;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.exceptions.ExceptionDB;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FacadeMessage {

    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    protected DAOMessage daoMessage = f.getInstanceofDAOMessage();

    private static FacadeMessage instance = null;

    public static FacadeMessage getInstance() {
        if (instance == null) {
            instance = new FacadeMessage();
        }
        return instance;
    }

    public Message createMessage(String content, User sender, User receiver) throws Exception {

        return daoMessage.createMessage(content, sender, receiver, LocalDateTime.now());
    }

    public void deleteMessage(UUID messageId) throws Exception {
        daoMessage.deleteMessage(messageId);
    }

    public Message getMessage(UUID messageId) throws Exception {
        return daoMessage.getMessage(messageId);
    }

    public List<Message> getMessagesByConversation(UUID conversationId) throws Exception {
        return daoMessage.getMessagesByConversation(String.valueOf(conversationId));
    }
}
