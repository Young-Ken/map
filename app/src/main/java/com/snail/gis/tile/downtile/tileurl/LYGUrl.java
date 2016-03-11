package com.snail.gis.tile.downtile.tileurl;

import com.snail.gis.tile.downtile.factory.IURLEnum;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/14
 */
public class LYGUrl implements BaseTiledURL
{
    LYGTileType lygTileType = null;
    String tileUrl = "http://61.132.0.56:8080/RemoteRest/services/%E8%BF%9E%E4%BA%91%E6%B8%AF2014%E5%B9%B4%E8%88%AA%E7%A9%BA%E5%BD%B1%E5%83%8F/MapServer/tile/";

    public LYGUrl(LYGTileType lygTileType)
    {
        this.lygTileType = lygTileType;
    }
    @Override
    public String getTiledServiceURL(int level, int col, int row)
    {
        switch (lygTileType)
        {
            case LYG_HK_TILE:
                StringBuffer stringBuffer = new StringBuffer(tileUrl);
                return stringBuffer.append(level).append("/").append(row).append("/").append(col).toString();
        }
        return null;
    }

    @Override
    public IURLEnum getMapServiceType()
    {
        return lygTileType;
    }
}
