package com.snail.gis.ztest.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.geometry.primary.Geometry;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/30
 */
public class DrawTools
{
    public static void drawEnvelope(Canvas canvas, Geometry geometry)
    {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        Envelope envelope = geometry.getEnvelopeInternal();
        canvas.drawRect((float)envelope.getMinX(), (float)envelope.getMinY(), (float)envelope.getMaxX(), (float)envelope.getMaxY(),paint);
    }


}
