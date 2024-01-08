package com.sw.dao;

import com.sw.classes.Conversation;
import com.sw.classes.User;

import java.util.List;
import java.util.UUID;

public abstract class DAOConversation extends DAO {

    public DAOConversation() {
        super("conversation");
    }

    public abstract Conversation createConversation(User user1, User user2) throws Exception;

    public abstract Conversation getConversation(UUID conversationId) throws Exception;

    public abstract User getOtherParticipant(Conversation conversation, User currentUser) throws Exception;
    public abstract List<Conversation> getConversationsByUser(User user) throws Exception;
}

    // Vous pouvez ajouter ici des méthodes supplémentaires pour la mise à jour, la suppression, et la récupération de toutes les conversations impliquant un utilisateur spécifique.

