package com.sw.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private LocalDate testDate;
    private ArrayList<Music> testPlaylist;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(1990, 1, 1);
        testPlaylist = new ArrayList<>();
        user = new User(1, "TestUser", "test@example.com", "password123", testDate, "photo.jpg", "user", testPlaylist);
    }

    @Test
    void testToString() {
        // Créez un User avec des données de test connues
        user = new User(1, "TestUser", "test@example.com", "password123", testDate, "photo.jpg", "user", testPlaylist);

        // Construisez la chaîne de caractères attendue
        String expectedString = "User{" +
                "id=" + user.getId() + '\'' +
                ", pseudo='" + user.getPseudo() + '\'' +
                ", mail='" + user.getMail() + '\'' +
                ", motDePasse='" + user.getMotDePasse() + '\'' +
                ", dateNaissance=" + user.getDateNaissance() + '\'' +
                ", photo=" + user.getPhoto() + '\'' +
                ", role=" + user.getRole() + '\'' +
                ", privatePlaylist=" + user.getPrivatePlaylist() +
                '}';

        // Comparez la sortie de toString() avec la chaîne attendue
        assertEquals(expectedString, user.toString());
    }




    @Test
    void getId() {
        assertEquals(1, user.getId());
    }

    @Test
    void getPseudo() {
        assertEquals("TestUser", user.getPseudo());
    }

    @Test
    void getMail() {
        assertEquals("test@example.com", user.getMail());
    }

    @Test
    void getMotDePasse() {
        assertEquals("password123", user.getMotDePasse());
    }

    @Test
    void getDateNaissance() {
        assertEquals(testDate, user.getDateNaissance());
    }

    @Test
    void getPhoto() {
        assertEquals("photo.jpg", user.getPhoto());
    }

    @Test
    void getPrivatePlaylist() {
        assertEquals(testPlaylist, user.getPrivatePlaylist());
    }

    @Test
    void getRole() {
        assertEquals("user", user.getRole());
    }

}
