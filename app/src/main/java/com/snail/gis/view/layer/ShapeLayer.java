package com.snail.gis.view.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.data.shapefile.shp.ShapeFile;
import com.snail.gis.data.shapefile.shp.geom.RecordGeometry;
import com.snail.gis.data.shapefile.shp.geom.RecordPoint;

import java.util.List;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/19
 */
public class ShapeLayer extends BaseLayer
{

    private ShapeFile shapeFile = null;
    private Paint paint = new Paint();

    public ShapeLayer(ShapeFile shapeFile)
    {
        this.shapeFile = shapeFile;
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }


    @Override
    void recycle()
    {

    }

    @Override
    void initLayer()
    {

    }

    @Override
    void draw(Canvas canvas)
    {
        if (shapeFile.getHeader().getShapeType().isPointType())
        {
            drawPoint(canvas);
        } else if (shapeFile.getHeader().getShapeType().isLineType())
        {
            drawLine(canvas);
        }
    }

    void drawPoint(Canvas canvas)
    {
        List<RecordGeometry> list = shapeFile.getList();
        if (shapeFile.getHeader().getShapeName().toString().equals("DSP"))
        {
            Log.e("ShapeLayer11", shapeFile.getHeader().getShapeName().toString());
            for (RecordGeometry geometry : list)
            {
                RecordPoint point = (RecordPoint) geometry;
                Coordinate tempPoint = getMap().getProjection().gaussTolnlaPro(point.getX(), point.getY(), true, 120, 0, 0);
                Coordinate result = getMap().getProjection().lonLatToMercator(tempPoint.x, tempPoint.y);
                Coordinate coordinate = getMap().getProjection().toScreenPoint(result.x, result.y);
                canvas.drawCircle((float) (coordinate.x + getMap().getTileTool().loadX), (float) (coordinate.y+getMap().getTileTool().loadY), 2, paint);
            }
        }


        Coordinate point =  getMap().getMapCenter();

        point = getMap().getProjection().mercatorToLonLat(point.x, point.y);

        //Log.e("ssss",point.getX()+"  "+point.getY()+ "point");

    }

    void drawLine(Canvas canvas)
    {

    }
}
