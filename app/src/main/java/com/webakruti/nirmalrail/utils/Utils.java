package com.webakruti.nirmalrail.utils;

import android.content.Context;

public class Utils {
    public static int pixelToDp(Context context, int pixel) {
        float density = context.getResources().getDisplayMetrics().density;
        float dp = pixel / density;
        return (int) dp;
    }

    public static int DpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        float px = dp * density;
        return (int) px;
    }

    public static String imagePath(Context context) {
        // internal file path
        String path = context.getFilesDir() + "image_" + System.currentTimeMillis() + ".jpg";
        return path;
    }

}
