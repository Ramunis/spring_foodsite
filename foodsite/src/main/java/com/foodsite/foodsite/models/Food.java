package com.foodsite.foodsite.models;

import javax.persistence.*;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String df;
    private String title;
    private String text;
    private String len;
    private String ing;
    private String step;
    private String pic;

    private String youtube;
    @ManyToOne
    Client client;
    @ManyToOne
    Theme theme;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Food(){
    }

    public Food(String df, String title, String text, String len, String ing, String step, String pic, String youtube, Client client, Theme theme) {
        this.df = df;
        this.title = title;
        this.text = text;
        this.len = len;
        this.ing = ing;
        this.step = step;
        this.pic = pic;
        this.youtube = youtube;
        this.client = client;
        this.theme = theme;
    }
}
