package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;

import org.junit.Test;

import java.util.ArrayList;

public class UserInformationUnitTest {
    @Test
    public void getterAndSetterUsernameTest1() {
        UserInformation user = new UserInformation();
        user.setUsername("Denver");
        assertEquals("Denver", user.getUsername());
    }

    @Test
    public void getterAndSetterUsernameTest2() {
        UserInformation user = new UserInformation();
        user.setUsername("Hanoi");
        assertEquals("Hanoi", user.getUsername());
    }

    @Test
    public void getterAndSetterUsernameTest3() {
        UserInformation user = new UserInformation();
        user.setUsername("Seoul");
        assertEquals("Seoul", user.getUsername());
    }

    @Test
    public void getterAndSetterPasswordTest1() {
        UserInformation user = new UserInformation();
        user.setPassword("123456789");
        assertEquals("123456789", user.getPassword());
    }

    @Test
    public void getterAndSetterPasswordTest2() {
        UserInformation user = new UserInformation();
        user.setPassword("zxcvbnm");
        assertEquals("zxcvbnm", user.getPassword());
    }

    @Test
    public void getterAndSetterPasswordTest3() {
        UserInformation user = new UserInformation();
        user.setPassword("azsxdcfvgbhnjm");
        assertEquals("azsxdcfvgbhnjm", user.getPassword());
    }

    @Test
    public void getterAndSetterFirstNameTest1() {
        UserInformation user = new UserInformation();
        user.setFirstName("Chris");
        assertEquals("Chris", user.getFirstName());
    }

    @Test
    public void getterAndSetterFirstNameTest2() {
        UserInformation user = new UserInformation();
        user.setFirstName("Harry");
        assertEquals("Harry", user.getFirstName());
    }

    @Test
    public void getterAndSetterFirstNameTest3() {
        UserInformation user = new UserInformation();
        user.setFirstName("Son");
        assertEquals("Son", user.getFirstName());
    }

    @Test
    public void getterAndSetterLastNameTest1() {
        UserInformation user = new UserInformation();
        user.setLastName("Duong");
        assertEquals("Duong", user.getLastName());
    }

    @Test
    public void getterAndSetterLastNameTest2() {
        UserInformation user = new UserInformation();
        user.setLastName("Hay");
        assertEquals("Hay", user.getLastName());
    }

    @Test
    public void getterAndSetterLastNameTest3() {
        UserInformation user = new UserInformation();
        user.setLastName("Maguire");
        assertEquals("Maguire", user.getLastName());
    }

    @Test
    public void getterAndSetterQRCodeListTest1() {
        ArrayList<QRCode> listOfQRCode = new ArrayList<>();
        listOfQRCode.add(new QRCode("AAA4710247000478", 53.5190187, -113.4872028, "newuser", "EAN_13", "Comment/zrg4rSgjg2atVs3VFsu7/", true, true, "Photo/IAZcnsDAEmEmSd2c4oue/", new Game()));
        listOfQRCode.add(new QRCode("AAA8936024920647", 0, 0, "Chubbyson", "EAN_13", "Comment/jKrHVSpY3fvbPz0qZyAk/", false, true, "Photo/uWyteQZPg3L8w3uDngJt/", new Game()));
        listOfQRCode.add(new QRCode("AAAX001FBV5YT", 53.516341648064554, -113.50690981373191, "dat", "CODE_128", "Comment/ljXcTqdtiybuMXWyZpL6/", true, true, "Photo/chAeV4tFjBksFTHJOuvZ/", new Game()));
        UserInformation user = new UserInformation();
        user.setQrCodeList(listOfQRCode);
        assertEquals(listOfQRCode, user.getQrCodeList());
    }

    @Test
    public void getterAndSetterQRCodeListTest2() {
        ArrayList<QRCode> listOfQRCode = new ArrayList<>();
        listOfQRCode.add(new QRCode("AAA9028359385093", 465.47, -14.376636, "user", "EAN_13", "Comment/zrg4rSgjg2atVs3VFsu7/", true, false, "Photo/IAZcnsDAEmEmSd2c4oue/", new Game()));
        listOfQRCode.add(new QRCode("AAA2385023985022", 100, 100, "abc", "EAN_13", "Comment/uFqHVSpY3fvbPz0qZyAk/", false, true, "Photo/uWyteQZPg6L1w3uDngJt/", new Game()));
        listOfQRCode.add(new QRCode("AAAX454XDI8PL", 3525238, -2342742, "qwe", "CODE_128", "Comment/ljXcTqdtiybuKOPyZpL6/", true, true, "Photo/chAeV4tFjBksQWEJOuvZ/", new Game()));
        UserInformation user = new UserInformation();
        user.setQrCodeList(listOfQRCode);
        assertEquals(listOfQRCode, user.getQrCodeList());
    }

    @Test
    public void getterAndSetterQRCodeListTest3() {
        ArrayList<QRCode> listOfQRCode = new ArrayList<>();
        listOfQRCode.add(new QRCode("AAA7890247123478", 11.111, -23.345, "olduser", "EAN_13", "Comment/abc4rSqwe2atVs3VFsu7/", false, false, "Photo/IAZcnsDAEmEmSd2c4tyu/", new Game()));
        listOfQRCode.add(new QRCode("AAA8936024123456", 33, 11, "user1", "EAN_13", "Comment/jKrHVSpY3bnhPz0qZyAk/", false, true, "Photo/oPfnmQZPg3L8w3eMkgJt/", new Game()));
        listOfQRCode.add(new QRCode("AAAX1234FBV5RQ", 4, -7, "user2", "CODE_128", "Comment/ljXcTqdtyuioMXWyZpG9/", false, false, "Photo/afAeV4tFjBksFTHJOupF/", new Game()));
        UserInformation user = new UserInformation();
        user.setQrCodeList(listOfQRCode);
        assertEquals(listOfQRCode, user.getQrCodeList());
    }
}
