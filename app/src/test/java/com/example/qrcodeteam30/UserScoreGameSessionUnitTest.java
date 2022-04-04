package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserScoreGameSession ;
import org.junit.Test;

import java.util.ArrayList;

public class UserScoreGameSessionUnitTest {
    @Test
    public void GetterAndSetterUsernameTest1() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setUsername("t4t4a");
        assertEquals("t4t4a", userScoreGameSession.getUsername());
    }

    @Test
    public void GetterAndSetterUsernameTest2() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setUsername("nickname");
        assertEquals("nickname", userScoreGameSession.getUsername());
    }

    @Test
    public void GetterAndSetterUsernameTest3() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setUsername("androidbot");
        assertEquals("androidbot", userScoreGameSession.getUsername());
    }

    @Test
    public void GetterAndSetterPasswordTest1() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setPassword("12345678");
        assertEquals("12345678", userScoreGameSession.getPassword());
    }

    @Test
    public void GetterAndSetterPasswordTest2() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setPassword("abcd1234");
        assertEquals("abcd1234", userScoreGameSession.getPassword());
    }

    @Test
    public void GetterAndSetterPasswordTest3() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setPassword("qwertyuiop");
        assertEquals("qwertyuiop", userScoreGameSession.getPassword());
    }

    @Test
    public void GetterAndSetterFirstNameTest1() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setFirstName("John");
        assertEquals("John", userScoreGameSession.getFirstName());
    }

    @Test
    public void GetterAndSetterFirstNameTest2() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setFirstName("David");
        assertEquals("David", userScoreGameSession.getFirstName());
    }

    @Test
    public void GetterAndSetterFirstNameTest3() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setFirstName("Luke");
        assertEquals("Luke", userScoreGameSession.getFirstName());
    }

    @Test
    public void GetterAndSetterLastNameTest1() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setLastName("Shaw");
        assertEquals("Shaw", userScoreGameSession.getLastName());
    }

    @Test
    public void GetterAndSetterLastNameTest2() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setLastName("Neymar");
        assertEquals("Neymar", userScoreGameSession.getLastName());
    }

    @Test
    public void GetterAndSetterLastNameTest3() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setLastName("Maguire");
        assertEquals("Maguire", userScoreGameSession.getLastName());
    }

    @Test
    public void GetterAndSetterScoreTest1() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setScore(102.345);
        assertEquals(102.345, userScoreGameSession.getScore(), 0.00001);
    }

    @Test
    public void GetterAndSetterScoreTest2() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setScore(2837.463);
        assertEquals(2837.463, userScoreGameSession.getScore(), 0.00001);
    }

    @Test
    public void GetterAndSetterScoreTest3() {
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setScore(29472.573);
        assertEquals(29472.573, userScoreGameSession.getScore(), 0.00001);
    }

    @Test
    public void getterAndSetterQRCodeArrayListTest1() {
        ArrayList<QRCode> listOfQRCode = new ArrayList<>();
        listOfQRCode.add(new QRCode("AAA4710247000478", 53.5190187, -113.4872028, "newuser", "EAN_13", "Comment/zrg4rSgjg2atVs3VFsu7/", true, true, "Photo/IAZcnsDAEmEmSd2c4oue/", new Game()));
        listOfQRCode.add(new QRCode("AAA8936024920647", 0, 0, "Chubbyson", "EAN_13", "Comment/jKrHVSpY3fvbPz0qZyAk/", false, true, "Photo/uWyteQZPg3L8w3uDngJt/", new Game()));
        listOfQRCode.add(new QRCode("AAAX001FBV5YT", 53.516341648064554, -113.50690981373191, "dat", "CODE_128", "Comment/ljXcTqdtiybuMXWyZpL6/", true, true, "Photo/chAeV4tFjBksFTHJOuvZ/", new Game()));
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setQrCodeArrayList(listOfQRCode);
        assertEquals(listOfQRCode, userScoreGameSession.getQrCodeArrayList());
    }

    @Test
    public void getterAndSetterQRCodeArrayListTest2() {
        ArrayList<QRCode> listOfQRCode = new ArrayList<>();
        listOfQRCode.add(new QRCode("AAA9028359385093", 465.47, -14.376636, "user", "EAN_13", "Comment/zrg4rSgjg2atVs3VFsu7/", true, false, "Photo/IAZcnsDAEmEmSd2c4oue/", new Game()));
        listOfQRCode.add(new QRCode("AAA2385023985022", 100, 100, "abc", "EAN_13", "Comment/uFqHVSpY3fvbPz0qZyAk/", false, true, "Photo/uWyteQZPg6L1w3uDngJt/", new Game()));
        listOfQRCode.add(new QRCode("AAAX454XDI8PL", 3525238, -2342742, "qwe", "CODE_128", "Comment/ljXcTqdtiybuKOPyZpL6/", true, true, "Photo/chAeV4tFjBksQWEJOuvZ/", new Game()));
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setQrCodeArrayList(listOfQRCode);
        assertEquals(listOfQRCode, userScoreGameSession.getQrCodeArrayList());
    }

    @Test
    public void getterAndSetterQRCodeArrayListTest3() {
        ArrayList<QRCode> listOfQRCode = new ArrayList<>();
        listOfQRCode.add(new QRCode("AAA7890247123478", 11.111, -23.345, "olduser", "EAN_13", "Comment/abc4rSqwe2atVs3VFsu7/", false, false, "Photo/IAZcnsDAEmEmSd2c4tyu/", new Game()));
        listOfQRCode.add(new QRCode("AAA8936024123456", 33, 11, "user1", "EAN_13", "Comment/jKrHVSpY3bnhPz0qZyAk/", false, true, "Photo/oPfnmQZPg3L8w3eMkgJt/", new Game()));
        listOfQRCode.add(new QRCode("AAAX1234FBV5RQ", 4, -7, "user2", "CODE_128", "Comment/ljXcTqdtyuioMXWyZpG9/", false, false, "Photo/afAeV4tFjBksFTHJOupF/", new Game()));
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession();
        userScoreGameSession.setQrCodeArrayList(listOfQRCode);
        assertEquals(listOfQRCode, userScoreGameSession.getQrCodeArrayList());
    }
}
