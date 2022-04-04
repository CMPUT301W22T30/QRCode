package com.example.qrcodeteam30;

import static org.junit.Assert.assertEquals;

import com.example.qrcodeteam30.controllerclass.CalculateScoreController;

import org.junit.Test;

public class CalculateScoreUnitTest {
    @Test
    public void calculateScoreTest() {
        double score = CalculateScoreController.calculateScore("BFG5DGW54");
        assertEquals(19.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest2() {
        double score = CalculateScoreController.calculateScore("");
        assertEquals(27.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest3() {
        double score = CalculateScoreController.calculateScore(" ");
        assertEquals(34.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest4() {
        double score = CalculateScoreController.calculateScore("a");
        assertEquals(59.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest5() {
        double score = CalculateScoreController.calculateScore("A");
        assertEquals(80.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest6() {
        double score = CalculateScoreController.calculateScore("abcd");
        assertEquals(17.0, score, 0.00001);
    }

    @Test
    public void calculateScoreTest7() {
        double score = CalculateScoreController.calculateScore("abcD");
        assertEquals(36.0, score, 0.00001);
    }
}
