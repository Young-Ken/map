package com.caobugs.gis.coordinate.system;

import com.caobugs.gis.coordinate.BaseCoordinateSystem;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.tile.TileInfo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/14
 */
public class OpenStreeCoordinateSystem extends BaseCoordinateSystem
{
    private int MAX_LEVEL = 20;
    public OpenStreeCoordinateSystem()
    {
        //super(new Point(-180, 90),295497593.05875003,0.703125,MAX_LEVEL);
    }

    double[] resolutions = {
            156543.03390625, 78271.516953125, 39135.7584765625,
            19567.87923828125, 9783.939619140625, 4891.9698095703125,
            2445.9849047851562, 1222.9924523925781, 611.4962261962891,
            305.74811309814453, 152.87405654907226, 76.43702827453613,
            38.218514137268066, 19.109257068634033, 9.554628534317017,
            4.777314267158508, 2.388657133579254, 1.194328566789627,
            0.5971642833948135, 0.25, 0.1, 0.05
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
        tileInfo.setOriginPoint(new Coordinate(-20037508.342787, 20037508.342787));
    }
}
