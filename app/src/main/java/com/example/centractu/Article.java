package com.example.centractu;

public class Article {
    public String titre;
    public String description;
    public String url;
    public String url_image;

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrl_image() {
        return url_image;
    }

    public Article(String titre, String description, String url, String url_image) {
        this.titre = titre;
        this.description = description;
        this.url = url;
        this.url_image = url_image;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
}
