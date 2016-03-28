package com.caobugs.gis.data;

import java.util.ArrayList;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/3/2
 */
public class FeatureClass
{
    private ArrayList<Feature> features = null;
    private ArrayList<String> head = null;

    public FeatureClass(ArrayList<Feature> features, ArrayList<String> head)
    {
        if(features == null)
        {
            features = new ArrayList<>();
        }

        if(head == null)
        {
            head = new ArrayList<>();
        }
        this.features = features;
        this.head = head;
    }

    public FeatureClass(ArrayList<Feature> features)
    {
       this(features, null);
    }

   public Feature getFeature(int index)
   {
       return features.get(index);
   }

    public ArrayList<Feature> getFeatures()
    {
        return features;
    }

    public ArrayList<String> getHead()
    {
        return head;
    }
}
