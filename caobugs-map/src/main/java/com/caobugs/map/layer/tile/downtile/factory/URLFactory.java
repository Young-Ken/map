package com.caobugs.map.layer.tile.downtile.factory;

import com.caobugs.map.layer.tile.downtile.tileurl.BaseTiledURL;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/18
 */
public abstract class URLFactory {

    abstract BaseTiledURL createTiledURL(IURLEnum layerEnum);
}
