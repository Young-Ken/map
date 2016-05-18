package com.caobugs.gis.view.appview.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/4/1
 */
public class BaseSpinnerAdapter extends BaseAdapter
{

    protected ArrayList<?> arrayList = null;
    protected Context context;



    public BaseSpinnerAdapter(Context context, ArrayList<?> list)
    {
        this.arrayList = list;
        this.context = context;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       return null;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return arrayList.isEmpty();
    }

}
