package com.example.qrcodeteam30.modelclass;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;

public class UserScoreGameSession implements Comparable<UserScoreGameSession> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double score;

    public UserScoreGameSession(UserInformation userInformation, Game game) {
        this.username = userInformation.getUsername();
        this.password = userInformation.getPassword();
        this.firstName = userInformation.getFirstName();
        this.lastName = userInformation.getLastName();
        this.score = CalculateScoreController.calculateTotalScore(userInformation, game);
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

    @Override
    public int compareTo(UserScoreGameSession userScoreGameSession) {
        return Double.compare(this.score, userScoreGameSession.getScore());
    }
}
