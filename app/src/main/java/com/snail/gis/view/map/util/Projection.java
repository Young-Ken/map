package com.snail.gis.view.map.util;

import android.util.Log;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.tile.CoordinateSystemManager;
import com.snail.gis.tool.TAG;
import com.snail.gis.view.map.BaseMap;
import com.snail.gis.tile.TileInfo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/29
 */
public class Projection
{
    private BaseMap map = null;

    private static double piExcept360 = Math.PI / 360;
    private static double piExcept180 = Math.PI / 180;

    private static double earthRExcept180 = 20037508.342787 / 180;
    private TileInfo tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();

    private double originPointX = tileInfo.getOriginPoint().x;
    private double originPointY = tileInfo.getOriginPoint().y;

    /**
     * 单例模式
     */
    private volatile static Projection instance = null;

    private Projection(BaseMap map)
    {
        this.map = map;
        this.tileInfo = CoordinateSystemManager.getInstance().getCoordinateSystem().getTileInfo();
    }

    /**
     * 单例模式返回ShapeFileManager实例
     *
     * @return ShapeFileManager实例
     */
    public static Projection getInstance(BaseMap map)
    {
        if (null == instance)
        {
            synchronized (Projection.class)
            {
                if (null == instance)
                {
                    instance = new Projection(map);
                }
            }
        }
        return instance;
    }


    /**
     * 把屏幕坐标转换成地理坐标
     *
     * @param screenX x
     * @param screenY y
     * @return 点
     */
    public Coordinate toMapPoint(float screenX, float screenY)
    {
        double x = ((map.getEnvelope().getMinX()) / map.getResolution() + screenX) * map.getResolution();
        double y = ((map.getEnvelope().getMinY()) / map.getResolution() + screenY) * map.getResolution();
        return new Coordinate(x, y);
    }

    public Coordinate earthTransformaImage(double x, double y)
    {
        return new Coordinate(Math.abs(tileInfo.getOriginPoint().x -x), Math.abs(tileInfo.getOriginPoint().y - y));
    }
    public Coordinate earthTransformaImage(Coordinate coordinate)
    {
        double x = Math.abs(originPointX - coordinate.x);
        double y = Math.abs(originPointY - coordinate.y);
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }

    /**
     * 把地理坐标转换成屏幕坐标
     *
     * @param pointX 坐标点
     * @param pointY 坐标点
     * @return 点
     */
    public Coordinate toScreenPoint(double pointX, double pointY)
    {
        double x = (pointX - map.getEnvelope().getMinX()) / map.getResolution();
        double y = (pointY - map.getEnvelope().getMinY()) / map.getResolution();
        return new Coordinate(x, y);
    }

    public Coordinate toScreenPoint(Coordinate coordinate)
    {
        double x = (coordinate.x - map.getEnvelope().getMinX()) / map.getResolution();
        double y = (coordinate.y - map.getEnvelope().getMinY()) / map.getResolution();
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }

    public Coordinate toScreenPoint(Coordinate coordinate, double minX, double minY, double resolution)
    {
        double x = (coordinate.x - minX) / resolution;
        double y = (coordinate.y - minY) / resolution;
        coordinate.x = x;
        coordinate.y = y;
        return coordinate;
    }


    /**
     * 将WGS84大地坐标，转换为淄博平面坐标
     * @return Point
     */
    public Coordinate GaussPrjCalculate(double longitude, double latitude)
    {
        int ZoneWide = 6;
        double _a = 6378137.0;
        double _f = 1.0 / 298.257223563;
        int ProjNo = 0;
        double longitude1, latitude1, longitude0, latitude0, X0, Y0, xval, yval;
        double e2, ee, NN, T, C, A, M, iPI;
        iPI = 0.0174532925199433; //3.1415926535898/180.0;
        //ZoneWide = 6; //6度带宽
        ProjNo = (int)(longitude / ZoneWide);
        longitude0 = ProjNo * ZoneWide + ZoneWide / 2;
        longitude0 = longitude0 * iPI;
        latitude0 = 0;
        longitude1 = longitude * iPI; //经度转换为弧度
        latitude1 = latitude * iPI; //纬度转换为弧度
        e2 = 2 * _f - _f * _f;
        ee = e2 * (1.0 - e2);
        NN = _a / Math.sqrt(1.0 - e2 * Math.sin(latitude1) * Math.sin(latitude1));
        T = Math.tan(latitude1) * Math.tan(latitude1);
        C = ee * Math.cos(latitude1) * Math.cos(latitude1);
        A = (longitude1 - longitude0) * Math.cos(latitude1);
        M = _a * ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256) * latitude1 - (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2 * e2 / 1024) * Math.sin(2 * latitude1) + (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024) * Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072) * Math.sin(6 * latitude1));
        xval = NN * (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 72 * C - 58 * ee) * A * A * A * A * A / 120);
        yval = M + NN * Math.tan(latitude1) * (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24 + (61 - 58 * T + T * T + 600 * C - 330 * ee) * A * A * A * A * A * A / 720);
        X0 = 1000000L * (ProjNo + 1) + 500000L;
        Y0 = 0;
        double X = Math.round((xval + X0) * 100) / 100.0 - X0;
        double Y = Math.round((yval + Y0) * 100) / 100.0;
        return new Coordinate(X, Y);
    }

    public void lnlatogaossProj(double ln, double lat, boolean IsThree,
                                 double CL, double x, double y) {
        ln = ln - CL;
        double num5;
        double num6;
        int num = 0;
        int num2 = 3;
        double num10 = 0.0;// a
        double num11 = 0.0;
        double num19 = 0.0174532925199433;
        num10 = 6378140.0;
        num11 = 0.0033528131778969143;
        if (IsThree) {
            num2 = 3;
        } else {
            num2 = 6;
        }

        if (num2 == 3) {
            // num = Convert.ToInt16((double)(ln / ((double)num2)));
            num = (int) (ln / ((double) num2));
            num5 = num * num2;
        } else {
            num = (int) (ln / ((double) num2));
            num5 = (num * num2) + (num2 / 2);
        }
        num5 *= num19;
        double num3 = ln * num19;
        double a = lat * num19;
        double num12 = (2.0 * num11) - (num11 * num11);
        double num13 = num12 * (1.0 - num12);
        double num20 = Math.sin(a);
        double num21 = Math.tan(a);
        double num22 = Math.cos(a);
        double num14 = num10 / Math.sqrt(1.0 - ((num12 * num20) * num20));
        double num15 = num21 * num21;
        double num16 = (num13 * num22) * num22;
        double num17 = (num3 - num5) * num22;
        double num23 = ((1.0 - (num12 / 4.0)) - (((3.0 * num12) * num12) / 64.0))
                - ((((5.0 * num12) * num12) * num12) / 256.0);
        double num24 = (((3.0 * num12) / 8.0) + (((3.0 * num12) * num12) / 32.0))
                + ((((45.0 * num12) * num12) * num12) / 1024.0);
        double num25 = (((15.0 * num12) * num12) / 256.0)
                + ((((45.0 * num12) * num12) * num12) / 1024.0);
        double num26 = (((35.0 * num12) * num12) * num12) / 3072.0;
        double num18 = num10
                * ((((num23 * a) - (num24 * Math.sin(2.0 * a))) + (num25 * Math
                .sin(4.0 * a))) - (num26 * Math.sin(6.0 * a)));
        double num27 = num17 * num17;
        double num28 = (num17 * num17) * num17;
        double num29 = ((num17 * num17) * num17) * num17;
        double num30 = (((num17 * num17) * num17) * num17) * num17;
        double num31 = ((((num17 * num17) * num17) * num17) * num17) * num17;
        double num8 = num14
                * ((num17 + ((((1.0 - num15) + num16) * num28) / 6.0)) + ((((((5.0 - (18.0 * num15)) + (num15 * num15)) + (72.0 * num16)) - (58.0 * num13)) * num30) / 120.0));
        double num9 = num18
                + ((num14 * Math.tan(a)) * (((num27 / 2.0) + (((((5.0 - num15) + (9.0 * num16)) + ((4.0 * num16) * num16)) * num29) / 24.0)) + ((((((61.0 - (58.0 * num15)) + (num15 * num15)) + (600.0 * num16)) - (330.0 * num13)) * num31) / 720.0)));
        if (num2 == 3) {
            num6 = (0xf4240 * num) + 0x7a120;
        } else {
            num6 = (0xf4240 * (num + 1)) + 0x7a120;
        }
        double num7 = 0.0;
        num8 += num6;
        num9 += num7;
       // X = num8;
        //Y = num9;

        gaussTolnlaPro(4047792.892, 40401563.299, true, 120, 0, 0);

    }

    // / <summary>
    // /
    // / </summary>
    // / <param name="x"></param>投影坐标x
    // / <param name="y"></param>投影坐标y
    // / <param name="IsThree"></param>判断是否为3度分带，true为3度分带，false为6度分带
    // / <param name="CL"></param>中央经线经度(单位：度)
    // / <param name="L"></param>地理坐标经度(单位：度)
    // / <param name="B"></param>地理坐标纬度(单位：度)
    public Coordinate gaussTolnlaPro(double x, double y, boolean IsThree, double CL,
                                double L, double B) {
        double num5;
        int num2 = 6;
        double num10 = 0.0;
        double num11 = 0.0;
        double num23 = 0.0174532925199433; // 角度单位
        // num10 = 6378140.0;
       // num10 = 6378245.0;
        num10 =  6378137.0;
        // num11 = 0.0033528131778969143;
       // num11 = 0.0033523298692591351;
        num11 = 1/298.257222101;
        int num = (int) (x / 1000000.0);
        if (IsThree) {
            num2 = 3;
        }

        if (num2 == 3) {
            num5 = num * num2;
        } else {
            num5 = ((num - 1) * num2) + (num2 / 2);
        }
        num5 *= num23;
        double num6 = (0xf4240 * num) + 0x7a120;
        double num7 = 0.0;
        double num8 = x - num6;
        double num9 = y - num7;
        double num13 = (2.0 * num11) - (num11 * num11);
        double num12 = (1.0 - Math.sqrt(1.0 - num13))
                / (1.0 + Math.sqrt(1.0 - num13));
        double num14 = num13 / (1.0 - num13);
        double num18 = num9;
        double num24 = ((1.0 - (num13 / 4.0)) - (((3.0 * num13) * num13) / 64.0))
                - ((((5.0 * num13) * num13) * num13) / 256.0);
        double num25 = ((3.0 * num12) / 2.0)
                - ((((27.0 * num12) * num12) * num12) / 32.0);
        double num26 = (((21.0 * num12) * num12) / 16.0)
                - (((((55.0 * num12) * num12) * num12) * num12) / 32.0);
        double num27 = (((151.0 * num12) * num12) * num12) / 96.0;
        double num28 = ((((1097.0 * num12) * num12) * num12) * num12) / 512.0;
        double num21 = num18 / (num10 * num24);
        double d = (((num21 + (num25 * Math.sin(2.0 * num21))) + (num26 * Math
                .sin(4.0 * num21))) + (num27 * Math.sin(6.0 * num21)))
                + (num28 * Math.sin(8.0 * num21));
        double num29 = Math.cos(d);
        double num30 = Math.tan(d);
        double num31 = Math.sin(d);
        double num17 = (num14 * num29) * num29;
        double num16 = num30 * num30;
        double num15 = num10 / Math.sqrt(1.0 - ((num13 * num31) * num31));
        double num20 = (num10 * (1.0 - num13))
                / Math.sqrt(((1.0 - ((num13 * num31) * num31)) * (1.0 - ((num13 * num31) * num31)))
                * (1.0 - ((num13 * num31) * num31)));
        double num19 = num8 / num15;
        double num32 = num19 * num19;
        double num33 = (num19 * num19) * num19;
        double num34 = ((num19 * num19) * num19) * num19;
        double num35 = (((num19 * num19) * num19) * num19) * num19;
        double num36 = ((((num19 * num19) * num19) * num19) * num19) * num19;
        double num37 = num16 * num16;
        double num38 = num17 * num17;
        double num3 = num5
                + (((num19 - ((((1.0 + (2.0 * num16)) + num17) * num33) / 6.0)) + (((((((5.0 - (2.0 * num17)) + (28.0 * num16)) - (3.0 * num38)) + (8.0 * num14)) + (24.0 * num37)) * num35) / 120.0)) / num29);
        double num4 = d
                - (((num15 * num30) / num20) * (((num32 / 2.0) - ((((((5.0 + (3.0 * num16)) + (10.0 * num17)) - (4.0 * num38)) - (9.0 * num14)) * num34) / 24.0)) + (((((((61.0 + (90.0 * num16)) + (298.0 * num17)) + (45.0 * num37)) - (256.0 * num14)) - (3.0 * num38)) * num36) / 720.0)));
        L = num3 / num23 + CL;
        B = num4 / num23;

        return new Coordinate(L, B);
    }

    // 高斯投影由大地坐标(Unit:Metres)反算经纬度(Unit:DD)
    public Coordinate GaussProjInvCal(double X, double Y)// 字串9

    {
        int ProjNo;
        int ZoneWide; // //带宽
        double longitude1, latitude1, longitude0, latitude0, X0, Y0, xval, yval;
        double e1, e2, f, a, ee, NN, T, C, M, D, R, u, fai, iPI;
        iPI = 0.0174532925199433; // //3.1415926535898/180.0;
        // a = 6378245.0; f = 1.0/298.3; //54年北京坐标系参数
        //a = 6378140.0;
        a = 6378137.0;
        //f = 1 / 298.257; // 80年西安坐标系参数
        f = 1/298.257222101;
        ZoneWide = 3; // //6度带宽
        ProjNo = (int) (X / 1000000L); // 查找带号
        longitude0 = (ProjNo - 1) * ZoneWide + ZoneWide / 2;
        longitude0 = longitude0 * iPI; // 中央经线
        longitude0 = 120;
        X0 = ProjNo * 1000000L + 500000L;
        Y0 = 0;
        xval = X - X0;
        yval = Y - Y0; // 带内大地坐标
        e2 = 2 * f - f * f;
        e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
        ee = e2 / (1 - e2);
        M = yval;
        u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
        fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u)
                + (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32)
                * Math.sin(4 * u) + (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u)
                + (1097 * e1 * e1 * e1 * e1 / 512) * Math.sin(8 * u);
        C = ee * Math.cos(fai) * Math.cos(fai);
        T = Math.tan(fai) * Math.tan(fai);
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));// 字串1
        R = a
                * (1 - e2)
                / Math.sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai))
                * (1 - e2 * Math.sin(fai) * Math.sin(fai))
                * (1 - e2 * Math.sin(fai) * Math.sin(fai)));
        D = xval / NN;
        // 计算经度(Longitude) 纬度(Latitude)
        longitude1 = longitude0
                + (D - (1 + 2 * T + C) * D * D * D / 6 + (5 - 2 * C + 28 * T
                - 3 * C * C + 8 * ee + 24 * T * T)
                * D * D * D * D * D / 120) / Math.cos(fai);
        latitude1 = fai
                - (NN * Math.tan(fai) / R)
                * (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D
                * D * D * D / 24 + (61 + 90 * T + 298 * C + 45 * T * T
                - 256 * ee - 3 * C * C)
                * D * D * D * D * D * D / 720);
        // 转换为度 DD

        return new Coordinate(longitude1 / iPI, latitude1 / iPI);
    }

    public Coordinate lonLatToMercator(double x,double y)
    {
        double toX = x * earthRExcept180;
        double toY = Math.log(Math.tan((90 + y) * piExcept360)) / piExcept180;
        toY = toY * earthRExcept180;
        return new Coordinate(toX, toY);
    }

    public Coordinate lonLatToMercator(Coordinate coordinate)
    {
        double toX = coordinate.x * earthRExcept180;
        double toY = Math.log(Math.tan((90 + coordinate.y) * piExcept360)) / piExcept180;
        toY = toY * earthRExcept180;
        coordinate.x = toX;
        coordinate.y = toY;
        return coordinate;
    }

    public Coordinate mercatorToLonLat(double x, double y)
    {
        double toX=x/ 20037508.342787 * 180;
        double toY=y/ 20037508.342787 * 180;
        toY = 180 / Math.PI * (2 * Math.atan(Math.exp(toY * Math.PI / 180)) - Math.PI / 2);
        return new Coordinate(toX, toY);
    }
}
