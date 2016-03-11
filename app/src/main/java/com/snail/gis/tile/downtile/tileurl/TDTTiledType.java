package com.snail.gis.tile.downtile.tileurl;

import com.snail.gis.tile.downtile.factory.IURLEnum;

/**
 * Created by Young Ken on 2015/8/26.
 */
public enum TDTTiledType implements IURLEnum
{

    /**
     * 天地图矢量
     * */
    TDT_VECTOR,
    /**
     * 天地图影像
     * */
    TDT_IMAGE,
    /**
     * 天地图矢量标注
     * */
    TDT_VECTOR_BZ,
    /**
     * 天地图影像标注
     * */
    TDT_IMAGE_BZ;

    public void setName()
    {

    }

    @Override
    public String getName()
    {
        return this.name().toString();
    }
}
