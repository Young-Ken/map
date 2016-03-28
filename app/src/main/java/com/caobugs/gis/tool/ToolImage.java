package com.caobugs.gis.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/9/10
 */
public class ToolImage
{
    /**
     * 把Drawable格式转换成Bitmap格式
     * @param drawable draw格式的图片
     * @return Bitmap形式的图片
     */
    public static Bitmap drawableToBitmap (Drawable drawable)
    {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
            {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
        {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else
        {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap → byte[]
     * @param bitmap Bitmap
     * @return byte[]
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    /**
     * 把图片设置上红色边框
     * @param bitmap bitmap 格式图片
     * @return Bitmap
     */
    public static Bitmap setFrameBitmap(Bitmap bitmap)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        double lineStrokeWidth = -1;
        final double lineWidth = w * 0.05;
        final double lineHeight = h * 0.05;
        lineStrokeWidth = (lineWidth > lineHeight ? lineWidth : lineHeight);

        int lineStrokeWidthInt = (int)lineStrokeWidth;
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(-lineStrokeWidthInt, -lineStrokeWidthInt, w+lineStrokeWidthInt, h+lineStrokeWidthInt);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint1 = new Paint();
        paint1.setColor(Color.RED);
        //paint1.setShadowLayer(lineStrokeWidthInt, lineStrokeWidthInt, lineStrokeWidthInt, color);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth((float) lineStrokeWidth);
        canvas.drawRect(0, 0, w, h, paint1);
        paint.setColor(color);
        //canvas.drawRoundRect(rectF, 10, 10, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap colorFrameBitmap(Bitmap bitmap)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        double lineStrokeWidth = -1;
        final double lineWidth = w * 0.05;
        final double lineHeight = h * 0.05;
        lineStrokeWidth = (lineWidth > lineHeight ? lineWidth : lineHeight);

        int lineStrokeWidthInt = (int)lineStrokeWidth;
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        final Rect rect = new Rect(lineStrokeWidthInt, lineStrokeWidthInt, w-lineStrokeWidthInt, h-lineStrokeWidthInt);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
