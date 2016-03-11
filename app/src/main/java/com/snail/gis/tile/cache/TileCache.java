package com.snail.gis.tile.cache;

import android.graphics.Bitmap;

import java.util.Map;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/7
 */
public interface TileCache
{
    /**
     * 销毁缓存
     */
    void destroy();

    byte[] get(String key);

    void put(String key, byte[] tile);

    boolean remove(String[] keys);

    Map<String, byte[]> getAll();

}
