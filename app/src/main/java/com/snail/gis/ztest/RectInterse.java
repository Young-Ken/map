package com.snail.gis.ztest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snail.gis.R;
import com.snail.gis.ztest.view.RectInView;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/12/1
 */
public class RectInterse extends Activity
{
//    Context context = null;
//    RectInView drawView = null;
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.geom_inter);
//        this.context = getApplicationContext().getApplicationContext();
//        final LinearLayout layout = (LinearLayout) findViewById(R.id.root);
//        drawView = new RectInView(this, this.findViewById(R.id.main));
//        drawView.setMinimumHeight(500);
//        drawView.setMinimumWidth(300);
//        //通知view组件重绘
//        drawView.init();
//        drawView.invalidate();
//        layout.addView(drawView);
//
//        Button runButton = (Button) findViewById(R.id.run);
//        runButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                /**
//                 * 测试共线情况
//                 */
//                boolean b = false;
//                if (drawView.point != null)
//                {
//                    b = drawView.polygon.intersects(drawView.point);
//                    Toast.makeText(context, b + "  point ", Toast.LENGTH_LONG).show();
//                }
//                if (drawView.lineString != null)
//                {
//                    b = drawView.polygon.intersects(drawView.lineString);
//                    Toast.makeText(context, b + "  lineString ", Toast.LENGTH_LONG).show();
//                }
//                if (drawView.envelope != null)
//                {
//                    b = drawView.envelope.intersects(drawView.polygon.getEnvelope());
//                    Toast.makeText(context, b + "  envelope ", Toast.LENGTH_LONG).show();
//                }
//
//                if (drawView.polygon1 != null)
//                {
//                    b = drawView.polygon.intersects(drawView.polygon1);
//                    Toast.makeText(context, b + "  polygon ", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        Button clearButton = (Button) findViewById(R.id.clear);
//        clearButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                drawView.point = null;
//                drawView.lineString = null;
//                drawView.envelope = null;
//                drawView.drawState = 0;
//                drawView.polygon1 = null;
//                drawView.invalidate();
//            }
//        });
//
//        Button drawEnd = (Button) findViewById(R.id.draw_end);
//        drawEnd.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                if (drawView.polygon1 != null)
//                {
//                    drawView.polygon1.getExteriorRing().getLines().add(drawView.polygon1.getExteriorRing().getPoint(0));
//                    drawView.invalidate();
//                }
//            }
//        });
//    }
//

}
