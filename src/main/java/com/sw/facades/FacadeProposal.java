package com.sw.facades;

import com.sw.dao.DAOProposal;
import com.sw.dao.DAOUser;
import com.sw.dao.factories.FactoryDAO;
import com.sw.classes.User;
import com.sw.classes.Proposal;
import com.sw.classes.Music;
import java.util.List;

public class FacadeProposal extends Facade{
    /**
     * Factory pour les DAO
     */
    protected FactoryDAO f = FactoryDAO.getInstanceofFactoryDAO();

    /**
     * DAO pour les propositions
     */
    protected DAOProposal daoProposal = f.getInstanceofDAOProposal();

    /**
     * Instance de la facade pour le singleton
     */
    private static FacadeProposal instance = null;

    /**
     * Getter de l'instance de la facade pour le singleton
     * @return FacadeProposal, l'instance de la facade
     */
    public static FacadeProposal getInstance() {
        if (instance == null) {
            instance = new FacadeProposal();
        }
        return instance;
    }

    /**
     * Crée une nouvelle proposition musicale
     * @param artist L'artiste qui fait la proposition
     * @param music La musique proposée
     * @param country Le pays de la proposition
     * @param description Description supplémentaire de la proposition
     * @return La proposition créée
     * @throws Exception si la création échoue
     */
    public Proposal createProposal(User artist, Music music, String country, String description) throws Exception {
        // Vérifier si un pays a bien été sélectionné
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Un pays doit être sélectionné pour créer une proposition.");
        }

        // Enregistrer la nouvelle proposition via DAOProposal
        // La méthode createProposal dans DAOProposal crée un nouvel objet Proposal avec les informations fournies et l'enregistre dans la base de données
        Proposal createdProposal = daoProposal.createProposal(country, description, music, artist);

        // Retourner la proposition créée
        return createdProposal;
    }

    /**
     * Récupère une proposition par son ID.
     * @param id L'identifiant de la proposition à récupérer.
     * @return La proposition correspondante, ou null si aucune proposition avec cet ID n'existe.
     * @throws Exception si une erreur survient pendant la récupération.
     */
    public Proposal getProposalById(int id) throws Exception {
        try {
            return daoProposal.getProposalById(id);
        } catch (Exception e) {
            // Gérer l'exception ou la propager
            throw new Exception("Erreur lors de la récupération de la proposition : " + e.getMessage(), e);
        }
    }

    /**
     * Récupère toutes les propositions de la base de données.
     * @return Liste de toutes les propositions, peut être vide si aucune proposition n'est trouvée.
     * @throws Exception si une erreur survient pendant la récupération.
     */
    public List<Proposal> getAllProposals() throws Exception {
        try {
            return daoProposal.getAllProposals();
        } catch (Exception e) {
            // Gérer l'exception ou la propager
            throw new Exception("Erreur lors de la récupération des propositions : " + e.getMessage(), e);
        }
    }

    /**
     * Récupère toutes les propositions pour un pays spécifique.
     * @param country Le pays pour lequel récupérer les propositions.
     * @return Liste de toutes les propositions pour le pays, peut être vide.
     * @throws Exception si une erreur survient pendant la récupération.
     */
    public List<Proposal> getAllProposalsByCountry(String country) throws Exception {
        try {
            return daoProposal.getProposalByCountry(country);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des propositions pour le pays : " + country, e);
        }
    }

    /**
     * Accepte une proposition spécifiée par son ID.
     * @param proposalId L'ID de la proposition à accepter.
     * @return true si l'opération réussit.
     * @throws Exception si une erreur survient pendant l'acceptation.
     */
    public boolean acceptProposal(int proposalId) throws Exception {
        try {
            return daoProposal.acceptProposal(proposalId);
        } catch (Exception e) {
            throw new Exception("Erreur lors de l'acceptation de la proposition : " + proposalId, e);
        }
    }

    /**
     * Refuse une proposition spécifiée par son ID.
     * @param proposalId L'ID de la proposition à refuser.
     * @return true si l'opération réussit.
     * @throws Exception si une erreur survient pendant le refus.
     */
    public boolean refuseProposal(int proposalId) throws Exception {
        try {
            return daoProposal.refuseProposal(proposalId);
        } catch (Exception e) {
            throw new Exception("Erreur lors du refus de la proposition : " + proposalId, e);
        }
    }
}
