package com.snail.gis.tile.downtile.factory;


import com.snail.gis.tile.downtile.tileurl.BaseTiledURL;
import com.snail.gis.tile.downtile.tileurl.GoogleTiledTypes;
import com.snail.gis.tile.downtile.tileurl.GoogleURL;
import com.snail.gis.tile.downtile.tileurl.LYGTileType;
import com.snail.gis.tile.downtile.tileurl.LYGUrl;
import com.snail.gis.tile.downtile.tileurl.TDTTiledType;
import com.snail.gis.tile.downtile.tileurl.TDTUrl;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/9/16
 */
public class TiledLayerFactory extends URLFactory
{
    /**
     * 单例模式
     */
    private volatile static TiledLayerFactory instance = null;

    private TiledLayerFactory()
    {
    }

    /**
     * 单例模式返回ShapeFileManager实例
     * @return ShapeFileManager实例
     */
    public static TiledLayerFactory getInstance()
    {
        if (null == instance)
        {
            synchronized (TiledLayerFactory.class)
            {
                if (null == instance)
                {
                    instance = new TiledLayerFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public BaseTiledURL createTiledURL(final IURLEnum layerEnum)
    {
        if (layerEnum instanceof GoogleTiledTypes)
        {
            return new GoogleURL((GoogleTiledTypes) layerEnum);
        } else if (layerEnum instanceof TDTTiledType)
        {
            return new TDTUrl((TDTTiledType) layerEnum);
        } else if(layerEnum instanceof LYGTileType)
        {
            return new LYGUrl((LYGTileType) layerEnum);
        }
        return null;
    }

}
