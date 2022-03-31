package com.example.qrcodeteam30.controllerclass;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.UserInformation;

import java.security.NoSuchAlgorithmException;

/**
 * Calculate the score
 */
public class CalculateScoreController {
    /**
     * Calculate the score
     * @param str
     * @return a score from the string
     */
    public static double calculateScore(String str) {
        String hashStr = null;
        try {
            hashStr = MyCryptographyController.hashSHA256(str);
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

    public static double calculateTotalScore(UserInformation userInformation, Game game) {
        double result = 0;
        for (var qrCode: userInformation.getQrCodeList()) {
            if (qrCode.getGameName().equals(game.getGameName()))
                result += qrCode.getScore();
        }
        return result;
    }
}
