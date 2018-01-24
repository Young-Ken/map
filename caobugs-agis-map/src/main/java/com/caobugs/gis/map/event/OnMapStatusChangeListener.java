package com.caobugs.gis.map.event;

import android.content.Intent;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/29
 */
public interface OnMapStatusChangeListener extends MapListener
{
    void onMapStatusChanged(String type, Intent intent);
}
