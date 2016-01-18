package com.snail.gis.tile;

import com.snail.gis.geometry.Coordinate;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/14
 */
public class LYGCoordinateSystem extends BaseCoordinateSystem
{
    private int MAX_LEVEL = 20;
    public LYGCoordinateSystem()
    {
        //super(new Coordinate(-180, 90),295497593.05875003,0.703125,MAX_LEVEL);
    }

    double[] resolutions = {
           // 1.40625,
            0.703125,
            0.3515625,
            0.17578125,
            0.087890625,
            0.0439453125,
            0.02197265625,
            0.010986328125,
            0.0054931640625,
            0.00274658203125,
            0.001373291015625,
            0.0006866455078125,
            0.00034332275390625,
            0.000171661376953125,
            8.58306884765629E-05,
            4.29153442382814E-05,
            2.14576721191407E-05,
            1.07288360595703E-05,
            5.36441802978515E-06,
            2.68220901489258E-06,
            1.34110450744629E-06
    };

    double[] scales = {
           // 400000000,
            295497598.5708346,
            147748799.285417,
            73874399.6427087,
            36937199.8213544,
            18468599.9106772,
            9234299.95533859,
            4617149.97766929,
            2308574.98883465,
            1154287.49441732,
            577143.747208662,
            288571.873604331,
            144285.936802165,
            72142.9684010827,
            36071.4842005414,
            18035.7421002707,
            9017.87105013534,
            4508.93552506767,
            2254.467762533835,
            1127.2338812669175,
            563.616940
    };

    @Override
    public void initTileInfo()
    {
        TileInfo tileInfo = getTileInfo();
        tileInfo.setResolutions(resolutions);
        tileInfo.setScales(scales);
        tileInfo.setOriginPoint(new Coordinate(-180, 90));
    }
}
