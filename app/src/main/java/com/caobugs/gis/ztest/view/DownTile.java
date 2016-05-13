package com.caobugs.gis.ztest.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.tile.downtile.factory.TiledLayerFactory;
import com.caobugs.gis.tile.downtile.tileurl.BaseTiledURL;
import com.caobugs.gis.tile.downtile.tileurl.GoogleTiledTypes;
import com.caobugs.gis.tile.downtile.tileurl.LYGTileType;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.tile.TileInfo;
import com.caobugs.gis.tile.downtile.tileurl.OpenStreetTileType;
import com.caobugs.gis.tile.factory.CoordinateSystemEnum;
import com.caobugs.gis.tile.factory.CoordinateSystemFactory;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/13
 */
public class DownTile extends Activity
{
    private static double piExcept360 = Math.PI / 360;
    private static double piExcept180 = Math.PI / 180;

    private static double earthRExcept180 = 20037508.342787 / 180;

    //小河镇  1.248778652474334E7, 3737861.3554900093
    //1.2493384893254824E7, 3734028.5682813274
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       // demo();
        //xiaohezhen();
        //40.023925, 116.229466
        //39.935191，116.509375
        //39.946487, 116.288600
        //39.933241, 116.304088

        beijing();
        Coordinate coordinate = lonLatToMercator(116.229466,40.023925);
        Coordinate coordinate1 = lonLatToMercator( 116.509375,39.935191);
        Log.e("sss",coordinate.toString()+  "   "+coordinate1.toString());
    }

    //01-20 11:41:12.172 32474-32474/com.youngken.zy_gis_demo_001 E/ZB: 13351184.453363707   3571106.811176191
    //01-20 11:41:14.312 32474-32474/com.youngken.zy_gis_demo_001 E/ZB: 13360855.024905564E7   3577801.5633670622
    public void demo()
    {
        TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.OPEN_STREET_MAP).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(OpenStreetTileType.TILE_OS);
        com.caobugs.gis.tile.downtile.DownTile downTile = new com.caobugs.gis.tile.downtile.DownTile(tileInfo, baseTiledURL
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


    public void beijing()
    {
        //(1.2938604970292658E7, 4869419.604634653)   (1.2969764297641108E7, 4856528.876808467)
        TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_IMAGE);
        com.caobugs.gis.tile.downtile.DownTile downTile = new com.caobugs.gis.tile.downtile.DownTile(tileInfo, baseTiledURL
                ,new Envelope(1.2938604970292658E7, 1.2969764297641108E7, 4869419.604634653, 4856528.876808467),6,21);
        try
        {
            downTile.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void xiaohezhen()
    {
        TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS).getTileInfo();
        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_IMAGE);
        com.caobugs.gis.tile.downtile.DownTile downTile = new com.caobugs.gis.tile.downtile.DownTile(tileInfo, baseTiledURL
                ,new Envelope(1.248735875733607E7, 1.249096068870382E7, 3724310.197728603, 3722990.7867262457),21,22);
        //1.248778652474334E7, 3737861.3554900093
        //1.2493384893254824E7, 3734028.5682813274
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
        com.caobugs.gis.tile.downtile.DownTile downTile = new com.caobugs.gis.tile.downtile.DownTile(
                tileInfo1,baseTiledURL,new Envelope(119.93573055555555,120.02260277777778,30.524172222222223, 30.57596388888889),9,15);
        try
        {
            downTile.run();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Coordinate lonLatToMercator(double x,double y)
    {
        double toX = x * earthRExcept180;
        double toY = Math.log(Math.tan((90 + y) * piExcept360)) / piExcept180;
        toY = toY * earthRExcept180;
        return new Coordinate(toX, toY);
    }
}