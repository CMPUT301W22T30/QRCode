package com.example.qrcodeteam30.modelclass;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInformation implements Serializable, Comparable<UserInformation> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private double score;
    private ArrayList<QRCode> qrCodeList;

    public UserInformation() {
    }

    public UserInformation(String username, String password, String firstName, String lastName, double score) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
        qrCodeList = new ArrayList<>();
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

    public ArrayList<QRCode> getQrCodeList() {
        return qrCodeList;
    }

    public void setQrCodeList(ArrayList<QRCode> qrCodeList) {
        this.qrCodeList = qrCodeList;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(UserInformation userInformation) {
        return Double.compare(this.score, userInformation.getScore());
    }
}
