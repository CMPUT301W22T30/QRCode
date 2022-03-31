package com.example.qrcodeteam30.modelclass;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;

import java.util.ArrayList;

public class UserScoreGameSession implements Comparable<UserScoreGameSession> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double score;
    private ArrayList<QRCode> qrCodeArrayList;


    public UserScoreGameSession(UserInformation userInformation, Game game) {
        this.username = userInformation.getUsername();
        this.password = userInformation.getPassword();
        this.firstName = userInformation.getFirstName();
        this.lastName = userInformation.getLastName();
        this.score = CalculateScoreController.calculateTotalScore(userInformation, game);
        this.qrCodeArrayList = new ArrayList<>();
        for (var qrCode: userInformation.getQrCodeList()) {
            if (qrCode.getGameName().equals(game.getGameName())) {
                qrCodeArrayList.add(qrCode);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ArrayList<QRCode> getQrCodeArrayList() {
        return qrCodeArrayList;
    }

    public void setQrCodeArrayList(ArrayList<QRCode> qrCodeArrayList) {
        this.qrCodeArrayList = qrCodeArrayList;
    }

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

    @Override
    public int compareTo(UserScoreGameSession userScoreGameSession) {
        return Double.compare(this.score, userScoreGameSession.getScore());
    }
}
