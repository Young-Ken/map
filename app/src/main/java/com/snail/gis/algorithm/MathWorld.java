package com.snail.gis.algorithm;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/2/26
 */
public class MathWorld
{
    /**
     * 点到线的距离
     *
     * @param p 点
     * @param A 线段开始点
     * @param B 线段结束点
     * @return 点到线的距离
     */
    public static double distancePointSegment(Coordinate p, Coordinate A, Coordinate B)
    {
        //开始点等于结束点，也就是线是点
        if (A.x == B.x && A.y == B.y)
        {
            return p.distance(A);
        }

         /*
          * (1) r = AC dot AB
          *         ---------
          *         ||AB||^2
          *
          * r has the following meaning:
          *   r=0 P = A
          *   r=1 P = B
          *   r<0 P is on the backward extension of AB
          *   r>1 P is on the forward extension of AB
          *   0<r<1 P is interior to AB
          */

        //向量ab的长度平方
        double len2 = vectorSquare(A, B);
        double r = dot(p, A, B) / len2;

        if (r <= 0.0)
            return p.distance(A);
        if (r >= 1.0)
            return p.distance(B);
        /*
         * (2) s = (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay)
         *         -----------------------------
         *                    L^2
         *
         * Then the distance from C to P = |s|*L.
         */

        double s = cross(p, A, B) / len2;
        return Math.abs(s) * Math.sqrt(len2);
    }

    public static double distancePointLine(Coordinate p, Coordinate A, Coordinate B)
    {
    /*
     * (2) s = (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay)
     *         -----------------------------
     *                    L^2
     *
     * Then the distance from C to P = |s|*L.
     */
        double len2 = vectorSquare(A, B);
        double s = cross(p, A, B) / len2;
        return Math.abs(s) * Math.sqrt(len2);
    }

    /**
     * 线段到线段的距离
     * @param A 向量ab起点
     * @param B 向量ab终点
     * @param C 向量cd起点
     * @param D 向量cd终点
     * @return 线到线的距离
     */
    public static double distanceSegmentSegment(Coordinate A, Coordinate B, Coordinate C, Coordinate D)
    {
        // check for zero-length segments
        if (A.equals(B))
            return distancePointSegment(A, C, D);
        if (C.equals(D))
            return distancePointSegment(D, A, B);

        // AB and CD are line segments
    /*
     * from comp.graphics.algo
     *
     * Solving the above for r and s yields
     *
     *     (Ay-Cy)(Dx-Cx)-(Ax-Cx)(Dy-Cy)
     * r = ----------------------------- (eqn 1)
     *     (Bx-Ax)(Dy-Cy)-(By-Ay)(Dx-Cx)
     *
     *     (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay)
     * s = ----------------------------- (eqn 2)
     *     (Bx-Ax)(Dy-Cy)-(By-Ay)(Dx-Cx)
     *
     * Let P be the position vector of the
     * intersection point, then
     *   P=A+r(B-A) or
     *   Px=Ax+r(Bx-Ax)
     *   Py=Ay+r(By-Ay)
     * By examining the values of r & s, you can also determine some other limiting
     * conditions:
     *   If 0<=r<=1 & 0<=s<=1, intersection exists
     *      r<0 or r>1 or s<0 or s>1 line segments do not intersect
     *   If the denominator in eqn 1 is zero, AB & CD are parallel
     *   If the numerator in eqn 1 is also zero, AB & CD are collinear.
     */

        boolean noIntersection = false;
        if (!Envelope.intersects(A, B, C, D))
        {
            noIntersection = true;
        } else
        {
            double denom = (B.x - A.x) * (D.y - C.y) - (B.y - A.y) * (D.x - C.x);

            if (denom == 0)
            {
                noIntersection = true;
            } else
            {
                double r_num = (A.y - C.y) * (D.x - C.x) - (A.x - C.x) * (D.y - C.y);
                double s_num = (A.y - C.y) * (B.x - A.x) - (A.x - C.x) * (B.y - A.y);

                double s = s_num / denom;
                double r = r_num / denom;

                if ((r < 0) || (r > 1) || (s < 0) || (s > 1))
                {
                    noIntersection = true;
                }
            }
        }
        if (noIntersection)
        {
            return min(distancePointSegment(A, C, D), distancePointSegment(B, C, D), distancePointSegment(C, A, B), distancePointSegment(D, A, B));
        }
        // segments intersect
        return 0.0;
    }

    public static double min(double v1, double v2, double v3, double v4)
    {
        double min = v1;
        if (v2 < min) min = v2;
        if (v3 < min) min = v3;
        if (v4 < min) min = v4;
        return min;
    }

    /**
     * 内积，数量积 计算
     * @param p 点
     * @param A 向量开始
     * @param B 向量结束
     * @return 内积，数量积
     */
    public static double dot(Coordinate p, Coordinate A, Coordinate B)
    {
        return (p.x - A.x) * (B.x - A.x) + (p.y - A.y) * (B.y - A.y);
    }

    /**
     * 向量ab 的长度平方
     * @param A 向量开始
     * @param B 向量结束
     * @return 长度平方
     */
    public static double vectorSquare(Coordinate A, Coordinate B)
    {
        return (B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y);
    }

    /**
     * 向量积，外积、叉积
     * @param p 点
     * @param A 向量开始
     * @param B 向量结束
     * @return 量积，外积、叉积
     */
    public static double cross (Coordinate p, Coordinate A, Coordinate B)
    {
        return (A.y - p.y) * (B.x - A.x) - (A.x - p.x) * (B.y - A.y);
    }
}
