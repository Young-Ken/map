package com.caobugs.map.layer.tile.downtile.tileurl;

import com.caobugs.map.layer.tile.downtile.factory.IURLEnum;

public enum TDTTiledType implements IURLEnum {

    /**
     * 天地图矢量
     */
    TDT_VECTOR,
    /**
     * 天地图影像
     */
    TDT_IMAGE,
    /**
     * 天地图矢量标注
     */
    TDT_VECTOR_BZ,
    /**
     * 天地图影像标注
     */
    TDT_IMAGE_BZ;

    public void setName() {

    }

    @Override
    public String getName() {
        return this.name().toString();
    }
}
