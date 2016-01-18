package com.snail.gis.ztest;

import android.app.Activity;
import android.os.Bundle;

import com.snail.gis.downtile.factory.TiledLayerFactory;
import com.snail.gis.downtile.tileurl.BaseTiledURL;
import com.snail.gis.downtile.tileurl.GoogleTiledTypes;
import com.snail.gis.downtile.tileurl.LYGTileType;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tile.factory.CoordinateSystemEnum;
import com.snail.gis.tile.factory.CoordinateSystemFactory;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/13
 */
public class DownTile extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        downLYG();
    }

    public void demo()
    {
        TileInfo tileInfo1 = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_VECTOR);
        com.snail.gis.downtile.DownTile downTile = new com.snail.gis.downtile.DownTile(tileInfo1, baseTiledURL,new Envelope(12945986.606604, 12963719.997167, 4838237.908444, 4808863.74626),12,18);
        try
        {
            downTile.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void downLYG()
    {
        TileInfo tileInfo1 = new CoordinateSystemFactory().create(CoordinateSystemEnum.LYG_HH_TILE).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(LYGTileType.LYG_HK_TILE);
        com.snail.gis.downtile.DownTile downTile = new com.snail.gis.downtile.DownTile(tileInfo1,baseTiledURL,new Envelope(118.89889200244264,119.994384167236,34.37661887668826, 35.21442877328082),9,15);
        try
        {
            downTile.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
