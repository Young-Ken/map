package com.caobugs.gis.ztest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.data.db.Database;
import com.caobugs.gis.data.db.sql.FarmlandSQL;
import com.caobugs.gis.enumeration.ConstantResult;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.location.GpsInfo;
import com.caobugs.gis.location.bd.BaiduLocation;
import com.caobugs.gis.tool.ApplicationContext;
import com.caobugs.gis.tool.GeomToString;
import com.caobugs.gis.view.appview.DownTile;
import com.caobugs.gis.view.appview.FarmlandInfoActivity;
import com.caobugs.gis.view.layer.BaseLayer;
import com.caobugs.gis.view.layer.FarmlandLayer;
import com.caobugs.gis.view.layer.MapLayerManger;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.tile.factory.CoordinateSystemEnum;
import com.caobugs.gis.view.map.MapStatus;
import com.caobugs.gis.view.map.event.OnMapStatusChangeListener;
import com.caobugs.gis.view.map.util.Projection;
import com.caobugs.gis.vo.Farmland;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2015/12/17
 */
public class MapActivity extends Activity implements View.OnClickListener, OnMapStatusChangeListener
{
    BaseMap map = null;
    private boolean isLocation = false;
    private BaiduLocation location = null;
    private boolean isDrawFarmland = false;
    private List<Coordinate> farmlandPoint = null;
    private Button drawFarmlandPointButton = null;
    private  Button locationButton = null;
    private Button drawFarmlandButton = null;
    private FarmlandSQL farmlandSQL = null;
    private LinearLayout selectFarmlandTool = null;
    private LinearLayout mapDarwFarmland = null;
    private FarmlandLayer farmlandLayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        new Database();
        setContentView(R.layout.basemap_layout);

        map = (BaseMap) findViewById(R.id.baseMap);

        Button searchButton = (Button) findViewById(R.id.search_envelope);
        searchButton.setOnClickListener(this);

        locationButton = (Button) findViewById(R.id.location_button);
        locationButton.setOnClickListener(this);

        Button text = (Button) findViewById(R.id.select_farmland);
        text.setOnClickListener(this);

        drawFarmlandButton = (Button) findViewById(R.id.draw_farmland);
        drawFarmlandButton.setOnClickListener(this);

        drawFarmlandPointButton = (Button) findViewById(R.id.draw_farmland_point);
        drawFarmlandPointButton.setOnClickListener(this);

        ViewTreeObserver vto = map.getViewTreeObserver();
        map.setMapStatusChangedListener(this);

        selectFarmlandTool = (LinearLayout) findViewById(R.id.selected_tool_layout);
        mapDarwFarmland = (LinearLayout) findViewById(R.id.map_draw_tool_layout);

        Button deleteButton = (Button) findViewById(R.id.delete_farmland);
        deleteButton.setOnClickListener(this);
        // attribute:min_x = "12945986.606604"
        //attribute:min_y = "4838237.908444"
        // attribute:max_x = "12963719.997167"
        // attribute:max_y = "4808863.74626"
        //        TileInfo tileInfo = new CoordinateSystemFactory().create(CoordinateSystemEnum.GOOGLE_CS).getTileInfo();
        //        BaseTiledURL baseTiledURL = TiledLayerFactory.getInstance().createTiledURL(GoogleTiledTypes.GOOGLE_IMAGE);
        //        com.caobugs.gis.tile.downtile.DownTile downTile = new com.caobugs.gis.tile.downtile.DownTile(tileInfo, baseTiledURL
        //                ,new Envelope(1.248778652474334E7, 1.2493384893254824E7, 3737861.3554900093, 3734028.5682813274),1,19);
        //(1.2938604970292658E7, 4869419.604634653)   (1.2969764297641108E7, 4856528.876808467)
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                map.getMapInfo().setDeviceHeight(map.getMeasuredHeight());
                map.getMapInfo().setDeviceWidth(map.getMeasuredWidth());
                map.initMap(CoordinateSystemEnum.GOOGLE_CS, new Envelope(1.2938604970292658E7, 1.2969764297641108E7, 4869419.604634653, 4856528.876808467));
                farmlandSQL = new FarmlandSQL();

                //                map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //                map.getMapInfo().setDeviceHeight(map.getMeasuredHeight());
                //                map.getMapInfo().setDeviceWidth(map.getMeasuredWidth());
                //                map.initMap(CoordinateSystemEnum.GOOGLE_CS, new Envelope(13012486.821215, 13032513.322625, 4396586.368211, 4385153.170403));
                // map.initMap(CoordinateSystemEnum.GOOGLE_CS, new
                //         Envelope(13351184.453363707, 13360855.024905564, 3571106.811176191, 3577801.5633670622));


                //30.524172 119.93573
                //30.57594 120.0226
                // map.initMap(CoordinateSystemEnum.LYG_HH_TILE, new Envelope(119.93573, 120.0226, 30.524172, 30.57594));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (resultCode)
        {
            case ConstantResult.RESULT_OK:
                Farmland farmland = new Farmland();
                Bundle bundle = data.getExtras();
                farmland.setTel(bundle.getString(Farmland.TEL));
                farmland.setFarmName((String) bundle.get(Farmland.FARMNAME));
                farmland.setAddress((String) bundle.get(Farmland.ADDRESS));
                Coordinate[] coordinates = farmlandPoint.toArray(new Coordinate[farmlandPoint.size()]);
                farmland.setFarmGeom(new LinearRing(coordinates));
                farmlandSQL.insert(farmland);

                if(map.getLevel() > 16)
                {
                    Envelope temp = map.getEnvelope();
                    farmlandSQL.selectFarmlandByEnvelop(GeomToString.geomToStringWEB(temp, map));
                } else {
                  Toast.makeText(ApplicationContext.getContext(),"请放到级别查看绘制完的田块",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.search_envelope:
                Intent intent = new Intent(MapActivity.this, DownTile.class);
                startActivity(intent);
                break;

            case R.id.location_button:
                if (location == null)
                {
                    location = new BaiduLocation(map);
                }

                if(location.isStart())
                {
                    location.stop();
                    locationButton.setText("开始定位");

                } else
                {
                    location.start();
                    locationButton.setText("停止定义");
                }
                break;
            case R.id.select_farmland:
                Coordinate temp = (Coordinate) map.getMapCenter().clone();
                temp = Projection.getInstance(map).imageTransFromEarth(temp);
                temp = Projection.getInstance(map).mercatorToLonLat(temp.x, temp.y);
                temp.x = Double.parseDouble(String.format("%.7f", temp.x));
                temp.y = Double.parseDouble(String.format("%.7f", temp.y));
                String point = "POINT("+temp.x+" "+temp.y+")";
                FarmlandLayer selectFarmLayer = farmlandSQL.selectFarmLandByPoint(point);

                if(selectFarmLayer != null)
                {
                    farmlandLayer = selectFarmLayer;
                    selectFarmlandTool.setVisibility(View.VISIBLE);
                    mapDarwFarmland.setVisibility(View.GONE);
                }
                map.refresh();
                break;
            case R.id.draw_farmland_point:
                drawFarmlandPoint();
                break;
            case R.id.draw_farmland:
               drawFarmland();
                break;
            case R.id.cancel_button:
                if(farmlandLayer != null)
                {
                    farmlandLayer.setSelected(null);
                    mapDarwFarmland.setVisibility(View.VISIBLE);
                    selectFarmlandTool.setVisibility(View.GONE);
                    map.refresh();
                }
                break;
            case R.id.delete_farmland:
                if (farmlandLayer == null)
                {
                    Toast.makeText(ApplicationContext.getContext(),"出现问题，不能删除，请从试",Toast.LENGTH_LONG).show();
                    break;
                } else {
                    if(farmlandLayer.getSelected() != null)
                    {
                        boolean result=  farmlandSQL.delete(farmlandLayer.getSelected().getId());
                        if(result)
                        {
                            Toast.makeText(ApplicationContext.getContext(),"删除成功",Toast.LENGTH_LONG).show();

                            ArrayList<Farmland> farmlands = farmlandLayer.getFarmlands();

                            for(Farmland farmland : farmlands)
                            {
                                if(farmland.getId() == farmlandLayer.getSelected().getId())
                                {
                                    farmlandLayer.getFarmlands().remove(farmland);
                                    break;
                                }
                            }
                        } else {
                            Toast.makeText(ApplicationContext.getContext(),"删除失败",Toast.LENGTH_LONG).show();
                        }

                        farmlandLayer.setSelected(null);
                        mapDarwFarmland.setVisibility(View.VISIBLE);
                        selectFarmlandTool.setVisibility(View.GONE);
                        map.refresh();
                    }
                }


        }
    }

    public void drawFarmland()
    {
        GpsInfo gpsInfo = GpsInfo.getInstance();
        if(gpsInfo.isEmpty())
        {
            Toast.makeText(this.getApplicationContext(),"请先进行定位，定位成功后才能操作本页面!",Toast.LENGTH_LONG).show();
            return;
        }

        if (!isDrawFarmland)
        {
            isDrawFarmland = true;
            drawFarmlandPointButton.setVisibility(View.VISIBLE);
            drawFarmlandButton.setText("结束绘制");
            farmlandPoint = new LinkedList<>();
        } else
        {
            if (farmlandPoint.size() < 3)
            {
                Toast.makeText(getApplicationContext(), "绘制点不能少于三个", Toast.LENGTH_LONG).show();
                return;
            }

            isDrawFarmland = false;
            drawFarmlandPointButton.setVisibility(View.GONE);
            drawFarmlandButton.setText("绘制田块");
            farmlandPoint.add(0, farmlandPoint.get(farmlandPoint.size() - 1));
            //绘制结束 存储点

            Intent farmInfoIntent = new Intent(MapActivity.this, FarmlandInfoActivity.class);
            startActivityForResult(farmInfoIntent, 0);
        }
    }

    public void drawFarmlandPoint()
    {
        Coordinate temp = (Coordinate) map.getMapCenter().clone();
        temp = Projection.getInstance(map).imageTransFromEarth(temp);
        temp = Projection.getInstance(map).mercatorToLonLat(temp.x, temp.y);
        temp.x = Double.parseDouble(String.format("%.7f", temp.x));
        temp.y = Double.parseDouble(String.format("%.7f", temp.y));
        farmlandPoint.add(temp);
        Toast.makeText(getApplicationContext(), "你已经绘制了"+farmlandPoint.size()+"个点", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapStatusChanged(String type, Intent intent)
    {
        MapLayerManger mapLayerManger = MapLayerManger.getInstance();
        List<BaseLayer> layers =  mapLayerManger.getArrayList();

        if(type.equals(MapStatus.Defualt.ZOOM.name()))
        {
            Bundle bundle = intent.getExtras();
            int level = bundle.getInt(MapStatus.Defualt.ZOOM.name());
            if(level > 16)
            {
                Envelope temp = map.getEnvelope();
                farmlandSQL.selectFarmlandByEnvelop(GeomToString.geomToStringWEB(temp, map));
            }
            if(level <= 15)
            {
                for (BaseLayer layer : layers)
                {
                    if(layer instanceof FarmlandLayer)
                    {
                        layer.recycle();
                    }
                }
            }
        }
    }
}
