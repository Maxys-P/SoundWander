package com.sw.dao.daoMysql;

import com.sw.classes.Playlist;
import com.sw.dao.DAOPlaylist;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;

import java.util.HashMap;
import java.util.Map;

public class DAOPlaylistMySQL extends DAOPlaylist {
    public DAOPlaylistMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    @Override
    public Playlist getPLaylistByName(String name) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name", name);
        try{
            MapperResultSet playlistData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!playlistData.getListData().isEmpty()) {
                Map<String, Object> playlistDetails = playlistData.getListData().getFirst();

                int id = (int) playlistDetails.get("id");
                String Playlistname = (String) playlistDetails.get("name");
                String country = (String) playlistDetails.get("country");

                return new Playlist(id, Playlistname, country);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la playlist par nom");
            return null;
        }
    }

    @Override
    public void addPublicPlaylist(String name, String country) throws Exception {
        try {

            Map<String, Object> values = new HashMap<>();
            values.put("name", name);
            values.put("country", country);

            int insertedId = ((RequetesMySQL) requetesDB).create("playlist", values);
            System.out.println("Playlist ajoutée avec l'id : " + insertedId);

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de la playlist");
        }
    }
}
