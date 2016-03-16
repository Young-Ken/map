package com.snail.gis.ztest;

import android.app.Activity;
import android.os.Bundle;


import com.snail.gis.tile.downtile.factory.TiledLayerFactory;
import com.snail.gis.tile.downtile.tileurl.BaseTiledURL;
import com.snail.gis.tile.downtile.tileurl.LYGTileType;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.tile.TileInfo;
import com.snail.gis.tile.downtile.tileurl.OpenStreetTileType;
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
        demo();
    }

    //01-20 11:41:12.172 32474-32474/com.youngken.zy_gis_demo_001 E/ZB: 13351184.453363707   3571106.811176191
    //01-20 11:41:14.312 32474-32474/com.youngken.zy_gis_demo_001 E/ZB: 13360855.024905564E7   3577801.5633670622
    public void demo()
    {
        TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.OPEN_STREET_MAP).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(OpenStreetTileType.TILE_OS);
        com.snail.gis.tile.downtile.DownTile downTile = new com.snail.gis.tile.downtile.DownTile(tileInfo, baseTiledURL
                ,new Envelope(13012333.947158, 13038513.322625, 4404406.101353, 4365153.170403),11,15);
        //13012486.821215, 4396586.368211
        //13032513.322625, 4385153.170403
        //13012333.947158, 4404406.101353
        //13036335.174038, 4359279.045776
        try
        {
            downTile.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

//        TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS).getTileInfo();
//        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_VECTOR);
//        com.snail.gis.tile.downtile.DownTile downTile =
//                new com.snail.gis.tile.downtile.DownTile(tileInfo,
//                        baseTiledURL,new Envelope(13351184.453363707, 13360855.024905564,
//                        3571106.811176191, 3577801.5633670622),8,20);
//        try
//        {
//            downTile.run();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }

    //30.524172222222223
    //119.93573055555555

    //30.57596388888889
    //120.02260277777778
    public void downLYG()
    {
        TileInfo tileInfo1 = new CoordinateSystemFactory().create(CoordinateSystemEnum.LYG_HH_TILE).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(LYGTileType.LYG_HK_TILE);
        com.snail.gis.tile.downtile.DownTile downTile = new com.snail.gis.tile.downtile.DownTile(
                tileInfo1,baseTiledURL,new Envelope(119.93573055555555,120.02260277777778,30.524172222222223, 30.57596388888889),9,15);
        try
        {
            downTile.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
