package com.sw.dao.daoMysql;

import com.sw.classes.Admin;
import com.sw.classes.Artist;
import com.sw.classes.MusicalExpert;
import com.sw.classes.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DAOUserMySQLTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        String pseudo = "TestUser";
        String mail = "testuser@example.com";
        String motDePasse = "password123";
        LocalDate dateNaissance = LocalDate.of(2000, 1, 1);

        try {
            User user = daoUserMySQL.createUser(pseudo, mail, motDePasse, dateNaissance);

            assertNotNull(user);
            assertEquals(pseudo, user.getPseudo());
            assertEquals(mail, user.getMail());
            // Vous pouvez également vérifier le mot de passe hashé et la date de naissance
        } catch (Exception e) {
            fail("La création de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void getAllUsers() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();

        try {
            List<User> users = daoUserMySQL.getAllUsers();

            assertNotNull(users);
            assertFalse(users.isEmpty()); // Assurez-vous que la liste n'est pas vide
            // Vous pouvez également vérifier certains attributs des utilisateurs si vous connaissez les données de votre base de données de test
        } catch (Exception e) {
            fail("La récupération de tous les utilisateurs a échoué : " + e.getMessage());
        }
    }


    @Test
    void getUserByMail() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        String mail = "existinguser@example.com";

        try {
            User user = daoUserMySQL.getUserByMail(mail);

            assertNotNull(user);
            assertEquals(mail, user.getMail());
            // Autres vérifications si nécessaire
        } catch (Exception e) {
            fail("La récupération de l'utilisateur par mail a échoué : " + e.getMessage());
        }
    }


    @Test
    void getUserById() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        try {
            User user = daoUserMySQL.getUserById(userId);

            assertNotNull(user);
            assertEquals(userId, user.getId());
            // Autres vérifications si nécessaire
        } catch (Exception e) {
            fail("La récupération de l'utilisateur par ID a échoué : " + e.getMessage());
        }
    }


    @Test
    void updateUser() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        Map<String, Object> updates = new HashMap<>();
        updates.put("pseudo", "UpdatedPseudo");

        try {
            User updatedUser = daoUserMySQL.updateUser(userId, updates);

            assertNotNull(updatedUser);
            assertEquals("UpdatedPseudo", updatedUser.getPseudo());
            // Vérifiez d'autres champs si nécessaire
        } catch (Exception e) {
            fail("La mise à jour de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void updateUserPseudo() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        String newPseudo = "NewPseudo";

        try {
            daoUserMySQL.updateUserPseudo(userId, newPseudo);
            User updatedUser = daoUserMySQL.getUserById(userId);

            assertNotNull(updatedUser);
            assertEquals(newPseudo, updatedUser.getPseudo());
        } catch (Exception e) {
            fail("La mise à jour du pseudo de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void updateUserMail() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        String newMail = "newmail@example.com";

        try {
            daoUserMySQL.updateUserMail(userId, newMail);
            User updatedUser = daoUserMySQL.getUserById(userId);

            assertNotNull(updatedUser);
            assertEquals(newMail, updatedUser.getMail());
        } catch (Exception e) {
            fail("La mise à jour du mail de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void updateUserMotDePasse() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        String newMotDePasse = "newPassword123";

        try {
            daoUserMySQL.updateUserMotDePasse(userId, newMotDePasse);
            // Vous pouvez ajouter une logique pour vérifier le mot de passe hashé
            // Par exemple, récupérer l'utilisateur et utiliser une méthode de vérification de mot de passe
        } catch (Exception e) {
            fail("La mise à jour du mot de passe de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void updateUserDateNaissance() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        LocalDate newDateNaissance = LocalDate.of(1995, 5, 15);

        try {
            daoUserMySQL.updateUserDateNaissance(userId, newDateNaissance);
            User updatedUser = daoUserMySQL.getUserById(userId);

            assertNotNull(updatedUser);
            assertEquals(newDateNaissance, updatedUser.getDateNaissance());
        } catch (Exception e) {
            fail("La mise à jour de la date de naissance de l'utilisateur a échoué: " + e.getMessage());
        }
    }
    @Test
    void updateUserPhoto() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;
        String newPhoto = "path/to/new/photo.jpg";

        try {
            daoUserMySQL.updateUserPhoto(userId, newPhoto);
            User updatedUser = daoUserMySQL.getUserById(userId);

            assertNotNull(updatedUser);
            assertEquals(newPhoto, updatedUser.getPhoto());
        } catch (Exception e) {
            fail("La mise à jour de la photo de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void setUserRole() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", 1);
        userData.put("pseudo", "TestUser");
        userData.put("mail", "testuser@example.com");
        userData.put("motDePasse", "password123");
        userData.put("dateNaissance", LocalDate.of(2000, 1, 1));
        userData.put("photo", "path/to/photo.jpg");
        userData.put("role", "artist");

        try {
            User userWithRole = daoUserMySQL.setUserRole(userData);
            assertNotNull(userWithRole);
            assertInstanceOf(Artist.class, userWithRole); // Vérifiez que l'utilisateur est bien un Artist
        } catch (Exception e) {
            fail("La définition du rôle de l'utilisateur a échoué : " + e.getMessage());
        }
    }


    @Test
    void userBecomeArtist() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;

        try {
            Artist artist = daoUserMySQL.userBecomeArtist(userId);
            assertNotNull(artist);
            assertEquals("artist", artist.getRole());
        } catch (Exception e) {
            fail("Le passage de l'utilisateur au statut d'artiste a échoué : " + e.getMessage());
        }
    }


    @Test
    void userBecomeMusicalExpert() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;

        try {
            MusicalExpert musicalExpert = daoUserMySQL.userBecomeMusicalExpert(userId);
            assertNotNull(musicalExpert);
            assertEquals("musicalExpert", musicalExpert.getRole());
            // Autres vérifications si nécessaire
        } catch (Exception e) {
            fail("Le passage de l'utilisateur au statut d'expert musical a échoué : " + e.getMessage());
        }
    }


    @Test
    void userBecomeAdmin() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int userId = 1;

        try {
            Admin admin = daoUserMySQL.userBecomeAdmin(userId);
            assertNotNull(admin);
            assertEquals("admin", admin.getRole());
            // Autres vérifications si nécessaire
        } catch (Exception e) {
            fail("Le passage de l'utilisateur au statut d'admin a échoué : " + e.getMessage());
        }
    }


    @Test
    void isArtistSubscribed() {
        DAOUserMySQL daoUserMySQL = new DAOUserMySQL();
        int artistId = 1;

        try {
            boolean isSubscribed = daoUserMySQL.isArtistSubscribed(artistId);
            assertTrue(isSubscribed); // ou assertFalse, selon l'état d'abonnement attendu de l'artiste
        } catch (Exception e) {
            fail("La vérification de l'abonnement de l'artiste a échoué : " + e.getMessage());
        }
    }

}