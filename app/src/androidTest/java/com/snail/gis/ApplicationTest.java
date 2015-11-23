package com.snail.gis;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
{
    public ApplicationTest()
    {
        super(Application.class);

        Envelope envelope = new Envelope(new Coordinate(0,0), new Coordinate(100,100));

        Envelope envelope1 = new Envelope(new Coordinate(200, 200), new Coordinate(400,400));

        boolean b  = envelope.intersects(envelope1);

        Log.e("e",b+"");

    }
}