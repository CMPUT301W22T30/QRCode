
package com.example.qrcodeteam30.modelclass;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    String username;  // username of the owner of the QRCode that Comment belongs
    String author;  // author of the comment
    String content;
    String date;

    public Comment() {
    }

    public Comment(String username, String author, String content) {
        this.username = username;
        this.author = author;
        this.content = content;
        this.date = (new Date()).toString();
    }

    public Comment(String username, String author, String content, String date) {
        this.username = username;
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
