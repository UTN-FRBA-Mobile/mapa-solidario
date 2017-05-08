package com.utn.mobile.mapasolidario;

/**
 * Created by svillarreal on 07/05/17.
 */

class NewsEvent {
    private String newsUserImg, newsTitle, newsDescription, newsDueDate;
    private boolean newsIsImportant;

    public NewsEvent(String newsUserImg, String newsTitle, String newsDescription, boolean newsIsImportant, String newsDueDate) {
        this.newsUserImg = newsUserImg;
        this.newsTitle = newsTitle;
        this.newsDescription = newsDescription;
        this.newsIsImportant = newsIsImportant;
        this.newsDueDate = newsDueDate;
    }

    public String getNewsUserImg() {
        return newsUserImg;
    }

    public void setNewsUserImg(String newsUserImg) {
        this.newsUserImg = newsUserImg;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public boolean isNewsIsImportant() {
        return newsIsImportant;
    }

    public void setNewsIsImportant(boolean newsIsImportant) {
        this.newsIsImportant = newsIsImportant;
    }

    public String getNewsDueDate() {
        return newsDueDate;
    }

    public void setNewsDueDate(String newsDueDate) {
        this.newsDueDate = newsDueDate;
    }
}
