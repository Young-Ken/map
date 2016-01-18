package com.snail.gis.downtile.factory;
import com.snail.gis.downtile.tileurl.BaseTiledURL;

/**
 * 工程厂模式
 * @author Young Ken
 * @version 0.1
 * @since 2015/9/16
 */
public abstract class URLFactory
{
    abstract BaseTiledURL createTiledURL(IURLEnum layerEnum);
}
