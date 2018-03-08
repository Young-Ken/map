package com.caobugs.map.layer;
import com.caobugs.map.layer.api.ILayer;
import com.caobugs.map.layer.api.ILayerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @since 2018-01
 */
public class LayerManager implements ILayerManager {

    private List<ILayer> mLayers;

    public LayerManager() {

        mLayers = new ArrayList<>();
    }

    @Override
    public synchronized void remove(ILayer layer) {
        if (layer == null) {
            throw new IllegalArgumentException("layer 不能为空");
        }
        mLayers.remove(layer);
    }

    @Override
    public synchronized void add(ILayer layer) {
        if (layer == null) {
            throw new IllegalArgumentException("layer 不能为空");
        }
        mLayers.add(layer);
    }

    @Override
    public void refreshLayer() {

    }
}
