package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;

import com.example.qrcodeteam30.modelclass.UserInformation;

import org.junit.Test;

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
    public void getterSetterScoreTest1() {
        UserInformation user = new UserInformation();
        user.setScore(20.1256);
        assertEquals(20.1256, user.getScore(), 0.00001);
    }

    @Test
    public void getterSetterScoreTest2() {
        UserInformation user = new UserInformation();
        user.setScore(56.897);
        assertEquals(56.897, user.getScore(), 0.00001);
    }

    @Test
    public void getterSetterScoreTest3() {
        UserInformation user = new UserInformation();
        user.setScore(1234.789);
        assertEquals(1234.789, user.getScore(), 0.00001);
    }
}
