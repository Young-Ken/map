package com.caobugs.gis.view.server;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import com.caobugs.gis.data.db.sql.FarmlandSQL;
import com.caobugs.gis.util.GeomToString;
import com.caobugs.gis.view.appview.LoginActivity;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.vo.Farmland;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/7/4
 */
public class UploadServerOkHttp extends IntentService
{

    private FarmlandSQL farmlandSQL = null;
    private ArrayList<Farmland> uploadFarmlands = null;
    private String url = "http://m.farm-keeper.com/api/farmland/uploadInfo";
    private String tel = "";
    private String body;
    private OkHttpClient client;

    public UploadServerOkHttp()
    {
        super("UploadServerOkHttp");
    }

    public void checkCollectionTel()
    {
        SharedPreferences settings = getSharedPreferences("setting", 0);
        String tel = settings.getString("tel", "");

        if (!tel.equals(""))
        {
            this.tel = tel;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        client = new OkHttpClient();
        farmlandSQL = new FarmlandSQL();
        checkCollectionTel();

        if (tel.equals(""))
        {
            Intent dialogIntent = new Intent(getBaseContext(), LoginActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(dialogIntent);
        } else
        {
            queryFarmland();
            try
            {
                uploadFarmland();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    protected String getParams()
    {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try
        {
            for (Farmland farmland : uploadFarmlands)
            {
                JSONObject farmJson = new JSONObject();
                farmJson.put("phone", farmland.getTel());
                farmJson.put("polygon", GeomToString.polygonToString(farmland.getFarmGeom()));
                farmJson.put("realName", farmland.getFarmName());
                farmJson.put("villageId", farmland.getAddress() + "");
                farmJson.put("time", farmland.getTime());
                jsonArray.put(farmJson);
            }
            jsonObject.putOpt("farmlands", jsonArray);
            jsonObject.put("upPhone", tel);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public void queryFarmland()
    {
        uploadFarmlands = farmlandSQL.queryCanUploadFarmland();
    }

    public void uploadFarmland() throws Exception
    {
        if(uploadFarmlands.size() == 0)
        {
            toast("没有需要上传的数据");
            return;
        }

        String json = getParams();
        FormBody requestBody = new FormBody.Builder().add("json", json).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful())
        {
            JSONObject jsonObject = new JSONObject(response.body().string());

            String info = (String) jsonObject.get("info");
            int status = (int) jsonObject.get("status");
            if ("OK".equals(info) && status == 0)
            {
                for (Farmland farmland : uploadFarmlands)
                {
                    farmlandSQL.updateUploadFarmland(farmland.getId() + "", "3");
                }
                toast("上传了" + uploadFarmlands.size() + "条数据");
            } else
            {
                toast("上传失败");
            }
        } else
        {
            toast("上传失败");
        }
    }

    public void toast(final String message)
    {
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MapManger.getInstance().getMap().getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
