package com.example.qrcodeteam30.modelclass;

import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable {
    private String gameName;
    private String ownerUsername;
    private String date;

    public Game() {

    }

    public Game(String gameName, String ownerUsername, String date) {
        this.gameName = gameName;
        this.ownerUsername = ownerUsername;
        this.date = date;
    }

    public Game(String gameName, String ownerUsername) {
        this.gameName = gameName;
        this.ownerUsername = ownerUsername;
        this.date = Long.toString(new Date().getTime());
    }
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
