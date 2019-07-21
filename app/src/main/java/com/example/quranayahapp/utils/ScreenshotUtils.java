package com.example.quranayahapp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.View;

public class ScreenshotUtils {



    private static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private static Bitmap addWaterMarkWithText(Bitmap src, String watermark, Point location, int color, int alpha, int size, boolean underline) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }

    public static Bitmap getWaterMarkedImage(View view){

       Bitmap bitmap =  screenShot(view);


        Point point = new Point();

        point.set(bitmap.getWidth() - 350, bitmap.getHeight() - 50);


        return addWaterMarkWithText(bitmap, "QuranAyahApp", point, Color.RED, 90, 40, true);
    }
}
