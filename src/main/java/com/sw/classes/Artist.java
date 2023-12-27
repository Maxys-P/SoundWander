package com.sw.classes;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe représentant un artiste
 * Un artiste est un user qui peut ajouter des musiques, en proposer à des playlists, etc.
 */
public class Artist extends User {

    private List<Music> musics;

    /**
     * Default constructor
     */
    public Artist(int id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role) {
        super(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
    }

    public Artist(int id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role, List<Music> privatePlaylist) {
        super(id, pseudo, mail, motDePasse, dateNaissance, photo, role, privatePlaylist);
    }

    public Artist(int id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role, List<Music> privatePlaylist, List<Music> musics) {
        super(id, pseudo, mail, motDePasse, dateNaissance, photo, role, privatePlaylist);
        this.musics = musics;
    }

    public Artist(Integer id, String pseudo, String mail, String motDePasse, LocalDate dateNaissance, String photo, String role) {
        super(id, pseudo, mail, motDePasse, dateNaissance, photo, role);
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
}
