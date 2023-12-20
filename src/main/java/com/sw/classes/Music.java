package com.sw.classes;


import com.sw.facades.FacadeMusicPlay;

public class Music {

    private FacadeMusicPlay facadeMusicPlay;
    private int id;
    private String name;
    private int artist;
    private int duration;
    /**
     * Le fichier de musique stocké sous forme de tableau d'octets.
     */
    private byte[] musicFile;

    public void setFacadeMusicPlay(FacadeMusicPlay facadeMusicPlay){
        this.facadeMusicPlay = facadeMusicPlay;
    }

    public Music(int id, String name, int artist, int duration){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
    }

    @Override
    public String toString(){
        return "Music{" +
                "id=" + id + '\'' +
                ", name=" + name + '\'' +
                 ", artist=" + artist + '\'' +
                ", duration=" + '\'' + duration +
                '}';
    }

    public FacadeMusicPlay getFacadeMusicPlay(){
        return facadeMusicPlay;
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
