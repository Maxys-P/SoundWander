package com.sw.dao.boiteAOutils;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitaire pour l'authentification
 */
public class AuthentificationManager {
    /**
     * Default constructor
     * @param plainTextPassword String, le mot de passe en clair
     * @return String, le mot de passe hashé
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * Méthode pour vérifier le mot de passe
     * @param plainTextPassword String, le mot de passe en clair
     * @param hashedPassword String, le mot de passe hashé
     * @return boolean, true si le mot de passe est correct, false sinon
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
