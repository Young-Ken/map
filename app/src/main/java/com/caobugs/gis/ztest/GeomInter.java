package com.caobugs.gis.ztest;

import android.app.Activity;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/26
 */
public class GeomInter extends Activity
{
//    Context context = null;
//    DrawGeomIntLine drawView = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.geom_inter);
//
//        context= this.getApplicationContext();
//
//        final LinearLayout layout=(LinearLayout) findViewById(R.id.root);
//
//
//        drawView =new DrawGeomIntLine(this, findViewById(android.R.id.content).getRootView());
//        drawView.setMinimumHeight(500);
//        drawView.setMinimumWidth(300);
//        //通知view组件重绘
//
//        drawView.invalidate();
//        layout.addView(drawView);
//        drawView.init();
//
//
//
//        Button runButton = (Button)  findViewById(R.id.run);
//        runButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                boolean isIn = false;
//                if (drawView.point != null)
//                {
//                    isIn = drawView.polygon.intersects(drawView.point);
//                    Toast.makeText(context, isIn + " point ", Toast.LENGTH_LONG).show();
//                } else if (drawView.lineString != null)
//                {
//                    isIn = drawView.polygon.intersects(drawView.lineString);
//                    Toast.makeText(context, isIn + " lineString ", Toast.LENGTH_LONG).show();
//                } else if (drawView.polygon != null)
//                {
//                    isIn = drawView.polygon.intersects(drawView.polygon);
//                    Toast.makeText(context, isIn + " polygon ", Toast.LENGTH_LONG).show();
//                }
//
//
//                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Button endButton = (Button)  findViewById(R.id.draw_end);
//        endButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                if (drawView.polygon != null)
//                {
//                   // drawView.polygon.getExteriorRing().getPointArray().add(polygon.getExteriorRing().getPointArray().get(0));
//                }
//
//                drawView.invalidate();
//            }
//        });
//    }
//
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
