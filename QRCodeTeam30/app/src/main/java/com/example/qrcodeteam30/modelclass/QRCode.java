package com.example.qrcodeteam30.modelclass;

import com.example.qrcodeteam30.MyCryptography;

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
        this.score = calculateScore(qrCodeContent);
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

    public static double calculateScore(String str) {
        String hashStr = null;
        try {
            hashStr = MyCryptography.hashSHA256(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return -1;
        }
        double score = 0.0f;

        int i = 0;
        while (i < hashStr.length()) {
            char ch = hashStr.charAt(i);
            int j;
            int numRepetitionInARow = 1;
            for (j = i + 1; j < hashStr.length(); j++) {
                if (hashStr.charAt(j) == ch) {
                    numRepetitionInARow++;
                } else {
                    break;
                }
            }

            switch (ch) {
                case '0': score += Math.pow(20, numRepetitionInARow - 1); break;
                case '1': score += Math.pow(1, numRepetitionInARow - 1); break;
                case '2': score += Math.pow(2, numRepetitionInARow - 1); break;
                case '3': score += Math.pow(3, numRepetitionInARow - 1); break;
                case '4': score += Math.pow(4, numRepetitionInARow - 1); break;
                case '5': score += Math.pow(5, numRepetitionInARow - 1); break;
                case '6': score += Math.pow(6, numRepetitionInARow - 1); break;
                case '7': score += Math.pow(7, numRepetitionInARow - 1); break;
                case '8': score += Math.pow(8, numRepetitionInARow - 1); break;
                case '9': score += Math.pow(9, numRepetitionInARow - 1); break;
                case 'a': score += Math.pow(10, numRepetitionInARow - 1); break;
                case 'b': score += Math.pow(11, numRepetitionInARow - 1); break;
                case 'c': score += Math.pow(12, numRepetitionInARow - 1); break;
                case 'd': score += Math.pow(13, numRepetitionInARow - 1); break;
                case 'e': score += Math.pow(14, numRepetitionInARow - 1); break;
                case 'f': score += Math.pow(15, numRepetitionInARow - 1); break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ch);
            }
            if (numRepetitionInARow == 1) {
                score--;
            }
            i = j;
        }

        return score;
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
