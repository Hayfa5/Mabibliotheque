package com.example.mabibliotheque;

public class histoire {
    private int id; // New field for id
    private String nom;
    private String Description;
    private String URL;
    private String image;

    public histoire(){

    };

    public histoire(String nom, String Description, String URL, String image) {

        this.id = (int) System.currentTimeMillis();
        this.nom = nom;
        this.Description = Description;
        this.URL = URL;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
