package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.classes.User;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import com.sw.classes.Proposal;
import com.sw.dao.DAOProposal;

import java.time.LocalDate;
import java.util.*;

public class DAOProposalMySQL extends DAOProposal {
    /**
     * Default constructor
     */
    public DAOProposalMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    /**
     * Méthode pour créer une proposal
     * @param country String, le country de la proposal
     * @param description String, la description de la proposal
     * @param music Music, la music de la proposal
     * @param artist User, l'artist de la proposal
     * @return Proposal, la proposal créé
     * @throws Exception si problème pendant la création de la proposal
     */
    @Override
    public Proposal createProposal(String country, String description, Music music, User artist) throws Exception {
        // Préparer les valeurs à insérer, en respectant le type des colonnes de la table proposal
        Map<String, Object> proposalData = new LinkedHashMap<>();
        proposalData.put("country", country);
        proposalData.put("description", description);
        proposalData.put("artist", artist.getId());
        proposalData.put("music", music.getId());

        // Appeler la méthode create de RequetesMySQL
        // Pour l'instant, cette méthode ne fait rien, donc nous simulerons l'appel sans effectuer d'insertion réelle
        try {
            int id = ((RequetesMySQL) requetesDB).create(table, proposalData);
            return getProposalById(id);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la création du user", e);
        }
    }

    /**
     * Méthode pour récupérer un utilisateur par son id
     * @param id int, l'id de l'utilisateur
     * @return User, l'utilisateur récupéré
     * @throws Exception si problème pendant la récupération du user
     */
    @Override
    public Proposal getProposalById(int id) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", id);

        try {
            MapperResultSet proposalData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!proposalData.getListData().isEmpty()) {
                Map<String, Object> proposalDetails = proposalData.getListData().getFirst();

                String country = (String) proposalDetails.get("country");
                String description = (String) proposalDetails.get("description");
                Music music = (Music) proposalDetails.get("music");
                User artist = (User) proposalDetails.get("artist");

                return new Proposal(id, country, description, music, artist);
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de la proposition par id", e);
        }
    }
    /**
     * Méthode pour récupérer toutes les propositions
     * @return List<Proposal>, la liste des propositions
     * @throws Exception si problème pendant la récupération des propositions
     */
    public List<Proposal> getAllProposals() throws Exception {
        List<Proposal> proposals = new ArrayList<>();
        try {
            //Appel de la méthode selectAll de RequetesMySQL
            MapperResultSet proposalData = ((RequetesMySQL) requetesDB).selectAll(table);
            // Parcourir le MapperResultSet et construire la liste des users
            List<Map<String, Object>> listData = proposalData.getListData();
            for (Map<String, Object> row : listData) {
                try {
                    Integer id = (Integer) row.get("id");
                    String country = (String) row.get("country");
                    String description = (String) row.get("description");
                    Music music = (Music) row.get("music");
                    User artist = (User) row.get("artist");

                    Proposal proposal = new Proposal(id, country, description, music, artist);
                    proposals.add(proposal);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la récupération d'une proposition : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'utilisateur par mail", e);
        }
        return proposals;
    }

    /**
     * Méthode pour récupérer les propositions par country
     * @param country String, le pays de la proposition
     * @return List<Proposal>, le propositions récupérées
     * @throws Exception si problème pendant la récupération des propositions
     */
    @Override
    public List<Proposal> getProposalByCountry(String country) throws Exception {
        List<Proposal> proposals = new ArrayList<>();
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("country", country);
        //on ajoute ici d'autres conditions si besoin pour vos usecases
        try {
            // Appel de selectWhere
            MapperResultSet proposalData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            List<Map<String, Object>> listData = proposalData.getListData();

            for (Map<String, Object> proposalDetails : listData) {
                Integer id = (Integer) proposalDetails.get("id");
                String description = (String) proposalDetails.get("description");
                Music music = (Music) proposalDetails.get("music");
                User artist = (User) proposalDetails.get("artist");

                Proposal proposal = new Proposal(id, country, description, music, artist);
                proposals.add(proposal);
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de la proposition par country", e);
        }
        return proposals;
    }

    /**
     * Méthode qui supprime une proposal de la base de donnée
     * @param id int, l'id de la proposition
     * @throws Exception si une erreur survient lors de la suppression de la proposition
     */
    public boolean deleteProposal(int id) throws Exception {
        try {
            // Création de la condition WHERE avec l'ID
            Map<String, Object> whereConditions = new HashMap<>();
            whereConditions.put("id", id);

            // Appel de la méthode générique delete de RequetesMySQL
            int affectedRows = ((RequetesMySQL) requetesDB).delete(table, whereConditions);

            // Vérifier si la suppression a affecté au moins une ligne
            return affectedRows > 0;
        } catch (Exception e) {
            // Gérer ou propager d'autres types d'exceptions
            throw new Exception("Erreur inattendue lors de la suppression de la proposition", e);
        }
    }

    public boolean acceptProposal(int id) throws Exception {
        // 1. Récupérer la proposition à partir de son ID
        Proposal proposal = getProposalById(id);
        if (proposal == null) {
            throw new Exception("La proposition avec l'ID spécifié n'existe pas.");
        }

        // 2. Ajouter la musique à la liste/playlist du pays
        // Simuler l'ajout à une liste à modifier lorsque les playlists seront implémentées
        String country = proposal.getCountry();
        Music musicToAdd = proposal.getMusic();
        List<Music> playlist = new ArrayList<>();
        playlist.add(musicToAdd);
        // TODO : ajouter la musique à la playlist du pays
        // TODO : notifier l'artiste que sa proposition est acceptée
        // 3. Supprimer la proposition de la base de données
        boolean isDeleted = deleteProposal(id);
        if (!isDeleted) {
            throw new Exception("Échec de la suppression de la proposition après acceptation.");
        }

        return true;
    }

    public boolean refuseProposal(int id) throws Exception {
        // 1. Récupérer la proposition à partir de son ID
        Proposal proposal = getProposalById(id);
        if (proposal == null) {
            throw new Exception("La proposition avec l'ID spécifié n'existe pas.");
        }

        //TODO : notifier l'artiste que sa proposition est refusée

        // 2. Supprimer la proposition de la base de données
        boolean isDeleted = deleteProposal(id);
        if (!isDeleted) {
            throw new Exception("Échec de la suppression de la proposition après refus.");
        }

        return true;
    }


}

