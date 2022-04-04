
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
     * @param username
     * @param author
     * @param content
     */
    public Comment(String username, String author, String content) {
        this.username = username;
        this.author = author;
        this.content = content;
        this.date = (new Date()).toString();
    }

    /**
     * Constructor
     * @param username
     * @param author
     * @param content
     * @param date
     */
    public Comment(String username, String author, String content, String date) {
        this.username = username;
        this.author = author;
        this.content = content;
        this.date = date;
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
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get author
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * set author
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * get content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * set content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * get date
     * @return date
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


}
