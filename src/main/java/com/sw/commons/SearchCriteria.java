package com.sw.commons;

public class SearchCriteria {
    private String searchTerm; // Terme de recherche général, peut être nom de musique ou pseudo d'artiste
    private String type; // "music" ou "artist"

    public SearchCriteria(String searchTerm, String type) {
        this.searchTerm = searchTerm;
        this.type = type;
    }

    // Getters
    public String getSearchTerm() {
        return searchTerm;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setType(String type) {
        this.type = type;
    }
}

