package com.sw.classes;

import java.time.LocalDate;

public class MusicalExpert extends User{
    public MusicalExpert(Integer id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role) {
        super(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
    }
}
