package com.example.qrcodeteam30.modelclass;

import java.io.Serializable;
import java.util.Date;

/**
 * This model class represents basic information of a game session (name, who creates, and date of creation)
 */
public class Game implements Serializable {
    private String gameName;  // Name of the game
    private String ownerUsername;  // Owner of the game
    private String date;  // Date of creation (in epoch time, up to millisecond)

    /**
     * Empty constructor
     */
    public Game() {

    }

    /**
     * Constructor
     * @param gameName
     * @param ownerUsername
     * @param date
     */
    public Game(String gameName, String ownerUsername, String date) {
        this.gameName = gameName;
        this.ownerUsername = ownerUsername;
        this.date = date;
    }

    /**
     * Constructor (auto assign current time)
     * @param gameName
     * @param ownerUsername
     */
    public Game(String gameName, String ownerUsername) {
        this.gameName = gameName;
        this.ownerUsername = ownerUsername;
        this.date = Long.toString(new Date().getTime());
    }

    /**
     * Getting game name
     * @return Name of the game
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Setting game name
     * @param gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Get owner of the game (in username)
     * @return owner username
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Setting owner of the game (set username)
     * @param ownerUsername
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * Get date of creation (should convert to epoch time (millisecond) before passing in
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date of creation, in epoch time (millisecond)
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
