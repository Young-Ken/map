package com.snail.gis.enumeration;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class ConstantMath
{
    public static final double pi = 3.1415926535898;
    public static final double twoPi = 6.28318530718;
    /**
     * 地球半径
     */
    public static final double earthR = 6378137;

    /**
     * 2*pi*earthR
     */
    public static final double earthPerimeter = 40075016.6855785724052;

    /**
     * 　ground resolution = (cos(latitude * pi/180) * 2 * pi * 6378137 meters) / (256 * 2level pixels)
     *   初始化的时候latitude为0，则cos(latitude * pi/180)为1，level为0 2^0 = 1
     *   最后就是地图周长/256
     */
    public static final double startGroundResolution = 156543.033928023;

    /**
     * 256 * 2level / screen dpi / 0.0254 / (cos(latitude * pi/180) * 2 * pi * 6378137)
     * 比例尺= 1 : (cos(latitude * pi/180) * 2 * pi * 6378137 * screen dpi) / (256 * 2level * 0.0254)
     * map scale = 1 : ground resolution * screen dpi / 0.0254 meters/inch
     */
    public static final double startScale = 591657527.591555;

    public static final double startMctX = -20037508.3427892;
    public static final double startMctY =  20037508.3427892;
}
