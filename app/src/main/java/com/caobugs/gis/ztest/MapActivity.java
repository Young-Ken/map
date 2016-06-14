package com.caobugs.gis.ztest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.algorithm.RobustDeterminant;
import com.caobugs.gis.data.db.sql.FarmlandSQL;
import com.caobugs.gis.geometry.Coordinate;
import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.geometry.primary.Envelope;
import com.caobugs.gis.location.GpsInfo;
import com.caobugs.gis.location.bd.BaiduLocation;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.GeomToString;
import com.caobugs.gis.util.constants.ConstantResult;
import com.caobugs.gis.view.appview.DownTileActivity;
import com.caobugs.gis.view.appview.EditFarmlandInfoActivity;
import com.caobugs.gis.view.appview.FarmlandInfoActivity;
import com.caobugs.gis.view.appview.LoginActivity;
import com.caobugs.gis.view.server.UploadServer;
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
    private Button locationButton = null;
    private Button drawFarmlandButton = null;
    private Button uploadFarmland = null;
    private FarmlandSQL farmlandSQL = null;
    private LinearLayout selectFarmlandTool = null;
    private LinearLayout mapDarwFarmland = null;
    private FarmlandLayer farmlandLayer = null;
    private long lastBackTime = 0;
    private long currentBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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

        uploadFarmland = (Button)findViewById(R.id.upload_farmland);
        uploadFarmland.setOnClickListener(this);

        ViewTreeObserver vto = map.getViewTreeObserver();
        map.setMapStatusChangedListener(this);

        selectFarmlandTool = (LinearLayout) findViewById(R.id.selected_tool_layout);
        mapDarwFarmland = (LinearLayout) findViewById(R.id.map_draw_tool_layout);

        Button deleteButton = (Button) findViewById(R.id.delete_farmland);
        deleteButton.setOnClickListener(this);

        Button zoomIn = (Button) findViewById(R.id.zoom_in);
        zoomIn.setOnClickListener(this);

        Button zoomOut = (Button) findViewById(R.id.zoom_out);
        zoomOut.setOnClickListener(this);

        Button editFarmland = (Button)findViewById(R.id.edit_farmland);
        editFarmland.setOnClickListener(this);

        Button cancleButton = (Button)findViewById(R.id.cancel_button);
        cancleButton.setOnClickListener(this);

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
            }
        });
        checkIdentity();
    }

    public void checkIdentity()
    {
        SharedPreferences settings = getSharedPreferences("setting", 0);
        String tel = settings.getString("tel","null");

        if(tel.equals("null"))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bundle bundle = null;
        Farmland farmland = null;
        switch (resultCode)
        {
            case ConstantResult.RESULT_OK:
                farmland = new Farmland();
                bundle = data.getExtras();
                farmland.setTel(bundle.getString(Farmland.TEL));
                farmland.setFarmName((String) bundle.get(Farmland.FARMNAME));
                farmland.setAddress((String) bundle.get(Farmland.ADDRESS));
                Coordinate[] coordinates = farmlandPoint.toArray(new Coordinate[farmlandPoint.size()]);
                farmland.setFarmGeom(new LinearRing(coordinates));
                farmlandSQL.insert(farmland);

                if (map.getLevel() > 16)
                {
                    Envelope temp = map.getEnvelope();
                    farmlandSQL.selectFarmlandByEnvelop(GeomToString.geomToStringWEB(temp, map));
                } else
                {
                    Toast.makeText(ApplicationContext.getContext(), "请放到到16级别查看绘制完的田块", Toast.LENGTH_LONG).show();
                }
                break;
            case ConstantResult.RESULT_UPDATE:
                if (farmlandLayer == null || farmlandLayer.getSelected() == null)
                {
                    Toast.makeText(ApplicationContext.getContext(), "出现问题，不能修改，请从试", Toast.LENGTH_LONG).show();
                    return;
                } else
                {
                    farmland = farmlandLayer.getSelected();
                    bundle = data.getExtras();
                    farmland.setTel(bundle.getString(Farmland.TEL));
                    farmland.setFarmName((String) bundle.get(Farmland.FARMNAME));
                    if (farmlandSQL.update(farmland))
                    {
                        ArrayList<Farmland> arrayList = farmlandLayer.getFarmlands();
                        for (Farmland temp : arrayList)
                        {
                            if (farmland.getId() == temp.getId())
                            {
                                temp.setFarmName(farmland.getFarmName());
                                temp.setTel(farmland.getTel());
                            }
                        }
                    } else
                    {
                        Toast.makeText(ApplicationContext.getContext(), "更新失败，不能修改，请从试", Toast.LENGTH_LONG).show();
                    }
                    farmlandLayer.setSelected(null);
                    mapDarwFarmland.setVisibility(View.VISIBLE);
                    selectFarmlandTool.setVisibility(View.GONE);
                    map.refresh();
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
                Intent downIntent = new Intent(MapActivity.this, DownTileActivity.class);
                startActivity(downIntent);
                break;

            case R.id.location_button:
                location();
                break;
            case R.id.select_farmland:
                Coordinate temp = (Coordinate) map.getMapCenter().clone();
                temp = Projection.getInstance(map).imageTransFromEarth(temp);
                temp = Projection.getInstance(map).mercatorToLonLat(temp.x, temp.y);
                temp.x = Double.parseDouble(String.format("%.7f", temp.x));
                temp.y = Double.parseDouble(String.format("%.7f", temp.y));

                String point = "POINT(" + temp.x + " " + temp.y + ")";
                FarmlandLayer selectFarmLayer =
                        farmlandSQL.selectFarmLandByPoint(point);

                if (selectFarmLayer != null)
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
                if (farmlandLayer != null)
                {
                    farmlandLayer.setSelected(null);
                    mapDarwFarmland.setVisibility(View.VISIBLE);
                    selectFarmlandTool.setVisibility(View.GONE);
                    map.refresh();
                }
                break;
            case R.id.delete_farmland:
                deleteFarmland();
                break;
            case R.id.edit_farmland:
                if (farmlandLayer == null)
                {
                    Toast.makeText(ApplicationContext.getContext(), "出现问题，不能修改，请从试", Toast.LENGTH_LONG).show();
                    return;
                } else
                {
                    if (farmlandLayer.getSelected() != null)
                    {
                        Intent infoIntent = new Intent(MapActivity.this, EditFarmlandInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Farmland.TEL, farmlandLayer.getSelected().getTel());
                        bundle.putString(Farmland.FARMNAME, farmlandLayer.getSelected().getFarmName());
                        infoIntent.putExtras(bundle);
                        startActivityForResult(infoIntent,0);
                    } else
                    {
                        Toast.makeText(ApplicationContext.getContext(), "出现问题，没有选中的田块，请从试", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                break;

            case R.id.upload_farmland:
                Intent intent = new Intent(MapActivity.this, UploadServer.class);
                startService(intent);
                break;

            case R.id.zoom_in:
                map.getMapController().zoomIn();
                map.refresh();
                break;
            case R.id.zoom_out:
                map.getMapController().zoomOut();
                map.refresh();
                break;
        }
    }

    public void location()
    {
        if (location == null)
        {
            location = new BaiduLocation(map);
        }

        if (location.isStart())
        {
            location.stop();
            locationButton.setText("开始定位");

        } else
        {
            location.start();
            locationButton.setText("停止定位");
        }
    }

    public void stopLocation()
    {
        if (location == null)
        {
            location = new BaiduLocation(map);
        }
        if (location.isStart())
        {
            location.stop();
            locationButton.setText("开始定位");

        }
    }

    public void deleteFarmland()
    {
        if (farmlandLayer == null || farmlandLayer.getSelected() == null)
        {
            Toast.makeText(ApplicationContext.getContext(), "出现问题，不能删除，请从试", Toast.LENGTH_LONG).show();
            return;
        } else
        {
            new AlertDialog.Builder(this).setTitle("系统提示").setMessage("确定要删除田块数据！").setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    boolean result = farmlandSQL.delete(farmlandLayer.getSelected().getId());
                    if (result)
                    {
                        Toast.makeText(ApplicationContext.getContext(), "删除成功", Toast.LENGTH_LONG).show();

                        ArrayList<Farmland> farmlands = farmlandLayer.getFarmlands();

                        for (Farmland farmland : farmlands)
                        {
                            if (farmland.getId() == farmlandLayer.getSelected().getId())
                            {
                                farmlandLayer.getFarmlands().remove(farmland);
                                break;
                            }
                        }
                    } else
                    {
                        Toast.makeText(ApplicationContext.getContext(), "删除失败", Toast.LENGTH_LONG).show();
                    }

                    farmlandLayer.setSelected(null);
                    mapDarwFarmland.setVisibility(View.VISIBLE);
                    selectFarmlandTool.setVisibility(View.GONE);
                    map.refresh();
                }
            }).setNegativeButton("返回", new DialogInterface.OnClickListener()
            {
                @Override

                public void onClick(DialogInterface dialog, int which)
                {
                    if (farmlandLayer != null)
                    {
                        farmlandLayer.setSelected(null);
                        mapDarwFarmland.setVisibility(View.VISIBLE);
                        selectFarmlandTool.setVisibility(View.GONE);
                        map.refresh();
                    }
                }

            }).show();
        }

    }


    public void drawFarmland()
    {
        GpsInfo gpsInfo = GpsInfo.getInstance();
        if (gpsInfo.isEmpty())
        {
            Toast.makeText(this.getApplicationContext(), "请先进行定位，定位成功后才能操作本页面!", Toast.LENGTH_LONG).show();
            return;
        }

        if (map.getLevel() <= 15)
        {
            Toast.makeText(this.getApplicationContext(), "请放到16级以上，才能进行绘制！", Toast.LENGTH_LONG).show();
            return;
        }
        stopLocation();

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

        for (int i = 0; i < farmlandPoint.size(); i++)
        {
            if (temp.x == farmlandPoint.get(i).x && temp.y == farmlandPoint.get(i).y)
            {
                Toast.makeText(getApplicationContext(), "这个点已经存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        for (int i = 0; i < farmlandPoint.size() - 1; i++)
        {
            if (RobustDeterminant.orientationIndex(
                    farmlandPoint.get(i),
                    farmlandPoint.get(i + 1),
                    new Coordinate(temp.x, temp.y)) == 0)
            {
                Toast.makeText(getApplicationContext(), "这个点在线上", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        farmlandPoint.add(temp);
        Toast.makeText(getApplicationContext(), "你已经绘制了" + farmlandPoint.size() + "个点", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onMapStatusChanged(String type, Intent intent)
    {
        if (type.equals(MapStatus.Defualt.ZOOM.name()) || type.equals(MapStatus.Defualt.SCROLLTO.name()))
        {
            queryFarmlandByEnvelop();
        }
    }

    private void queryFarmlandByEnvelop()
    {
        MapLayerManger mapLayerManger = MapLayerManger.getInstance();
        List<BaseLayer> layers = mapLayerManger.getArrayList();

        int level = map.getLevel();
        if (level > 16)
        {
            Envelope temp = map.getEnvelope();
            farmlandSQL.selectFarmlandByEnvelop(GeomToString.geomToStringWEB(temp, map));
        }
        if (level <= 15)
        {
            for (BaseLayer layer : layers)
            {
                if (layer instanceof FarmlandLayer)
                {
                    layer.recycle();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            currentBackTime = System.currentTimeMillis();
            if (currentBackTime - lastBackTime > 2 * 1000)
            {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            } else
            {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
