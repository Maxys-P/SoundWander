package com.sw.dao.daoMysql;

import com.sw.classes.Music;
import com.sw.dao.DAOMusic;
import com.sw.dao.boiteAOutils.MapperResultSet;
import com.sw.dao.requetesDB.RequetesMySQL;
import java.util.HashMap;
import java.util.Map;

public class DAOMusicMySQL extends DAOMusic {

    public DAOMusicMySQL() {
        super();
        this.requetesDB = new RequetesMySQL();
    }

    public Music createMusic(String name, String Artist, int duration){
        return null;
    }
    @Override
    public Music getMusicByName(String name) {
        return null;
    }

    @Override
    public Music getMusicById(int musicId) throws Exception {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("id", musicId);
        try {
            MapperResultSet musicData = ((RequetesMySQL) requetesDB).selectWhere(table, conditions);
            if (!musicData.getListData().isEmpty()) {
                Map<String, Object> musicDetails = musicData.getListData().getFirst();

                String name = (String) musicDetails.get("name");
                int artist = (int) musicDetails.get("artist");
                int duration = (int) musicDetails.get("duration");
                byte[] musicFile = (byte[]) musicDetails.get("musicFile");

                return new Music(musicId, name, artist, duration, musicFile);
            } else {
                System.out.println("Pb quand on appelle selectWhere");
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de la musique par ID", e);
        }
    }


    @Override
    public Music getNextMusic(int currentId) throws Exception {
        getMusicById(currentId+1);
        return null;
    }

    @Override
    public Music getPreviousMusic(int id) throws Exception{
        getMusicById(id-1);
        return null;
    }

    @Override
    public void addPrivatePlaylist(String name) {
    }


}