package com.caobugs.map.view.api;

import com.caobugs.map.coordinate.projection.api.IProjection;
import com.caobugs.map.layer.api.ILayerManager;
import com.caobugs.map.opration.api.IOperation;

/**
 * @author Young Ken
 * @since 2018-01
 */
public interface IMapView {

    int getHeight();
    int getWidth();
    ILayerManager getLayerManager();
    IProjection getProjection();
    IOperation getOperation();
    void destroy();
}
