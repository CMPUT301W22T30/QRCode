package com.example.qrcodeteam30.modelclass;

import com.example.qrcodeteam30.controllerclass.CalculateScore;
import com.example.qrcodeteam30.controllerclass.MyCryptography;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    private String photoReference;

    public boolean isRecordPhoto() {
        return recordPhoto;
    }

    public void setRecordPhoto(boolean recordPhoto) {
        this.recordPhoto = recordPhoto;
    }

    private boolean recordPhoto;

    public QRCode() {
    }

    public QRCode(String qrCodeContent, double latitude, double longitude,
                  String username, String format, String commentListReference,
                  boolean recordLocation, boolean recordPhoto, String photoReference) {
        this.qrCodeContent = MyCryptography.encrypt(qrCodeContent);
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = CalculateScore.calculateScore(qrCodeContent);
        this.date = (new Date()).toString();
        this.username = username;
        this.format = format;
        this.commentListReference = commentListReference;
        this.recordLocation = recordLocation;
        this.recordPhoto = recordPhoto;
        this.photoReference = photoReference;
    }

    public String getQrCodeContent() {
        return qrCodeContent;
    }

    public void setQrCodeContent(String qrCodeContent) {
        this.qrCodeContent = qrCodeContent;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCommentListReference() {
        return commentListReference;
    }

    public void setCommentListReference(String commentListReference) {
        this.commentListReference = commentListReference;
    }

    public boolean isRecordLocation() {
        return recordLocation;
    }

    public void setRecordLocation(boolean recordLocation) {
        this.recordLocation = recordLocation;
    }

}
