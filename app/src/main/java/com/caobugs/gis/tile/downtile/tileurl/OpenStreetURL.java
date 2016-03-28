package com.caobugs.gis.tile.downtile.tileurl;

import com.caobugs.gis.tile.downtile.factory.IURLEnum;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/14
 */
public class OpenStreetURL implements BaseTiledURL
{

    private OpenStreetTileType tileType;
    public OpenStreetURL(OpenStreetTileType tileType)
    {
        this.tileType = tileType;
    }
    /**
     * 瓦片地址格式：http://a.tile.openstreetmap.org/9/420/193.png

     Cycle Map：http://c.tile.opencyclemap.org/cycle/9/420/193.png

     Transport Map：http://b.tile2.opencyclemap.org/transport/9/420/193.png

     MapQuest Map：http://otile3.mqcdn.com/tiles/1.0.0/osm/9/420/193.png
     * @param level 地图当前级别
     * @param col 行
     * @param row 列
     * @return
     */
    @Override
    public String getTiledServiceURL(int level, int col, int row)
    {
        switch (tileType)
        {
            case TILE_OS:
                return "http://a.tile.openstreetmap.org/"+level+"/"+col+"/"+row+".png";
            case CYCLE_OS:
                return "http://http://c.tile.opencyclemap.org/cycle/"+level+"/"+col+"/"+row+".png";
            case TRANSPORT_OS:
                return "http://b.tile2.opencyclemap.org/transport/"+level+"/"+col+"/"+row+".png";
            case MAPQUEST_OS:
                return "http://otile3.mqcdn.com/tiles/1.0.0/osm/"+level+"/"+col+"/"+row+".png";
        }
        return null;
    }

    @Override
    public IURLEnum getMapServiceType()
    {
        return tileType;
    }
}
