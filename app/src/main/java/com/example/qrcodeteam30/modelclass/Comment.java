
package com.example.qrcodeteam30.modelclass;

import java.io.Serializable;
import java.util.Date;

/**
 * This model class represents a comment to a QR code
 * Including date of posted, content, the author, and
 * the username of the owner of the QRCode that Comment belongs
 */
public class Comment implements Serializable {
    String username;  // username of the owner of the QRCode that Comment belongs
    String author;  // author of the comment
    String content;
    String date;

    /**
     * Empty Constructor
     */
    public Comment() {
    }

    /**
     * Constructor (date will be current)
     * @param username username of the owner of the barcode the parameter author comment to
     * @param author author of the comment
     * @param content content of the comment
     */
    public Comment(String username, String author, String content) {
        this.username = username;
        this.author = author;
        this.content = content;
        this.date = (new Date()).toString();
    }

    /**
     * Constructor
     * @param username username of the owner of the barcode the parameter author comment to
     * @param author author of the comment
     * @param content content of the comment
     * @param date date of creation/comment
     */
    public Comment(String username, String author, String content, String date) {
        this.username = username;
        this.author = author;
        this.content = content;
        this.date = date;
    }

    /**
     * get username of the owner of the barcode the parameter author comment to
     * @return username (in String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username of the owner of the barcode the parameter author comment to
     * @param username username (in String)
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get author of the comment
     * @return author (in String)
     */
    public String getAuthor() {
        return author;
    }

    /**
     * set author of the comment
     * @param author author (in String)
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * get content of the comment
     * @return content (in String)
     */
    public String getContent() {
        return content;
    }

    /**
     * set content
     * @param content content of the comment (in String)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * get date of creation
     * @return date (in String)
     */
    public String getDate() {
        return date;
    }

    /**
     * set date of creation
     * @param date date (in String)
     */
    public void setDate(String date) {
        this.date = date;
    }


}
