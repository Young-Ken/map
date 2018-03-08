package com.caobugs.map.layer.tile.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.Map;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/7
 */
public class MemoryTileCache extends LruCache implements TileCache {

    public MemoryTileCache(int maxSize) {
        super(maxSize);
    }

    public int sizeOf(String key, byte[] value) {
        return value.length;
    }

    protected void entryRemoved(boolean evicted, String key,
                                Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    @Override
    public void destroy() {
        super.evictAll();
    }

    @Override
    public boolean remove(String[] keys) {
        for (String key : keys) {
            if (remove(key) == null)
                return false;
        }
        return true;
    }

    @Override
    public Map<String, byte[]> getAll() {
        return this.snapshot();
    }

    @Override
    public byte[] get(String key) {
        return (byte[]) super.get(key);
    }

    @Override
    public void put(String key, byte[] tile) {
        super.put(key, tile);
    }
}
