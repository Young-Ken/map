package com.caobugs.gis.view.appview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caobugs.gis.R;
import com.caobugs.gis.vo.Position;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/16
 */
public class PositionSpinnerAdapter extends BaseSpinnerAdapter
{
    public PositionSpinnerAdapter(Context context, ArrayList<?> list)
    {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflate = LayoutInflater.from(context);
        ViewHolder viewHolder = null;
        if (convertView == null || convertView.getTag() == null)
        {
            viewHolder = new ViewHolder();
            convertView = layoutInflate.inflate(R.layout.position_item, null);
            viewHolder.pid = (TextView) convertView.findViewById(R.id.pid_text);
            viewHolder.name = (TextView) convertView.findViewById(R.id.p_name_text);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (arrayList == null)
        {
            return null;
        }

        Position temp = (Position) arrayList.get(position);

        viewHolder.pid.setText(temp.getPositionID().toString());
        viewHolder.name.setText(temp.getName().toString());
        return convertView;
    }

    public final class ViewHolder
    {
        public TextView pid;
        public TextView name;
    }
}
