package com.example.qrcodeteam30.controllerclass;

import com.example.qrcodeteam30.modelclass.Game;
import com.example.qrcodeteam30.modelclass.QRCode;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.modelclass.UserScoreGameSession;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

/**
 * Handle anything related to Statistics
 */
public class StatisticsController {
    /**
     * Estimate the user's ranking within the server
     * @param value Contain all the user information of all players
     * @param game The game we want to estimate
     * @param score Current total score of the player we want to estimate
     * @param count The number of barcodes of the player we want to estimate
     * @param max The max score of the player we want to estimate
     * @return An ArrayList of Object, with:
     * At position 0, return the percentile of total score (in percentage, and in double)
     * At position 1, return the percentile of max score (in percentage, and in double)
     * At position 2, return the percentile of number of barcodes (in percentage, and in double)
     * At position 3, exact rank of total score
     * At position 4, the number of players in the server
     * At position 5, exact rank of max score
     * At position 6, the number of unique barcodes
     * At position 7, exact rank of number of barcodes
     * At position 8, the number of players in the server (Same as position 4)
     */
    public static ArrayList<Object> estimateRanking(QuerySnapshot value, Game game, double score, int count, double max) {
        int cfMax = 0;
        int fMax = 0;
        int cfCount = 0;
        int fCount = 0;
        int cfScore = 0;
        int fScore = 0;
        int numQRCodes = 0;
        int numDocs = 0;

        HashSet<String> allQRCodeHashSetString = new HashSet<>();
        ArrayList<Double> allQRCodeArr = new ArrayList<>();
        ArrayList<Double> scoreArr = new ArrayList<>();
        ArrayList<Integer> countArr = new ArrayList<>();

        for (var queryDocumentSnapshot : value) {
            UserInformation userInformation = queryDocumentSnapshot.toObject(UserInformation.class);
            UserScoreGameSession userScoreGameSession = new UserScoreGameSession(userInformation, game);

            double totalScore = CalculateScoreController.calculateTotalScore(userInformation, game);
            if (totalScore < score) {
                cfScore++;
            } else if (totalScore == score) {
                fScore++;
            }
            scoreArr.add(totalScore);

            if (userScoreGameSession.getQrCodeArrayList().size() < count) {
                cfCount++;
            } else if (userScoreGameSession.getQrCodeArrayList().size() == count) {
                fCount++;
            }
            countArr.add(userScoreGameSession.getQrCodeArrayList().size());

            for (var qrCode: userScoreGameSession.getQrCodeArrayList()) {
                if (qrCode.getScore() < max) {
                    cfMax++;
                } else if (qrCode.getScore() == max) {
                    fMax++;
                }
                numQRCodes++;

                if (!allQRCodeHashSetString.contains(qrCode.getQrCodeContent())) {
                    allQRCodeHashSetString.add(qrCode.getQrCodeContent());
                    allQRCodeArr.add(qrCode.getScore());
                }
            }

            numDocs++;
        }
        double topPercentileRankSum = 100 - (cfScore + (0.5 * fScore)) / numDocs * 100;
        double topPercentileRankMax = 100 - (cfMax + (0.5 * fMax)) / numQRCodes * 100;
        double topPercentileRankCount = 100 - (cfCount + (0.5 * fCount)) / numDocs * 100;

        allQRCodeArr.sort(Collections.reverseOrder());
        scoreArr.sort(Collections.reverseOrder());
        countArr.sort(Collections.reverseOrder());

        int rankMax = allQRCodeArr.indexOf(max);
        int rankScore = scoreArr.indexOf(score);
        int rankCount = countArr.indexOf(count);

        ArrayList<Object> result = new ArrayList<>();
        result.add(topPercentileRankSum);
        result.add(topPercentileRankMax);
        result.add(topPercentileRankCount);
        result.add(rankScore);
        result.add(scoreArr.size());
        result.add(rankMax);
        result.add(allQRCodeArr.size());
        result.add(rankCount);
        result.add(countArr.size());
        return result;
    }

    /**
     * The user's statistic within the server
     * @param value
     * @param game
     * @return A string represent the statistics
     */
    public static String userStatistic(DocumentSnapshot value, Game game) {
        ArrayList<Double> arrayList = new ArrayList<>();
        double sum = 0;
        UserInformation userInformation = value.toObject(UserInformation.class);
        UserScoreGameSession userScoreGameSession = new UserScoreGameSession(userInformation, game);
        for (QRCode qrCode: userScoreGameSession.getQrCodeArrayList()) {
            arrayList.add(qrCode.getScore());
            sum += qrCode.getScore();
        }

        var strippedSum = new BigDecimal(Double.toString(sum)).stripTrailingZeros();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(Locale.CANADA, "Number of Barcodes: %d\n", userScoreGameSession.getQrCodeArrayList().size()));
        stringBuilder.append(String.format(Locale.CANADA, "Total score: %s\n", strippedSum.toPlainString()));

        if (userScoreGameSession.getQrCodeArrayList().size() > 0) {
            var strippedAverage = new BigDecimal(Double.toString(sum / userScoreGameSession.getQrCodeArrayList().size()))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            var strippedMax = new BigDecimal(Double.toString(Collections.max(arrayList)))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            var strippedMin = new BigDecimal(Double.toString(Collections.min(arrayList)))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
            stringBuilder.append(String.format(Locale.CANADA, "Average: %s\n", strippedAverage.toPlainString()));
            stringBuilder.append(String.format(Locale.CANADA, "Max: %s\n", strippedMax.toPlainString()));
            stringBuilder.append(String.format(Locale.CANADA, "Min: %s", strippedMin.toPlainString()));
        } else {
            stringBuilder.append("Average: Not applicable\n");
            stringBuilder.append("Max: Not applicable\n");
            stringBuilder.append("Min: Not applicable");
        }

        return stringBuilder.toString();
    }
}
