package com.caobugs.gis.map.event;

import android.view.MotionEvent;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/25
 */
public interface OnMapDefaultListener extends MapListener {
   void onMapDefaultEvent(MotionEvent event);
}
