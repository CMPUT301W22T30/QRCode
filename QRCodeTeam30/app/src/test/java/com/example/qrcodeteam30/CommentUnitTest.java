package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;
import com.example.qrcodeteam30.modelclass.Comment;
import org.junit.Test;

public class CommentUnitTest {
    @Test
    public void getterSetterUsernameTest1() {
        Comment cmt = new Comment();
        cmt.setUsername("user1");
        assertEquals("user1", cmt.getUsername());
    }

    @Test
    public void getterSetterUsernameTest2() {
        Comment cmt = new Comment();
        cmt.setUsername("admin");
        assertEquals("admin", cmt.getUsername());
    }

    @Test
    public void getterSetterUsernameTest3() {
        Comment cmt = new Comment();
        cmt.setUsername("ronaldo");
        assertEquals("ronaldo", cmt.getUsername());
    }

    @Test
    public void getterSetterAuthorTest1() {
        Comment cmt = new Comment();
        cmt.setAuthor("razer");
        assertEquals("razer", cmt.getAuthor());
    }

    @Test
    public void getterSetterAuthorTest2() {
        Comment cmt = new Comment();
        cmt.setAuthor("alberta");
        assertEquals("alberta", cmt.getAuthor());
    }

    @Test
    public void getterSetterAuthorTest3() {
        Comment cmt = new Comment();
        cmt.setAuthor("toronto");
        assertEquals("toronto", cmt.getAuthor());
    }

    @Test
    public void getterSetterContentTest1() {
        Comment cmt = new Comment();
        cmt.setContent("Nice QR Code");
        assertEquals("Nice QR Code", cmt.getContent());
    }

    @Test
    public void getterSetterContentTest2() {
        Comment cmt = new Comment();
        cmt.setContent("High point");
        assertEquals("High point", cmt.getContent());
    }

    @Test
    public void getterSetterContentTest3() {
        Comment cmt = new Comment();
        cmt.setContent("I want to scan this QR Code");
        assertEquals("I want to scan this QR Code", cmt.getContent());
    }

    @Test
    public void getterSetterDateTest1() {
        Comment cmt = new Comment();
        cmt.setDate("Tue Dec  2 01:03:00 PST 2019");
        assertEquals("Tue Dec  2 01:03:00 PST 2019", cmt.getDate());
    }

    @Test
    public void getterSetterDateTest2() {
        Comment cmt = new Comment();
        cmt.setDate("Sun Feb  2 10:01:00 MT 2020");
        assertEquals("Sun Feb  2 10:01:00 MT 2020", cmt.getDate());
    }

    @Test
    public void getterSetterDateTest3() {
        Comment cmt = new Comment();
        cmt.setDate("Sun Aug 23 09:05:00 PDT 2001");
        assertEquals("Sun Aug 23 09:05:00 PDT 2001", cmt.getDate());
    }
}
