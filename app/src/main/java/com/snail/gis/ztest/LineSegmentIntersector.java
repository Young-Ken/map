package com.snail.gis.ztest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snail.gis.R;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LineSegment;
import com.snail.gis.topology.SegmentIntersectSegment;
import com.snail.gis.ztest.view.Lines_in_Lines;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/30
 */
public class LineSegmentIntersector extends Activity
{
    Context context = null;
    Lines_in_Lines drawView = null;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lines_in_lines);
        this.context = getApplicationContext().getApplicationContext();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.rootlines);
        drawView = new Lines_in_Lines(this, this.findViewById(R.id.mainlines));
        drawView.setMinimumHeight(500);
        drawView.setMinimumWidth(300);
        //通知view组件重绘
        drawView.init();
        drawView.invalidate();
        layout.addView(drawView);


        Button endDrawButton = (Button) findViewById(R.id.draw_end);
        endDrawButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawView.invalidate();
            }
        });

        Button runButton = (Button) findViewById(R.id.run);
        runButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * 测试共线情况
                 */

                drawView.line1 = new LineSegment(new Coordinate(100,100), new Coordinate(200,200));
                drawView.line2 = new LineSegment(new Coordinate(150, 150), new Coordinate(160,160));
                //drawView.line1 = new LineSegment(new Coordinate(100,100), new Coordinate(300,300));
                //drawView.line2 = new LineSegment(new Coordinate(100, 0), new Coordinate(300,200));
                int isIn = -1;
                if (drawView.line1 != null && drawView.line2 != null)
                {
                    SegmentIntersectSegment lineIntersector = new SegmentIntersectSegment();
                    isIn =  lineIntersector.intersects(drawView.line1, drawView.line2);
                    Toast.makeText(context, isIn + " lineString ", Toast.LENGTH_LONG).show();
                }

               // Toast.makeText(context, "", Toast.LENGTH_LONG).show();
            }
        });

    }
}
