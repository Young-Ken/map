package com.snail.gis.view.layer;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/14
 */
public interface ILayerSubject
{
    boolean addLayer(BaseLayer layer);
    boolean removeLayer(BaseLayer layer);
    boolean moveLayer();
    boolean zoomLayer();

}
