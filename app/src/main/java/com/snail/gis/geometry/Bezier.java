package com.snail.gis.geometry;

import android.util.Log;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/2/26
 */
public class Bezier
{
    public Bezier()
    {

    }

    /**
     * 创建贝塞尔曲线算法
     * @param list 原始点集合
     * @return 曲线集合
     */
    public ArrayList<Coordinate> createBezier(ArrayList<Coordinate> list)
    {
        Coordinate[] points = list.toArray(new Coordinate[list.size()]);
        return createBezier(points);
    }

    /**
     * 创建贝塞尔曲线算法
     * @param points 原始点集合
     * @return 曲线集合
     */
    public ArrayList<Coordinate> createBezier(Coordinate[] points)
    {
        if (points == null || points.length < 2)
            return new ArrayList();
        ArrayList<Coordinate> bezierPoint = new ArrayList<>();
        bezierPoint.clear();
        int n = points.length - 1; //
        int i, r;
        float u;
        for (u = 0; u <= 1; u += 0.001)
        {
            Coordinate p[] = new Coordinate[n + 1];
            for (i = 0; i <= n; i++)
            {
                p[i] = new Coordinate(points[i].x, points[i].y);
            }

            for (r = 1; r <= n; r++)
            {
                for (i = 0; i <= n - r; i++)
                {
                    p[i].x = (1 - u) * p[i].x + u * p[i + 1].x;
                    p[i].y = (1 - u) * p[i].y + u * p[i + 1].y;
                }
            }
            bezierPoint.add(p[0]);
        }
        return bezierPoint;
    }
}
