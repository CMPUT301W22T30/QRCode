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
     * @param qrCodeContent content of the barcode
     * @param latitude latitude of location
     * @param longitude longitude of location
     * @param username username of the owner of the QR code
     * @param format format of the barcode (QR_CODE or CODE_39)
     * @param commentListReference reference to comment on Firestore
     * @param recordLocation Store location or not
     * @param recordPhoto Store photo or not
     * @param photoReference reference to the photo on Firestore
     * @param game The game the QR Code belongs to
     */
    public QRCode(String qrCodeContent, double latitude, double longitude,
                  String username, String format, String commentListReference,
                  boolean recordLocation, boolean recordPhoto, String photoReference,
                  Game game) {
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
        this.gameName = game.getGameName();
        this.gameOwner = game.getOwnerUsername();
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
     * @param qrCodeContent The string we want to set the content to
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
     * @param latitude The latitude we want to set
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
     * @param longitude The longitude we want to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * get date of creation of barcode
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * set date of creation of barcode
     * @param date The string of the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * get score of the barcode
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * set score of the barcode
     * @param score The score of the barcode
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
     * @param username The string of the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get format of the barcode
     * @return return the format of QR code in String
     */
    public String getFormat() {
        return format;
    }

    /**
     * set format of the barcode
     * @param format The format of the barcode (in String)
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
     * @param commentListReference reference to comment list on Firestore (in String)
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
     * @param recordLocation Set to true or false
     */
    public void setRecordLocation(boolean recordLocation) {
        this.recordLocation = recordLocation;
    }

    /**
     * Get photoReference on Firestore
     * @return photoReference on Firestore
     */
    public String getPhotoReference() {
        return photoReference;
    }

    /**
     * Set photoReference on Firestore
     * @param photoReference photoReference on Firestore
     */
    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    /**
     * Check if recordPhoto is true or false
     * @return true if the photo is record, false otherwise
     */
    public boolean isRecordPhoto() {
        return recordPhoto;
    }

    /**
     * Set recordPhoto to true or false
     * @param recordPhoto Set to true or false
     */
    public void setRecordPhoto(boolean recordPhoto) {
        this.recordPhoto = recordPhoto;
    }

    /**
     * Get the name of the game the barcode belongs to
     * @return The name of the game in String
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Set the name of the game the barcode belongs to
     * @param gameName The name of the game the barcode belongs to
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Get the name of the game owner the barcode belongs to
     * @return the name of the game owner the barcode belongs to (in String)
     */
    public String getGameOwner() {
        return gameOwner;
    }

    /**
     * Set the name of the game owner the barcode belongs to
     * @param gameOwner the name of the game owner the barcode belongs to
     */
    public void setGameOwner(String gameOwner) {
        this.gameOwner = gameOwner;
    }

}
