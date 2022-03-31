package com.example.qrcodeteam30.modelclass;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.controllerclass.MyCryptographyController;

import java.io.Serializable;
import java.util.Date;

/**
 * This model class represents a QR code and its properties
 * Including username that the QR code belongs to, the content, the location, the date of taken
 * The score, the reference to the list of comment on Firestore, the format (QR code/barcodes/...),
 * the reference to the photo on Firestore
 */
public class QRCode implements Serializable {
    private String username;
    private String qrCodeContent;
    private double latitude;
    private double longitude;
    private String date;
    private double score;
    private String commentListReference;
    private String format;
    private boolean recordLocation;
    private String gameName;
    private String gameOwner;
    private String photoReference;
    private boolean recordPhoto;

    /**
     * Empty constructor
     */
    public QRCode() {
    }

    /**
     * Constructor
     * @param qrCodeContent
     * @param latitude
     * @param longitude
     * @param username
     * @param format
     * @param commentListReference
     * @param recordLocation
     * @param recordPhoto
     * @param photoReference
     * @param gameName
     * @param gameOwner
     */
    public QRCode(String qrCodeContent, double latitude, double longitude,
                  String username, String format, String commentListReference,
                  boolean recordLocation, boolean recordPhoto, String photoReference,
                  String gameName, String gameOwner) {
        this.qrCodeContent = MyCryptographyController.encrypt(qrCodeContent);
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = CalculateScoreController.calculateScore(qrCodeContent);
        this.date = (new Date()).toString();
        this.username = username;
        this.format = format;
        this.commentListReference = commentListReference;
        this.recordLocation = recordLocation;
        this.recordPhoto = recordPhoto;
        this.photoReference = photoReference;
        this.gameName = gameName;
        this.gameOwner = gameOwner;
    }

    /**
     * get content
     * @return the content
     */
    public String getQrCodeContent() {
        return qrCodeContent;
    }

    /**
     * set content
     * @param qrCodeContent
     */
    public void setQrCodeContent(String qrCodeContent) {
        this.qrCodeContent = qrCodeContent;
    }

    /**
     * get Latitude
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set latitude
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * get longitude
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * set longitude
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * get date
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * set date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * get score
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * set score
     * @param score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * get username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get format
     * @return return the format of QR code in String
     */
    public String getFormat() {
        return format;
    }

    /**
     * set format
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * get reference to comment list on Firestore
     * @return get reference to comment list on Firestore
     */
    public String getCommentListReference() {
        return commentListReference;
    }

    /**
     * set reference to comment list on Firestore
     * @param commentListReference
     */
    public void setCommentListReference(String commentListReference) {
        this.commentListReference = commentListReference;
    }

    /**
     * Check if record location is true or false
     * @return true or false depends on the above criteria
     */
    public boolean isRecordLocation() {
        return recordLocation;
    }

    /**
     * set record location
     * @param recordLocation
     */
    public void setRecordLocation(boolean recordLocation) {
        this.recordLocation = recordLocation;
    }

    /**
     * Get photoReference
     * @return
     */
    public String getPhotoReference() {
        return photoReference;
    }

    /**
     * Set photoReference
     * @param photoReference
     */
    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    /**
     * Check if recordPhoto is true or false
     * @return
     */
    public boolean isRecordPhoto() {
        return recordPhoto;
    }

    /**
     * Set recordPhoto
     * @param recordPhoto
     */
    public void setRecordPhoto(boolean recordPhoto) {
        this.recordPhoto = recordPhoto;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameOwner() {
        return gameOwner;
    }

    public void setGameOwner(String gameOwner) {
        this.gameOwner = gameOwner;
    }

}
