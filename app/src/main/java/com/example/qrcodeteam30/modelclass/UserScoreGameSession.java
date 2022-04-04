package com.example.qrcodeteam30.modelclass;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;

import java.util.ArrayList;

/**
 * This class represents the basic information of a user in a single game session
 * Note that UserInformation.class represents the same info, but its scope is all the game sessions
 */
public class UserScoreGameSession implements Comparable<UserScoreGameSession> {
    private String username;  // username
    private String password;  // password
    private String firstName;  // first name
    private String lastName;  // last name
    private double score;  // total score in this game session of a user
    private ArrayList<QRCode> qrCodeArrayList;  // list of all qr code of the user in this game session

    /**
     * Constructor
     * Auto filter out all QR codes of the userInformation that is not belonged to the game
     * @param userInformation
     * @param game
     */
    public UserScoreGameSession(UserInformation userInformation, Game game) {
        this.username = userInformation.getUsername();
        this.password = userInformation.getPassword();
        this.firstName = userInformation.getFirstName();
        this.lastName = userInformation.getLastName();
        this.score = CalculateScoreController.calculateTotalScore(userInformation, game);
        this.qrCodeArrayList = new ArrayList<>();

        // Filter all qr code that is not belonged to the game
        for (var qrCode: userInformation.getQrCodeList()) {
            if (qrCode.getGameName().equals(game.getGameName())) {
                qrCodeArrayList.add(qrCode);
            }
        }
    }

    /**
     * Empty constructor
     */
    public UserScoreGameSession() {

    }

    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get hashed password
     * @return hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set hashed password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get first name
     * @return firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get total score in this game of the user
     * @return score
     */
    public double getScore() {
        return score;
    }

    /**
     * set total score in this game of the user
     * @param score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * get array list of QR codes in this game of the user
     * @return array list of QR codes
     */
    public ArrayList<QRCode> getQrCodeArrayList() {
        return qrCodeArrayList;
    }

    /**
     * set array list of QR codes in this game of the user
     * @param qrCodeArrayList
     */
    public void setQrCodeArrayList(ArrayList<QRCode> qrCodeArrayList) {
        this.qrCodeArrayList = qrCodeArrayList;
    }

    /**
     *
     * @return
     */
    public double maxScoreQRCode() {
        if (getQrCodeArrayList().size() == 0) {
            return -1;
        }

        double max = getQrCodeArrayList().get(0).getScore();
        for (QRCode qrCode: getQrCodeArrayList()) {
            if (qrCode.getScore() > max) {
                max = qrCode.getScore();
            }
        }
        return max;
    }

    /**
     * This is the comparator override method to user Collections.sort()
     * @param userScoreGameSession
     * @return
     */
    @Override
    public int compareTo(UserScoreGameSession userScoreGameSession) {
        return Double.compare(this.score, userScoreGameSession.getScore());
    }
}
