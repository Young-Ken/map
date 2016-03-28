package com.caobugs.gis;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.caobugs.gis.geometry.Point;
import com.caobugs.gis.geometry.primary.Envelope;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
{
    public ApplicationTest()
    {
        super(Application.class);

        Envelope envelope = new Envelope(new Point(0,0), new Point(100,100));

        Envelope envelope1 = new Envelope(new Point(200, 200), new Point(400,400));

        boolean b  = envelope.intersects(envelope1);

        Log.e("e",b+"");

    }
}