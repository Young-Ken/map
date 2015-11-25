package com.snail.gis.ztest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snail.gis.R;
import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.LineString;
import com.snail.gis.geometry.Polygon;
import com.snail.gis.lgorithm.cg.CGPointInRing;
import com.snail.gis.ztest.view.DrawView;

import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/25
 */
public class PointInRing extends Activity
{
    Context context = null;
    DrawView drawView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_in_ring);

        context= this.getApplicationContext();

        final LinearLayout layout=(LinearLayout) findViewById(R.id.root);
        drawView =new DrawView(this);
        drawView.setMinimumHeight(500);
        drawView.setMinimumWidth(300);
        //通知view组件重绘
        drawView.init();
        drawView.invalidate();
        layout.addView(drawView);


        Button drawPoint = (Button) findViewById(R.id.draw_point);

        drawPoint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawView.drawState = 1;
                drawView.point = new Coordinate();
            }
        });


        final Button drawLine = (Button) findViewById(R.id.draw_line);
        drawLine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawView.drawState = 2;
                drawView.lineString = new LineString();
            }
        });

        Button drawPolygon = (Button) findViewById(R.id.draw_polygon);
        drawPolygon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawView.drawState = 3;
                drawView. polygon = new Polygon();
            }
        });

        Button endButton = (Button) findViewById(R.id.draw_end);
        endButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (drawView.polygon != null)
                {
                    drawView.polygon.getExteriorRing().getPointArray().add(drawView.polygon.getExteriorRing().getPointArray().get(0));
                }

                drawView.invalidate();
            }
        });

        Button runButton = (Button) findViewById(R.id.run);
        runButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                List<Coordinate> list =  drawView.polygon.getExteriorRing().getPointArray();
                Coordinate[] cc = list.toArray(new Coordinate[list.size()]);


                //Coordinate[] cc = (Coordinate[]) drawView.polygon.getExteriorRing().getPointArray().toArray();

                int s = CGPointInRing.locationPointInRing(drawView.point, cc);
                Toast.makeText(context, s + "", Toast.LENGTH_LONG).show();
            }
        });

        Button clearButton = (Button) findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawView.point = null;
                drawView.lineString = null;
                drawView.polygon = null;
                drawView.invalidate();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
