package com.sw.dao.daoMysql;

import com.sw.classes.Conversation;
import com.sw.classes.User;
import com.sw.dao.DAOConversation;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.exceptions.ExceptionDB;
import com.sw.facades.FacadeUser;

import java.sql.SQLException;
import java.util.*;

public class DAOConversationMySQL extends DAOConversation {

    private FacadeUser userFacade = FacadeUser.getInstance();

    public DAOConversationMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    @Override
    public Conversation createConversation(User user1, User user2) throws SQLException {

        // Générer un nouvel UUID pour la conversation
        UUID conversationId = UUID.randomUUID();

        // Préparer les données pour la création de la conversation
        Map<String, Object> conversationData = new HashMap<>();
        conversationData.put("id", conversationId.toString());
        conversationData.put("user1Id", user1.getId());
        conversationData.put("user2Id", user2.getId());

        try {
            // Créer la conversation dans la base de données
            ((RequetesMySQL) requetesDB).createNoReturn("conversation", conversationData);

            // Créer l'objet Conversation avec le bon identifiant
            Conversation conversation = new Conversation(user1, user2);
            conversation.setId(conversationId); // Assurez-vous que la classe Conversation a un setter pour l'ID
            return conversation;
        } catch (Exception e) {
            throw new SQLException("Erreur lors de la création de la conversation", e);
        }
    }


    @Override
    public Conversation getConversation(UUID conversationId) throws SQLException {

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", conversationId.toString());

        try {
            MapperResultSet conversationData = ((RequetesMySQL) requetesDB).selectWhere("conversation", conditions);
            if (!conversationData.getListData().isEmpty()) {
                Map<String, Object> conversationDataMap = conversationData.getListData().getFirst();
                User user1 = userFacade.getUserById((int) conversationDataMap.get("user1Id"));
                User user2 = userFacade.getUserById((int) conversationDataMap.get("user2Id"));
                return new Conversation(user1, user2);
            } else {
                throw new SQLException("Conversation not found with ID: " + conversationId);
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la récupération de la conversation", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UUID findConversationByUsers(User user1, User user2) throws SQLException {
        Map<String, Object> whereConditions = new HashMap<>();
        whereConditions.put("user1Id", user1.getId());
        whereConditions.put("user2Id", user2.getId());

        try {
            MapperResultSet resultSet = ((RequetesMySQL) requetesDB).selectWhere("conversation", whereConditions);
            if (!resultSet.getListData().isEmpty()) {
                Map<String, Object> firstRow = resultSet.getListData().getFirst();
                return UUID.fromString((String) firstRow.get("id"));
            }

            // Si aucune conversation n'est trouvée avec user1Id et user2Id, vérifiez l'inverse
            whereConditions.clear();
            whereConditions.put("user1Id", user2.getId());
            whereConditions.put("user2Id", user1.getId());

            resultSet = ((RequetesMySQL) requetesDB).selectWhere("conversation", whereConditions);
            if (!resultSet.getListData().isEmpty()) {
                Map<String, Object> firstRow = resultSet.getListData().getFirst();
                return UUID.fromString((String) firstRow.get("id"));
            }
        } catch (ExceptionDB e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    @Override
    public User getOtherParticipant(Conversation conversation, User currentUser) throws SQLException {
        User user1 = conversation.getUser1();
        User user2 = conversation.getUser2();

        if (user1.getId() == currentUser.getId()) {
            return user2;
        } else if (user2.getId() == currentUser.getId()) {
            return user1;
        } else {
            throw new SQLException("Utilisateur courant non trouvé dans la conversation");
        }
    }


    @Override
    public List<Conversation> getConversationsByUser(User user) throws SQLException {
        List<Conversation> conversations = new ArrayList<>();

        try {
            // Appel à la méthode personnalisée dans RequetesMySQL
            MapperResultSet resultSet = ((RequetesMySQL) requetesDB).selectConversationsByUser("conversation", user.getId());

            // Utiliser une boucle for sur le résultat
            for (Map<String, Object> row : resultSet.getListData()) {
                // Récupérer les utilisateurs impliqués dans la conversation
                User user1 = userFacade.getUserById((int) row.get("user1Id"));
                User user2 = userFacade.getUserById((int) row.get("user2Id"));
                UUID conversationId = UUID.fromString((String) row.get("id"));

                // Créer et ajouter la conversation à la liste
                Conversation conversation = new Conversation(user1, user2);
                conversation.setId(conversationId);
                conversations.add(conversation);
            }
        } catch (Exception e) {
            throw new SQLException("Erreur lors de la récupération des conversations de l'utilisateur", e);
        }

        return conversations;
    }




}
