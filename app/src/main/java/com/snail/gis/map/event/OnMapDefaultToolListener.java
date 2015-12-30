package com.snail.gis.map.event;

import android.view.View;
import com.snail.gis.map.BaseMap;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/25
 */
public interface OnMapDefaultToolListener extends View.OnTouchListener
{
   void onMapDefaultTool(BaseMap map);
}
