package com.sw.facades;

import com.sw.classes.Conversation;
import com.sw.classes.User;
import com.sw.dao.DAOConversation;
import com.sw.dao.factories.FactoryDAO;
import com.sw.exceptions.ExceptionDB;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class FacadeConversation {

    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    protected DAOConversation daoConversation = f.getInstanceofDAOConversation();

    private static FacadeConversation instance = null;

    public static FacadeConversation getInstance() {
        if (instance == null) {
            instance = new FacadeConversation();
        }
        return instance;
    }

    public Conversation createConversation(User user1, User user2) throws Exception {

        return daoConversation.createConversation(user1, user2);
    }

    public Conversation getConversation(UUID conversationId) throws Exception {
        return daoConversation.getConversation(conversationId);
    }

    public User getOtherParticipant(Conversation conversation, User currentUser) throws Exception {
        try {
            return daoConversation.getOtherParticipant(conversation, currentUser);
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de l'obtention de l'autre participant de la conversation", e);
        }
    }

    public List<Conversation> getConversationsByUser(User user) throws Exception {
        try {
            return daoConversation.getConversationsByUser(user);
        } catch (SQLException e) {
            throw new ExceptionDB("Erreur lors de l'obtention des conversations de l'utilisateur", e);
        }
    }


}
