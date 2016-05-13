package com.caobugs.gis.ztest.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.geometry.primary.Geometry;
import com.caobugs.gis.view.map.util.Projection;

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
