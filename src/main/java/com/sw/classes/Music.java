package com.sw.classes;


import com.sw.facades.FacadeMusic;

public class Music {

    private FacadeMusic facadeMusic;
    private int id;
    private String name;
    private int artist;
    private int duration;
    /**
     * Le fichier de musique stocké sous forme de tableau d'octets.
     */
    private byte[] musicFile;

    public void setFacadeMusicPlay(FacadeMusic facadeMusic){
        this.facadeMusic = facadeMusic;
    }

    public Music(int id, String name, int artist, int duration, byte[] musicFile){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.musicFile = musicFile;
    }

    @Override
    public String toString(){
        return "Music{" +
                "id=" + id + '\'' +
                ", name=" + name + '\'' +
                 ", artist=" + artist + '\'' +
                ", duration=" + '\'' + duration +
                ", musicFile=" + musicFile + '\'' +
                '}';
    }

    public FacadeMusic getFacadeMusicPlay(){
        return facadeMusic;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public int getArtist(){return artist;}
    public int getDuration(){return duration;}

    /**
     * Getter pour le fichier de musique.
     * @return Le fichier de musique sous forme de tableau d'octets.
     */
    public byte[] getMusicFile() {
        return musicFile;
    }

    /**
     * Setter pour le fichier de musique.
     * @param musicFile Le fichier de musique sous forme de tableau d'octets.
     */
    public void setMusicFile(byte[] musicFile) {
        this.musicFile = musicFile;
    }



    public void setId(int id){this.id = id;}

    public void setName(String name){this.name = name;}

    public void setArtist(int artist){this.artist = artist;}

    public void setDuration(int duration){this.duration = duration;}
}
