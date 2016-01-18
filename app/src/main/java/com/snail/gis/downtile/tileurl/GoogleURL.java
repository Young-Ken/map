package com.snail.gis.downtile.tileurl;


import com.snail.gis.downtile.factory.IURLEnum;
import com.snail.gis.downtile.tileurl.BaseTiledURL;
import com.snail.gis.downtile.tileurl.GoogleTiledTypes;

/**
 * Created by Young Ken on 2015/8/26.
 */
public class GoogleURL implements BaseTiledURL
{
    private GoogleTiledTypes mapServiceTypes;
    public GoogleURL(GoogleTiledTypes mapServiceTypes)
    {
        this.mapServiceTypes = mapServiceTypes;
    }

    /**
     * 根据级别 组装URL
     * @param level 级别
     * @param col 行
     * @param row 列
     * @return url字符串
     */
    @Override
    public String getTiledServiceURL(int level, int col, int row)
    {
        String s = "Galileo".substring(0, ((3 * col + row) % 8));
        String url = "";
        switch (mapServiceTypes)
        {
            case GOOGLE_IMAGE:
                url = "http://mt" + (col % 4) + ".google.com/vt/lyrs=s&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
            case GOOGLE_VECTOR:
                url = "http://mt" + (col % 4) + ".google.com/vt/lyrs=m@158000000&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
            case GOOGLE_TOPO:
                url = "http://mt" + (col % 4) + ".google.cn/vt/lyrs=t@131,r@227000000&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
            case GOOGLE_NET_ROAD:
                url = "http://mt" + (col % 4) + ".google.com/vt/imgtp=png32&lyrs=h@169000000&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                //	url = "http://mt"+ (col % 4) +".google.cn/vt/lyrs=m@256000000&hl=zh-CN&gl=CN&src=app&x=" + col + "&y=" + row + "&z=" + level + "&s=" + s;
                break;
        }
        return url;
    }

    @Override
    public IURLEnum getMapServiceType()
    {
        return mapServiceTypes;
    }

}
