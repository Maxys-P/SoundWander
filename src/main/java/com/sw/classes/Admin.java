package com.sw.classes;

import java.time.LocalDate;

public class Admin extends User {
    public Admin(Integer id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role) {
        super(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
    }
}
