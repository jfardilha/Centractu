package com.example.centractu;

class Article {
    private String titre;
    private String description;
    private String url;
    private String url_image;

    String getTitre() {
        return titre;
    }

    String getDescription() {
        return description;
    }

    String getUrl() {
        return url;
    }

    String getUrl_image() {
        return url_image;
    }

    Article(String titre, String description, String url, String url_image) {
        this.titre = titre;
        this.description = description;
        this.url = url;
        this.url_image = url_image;
    }
}
