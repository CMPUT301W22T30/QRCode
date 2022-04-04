package com.example.qrcodeteam30.modelclass;

import java.util.ArrayList;

/**
 * This model class represents the basic information of a user
 * Username, password, firstName, lastName, his/her total score, and the list of QR Code
 */
public class UserInformation {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private ArrayList<QRCode> qrCodeList;

    /**
     * Empty Constructor
     */
    public UserInformation() {
    }

    /**
     * Constructor
     * @param username username
     * @param password hashed password
     * @param firstName first name
     * @param lastName last name
     */
    public UserInformation(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        qrCodeList = new ArrayList<>();
    }

    /**
     * get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username username (in String)
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get password (hashed)
     * @return hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set hashed password
     * @param password hashed password (in String)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set first name
     * @param firstName first name (in String)
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
     * @param lastName last name (in String)
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get list of qr codes
     * @return list of qr codes
     */
    public ArrayList<QRCode> getQrCodeList() {
        return qrCodeList;
    }

    /**
     * set list of qr codes
     * @param qrCodeList list of all barcodes
     */
    public void setQrCodeList(ArrayList<QRCode> qrCodeList) {
        this.qrCodeList = qrCodeList;
    }
}
