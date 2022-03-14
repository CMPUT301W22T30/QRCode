package com.example.qrcodeteam30.controllerclass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Handling anything related to Bitmap (Photo)
 */
public class MyBitmap {
    /**
     * Method to scale the Bitmap to respect the max bytes
     *
     * @param input    the Bitmap to scale if too large
     * @param maxBytes the amount of bytes the Image may be
     * @return The scaled bitmap or the input if already valid
     */
    public static Bitmap scaleBitmap(final Bitmap input, final long maxBytes) {
        final int currentWidth = input.getWidth();
        final int currentHeight = input.getHeight();
        final int currentPixels = currentWidth * currentHeight;
        // Get the amount of max pixels:
        // 1 pixel = 4 bytes (R, G, B, A)
        final long maxPixels = maxBytes / 4; // Floored
        if (currentPixels <= maxPixels) {
            // Already correct size:
            return input;
        }
        // Scaling factor when maintaining aspect ratio is the square root since x and y have a relation:
        final double scaleFactor = Math.sqrt(maxPixels / (double) currentPixels);
        final int newWidthPx = (int) Math.floor(currentWidth * scaleFactor);
        final int newHeightPx = (int) Math.floor(currentHeight * scaleFactor);
        return Bitmap.createScaledBitmap(input, newWidthPx, newHeightPx, true);
    }

    public static String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte [] b=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }
}
