package com.caobugs.gis.view.server;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.caobugs.gis.data.db.sql.FarmlandSQL;
import com.caobugs.gis.util.ApplicationContext;
import com.caobugs.gis.util.GeomToString;
import com.caobugs.gis.view.appview.LoginActivity;
import com.caobugs.gis.vo.Farmland;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/6/13
 */
public class UploadServer extends IntentService
{
    private FarmlandSQL farmlandSQL = null;
    private ArrayList<Farmland> uploadFarmlands = null;
    private String url = "http://192.168.1.166/api/farmland/uploadInfo";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String tel = "";
    public UploadServer()
    {
        super("UploadServer");

    }

    public void checkCollectionTel()
    {
        SharedPreferences settings = getSharedPreferences("setting", 0);
        String tel = settings.getString("tel","");

        if(!tel.equals(""))
        {
          this.tel = tel;
        }
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        mRequestQueue = ApplicationContext.getRequestQueue();
        farmlandSQL = new FarmlandSQL();
        checkCollectionTel();

        if (tel.equals(""))
        {
            Intent dialogIntent = new Intent(getBaseContext(), LoginActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(dialogIntent);
        }else
        {
            queryFarmland();
            uploadFarmland();
        }


    }

    public void queryFarmland()
    {
        uploadFarmlands = farmlandSQL.queryCanUploadFarmland();
    }

    public void uploadFarmland()
    {

        mStringRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String info = (String) jsonObject.get("info");
                            int status = (int) jsonObject.get("status");
                            if ("OK".equals(info) && status == 0)
                            {
                                for (Farmland farmland : uploadFarmlands)
                                {
                                    farmlandSQL.updateUploadFarmland(farmland.getId() + "", "3");
                                }
                                Toast.makeText(getApplicationContext(), "上传了"+uploadFarmlands.size()+"条数据", Toast.LENGTH_LONG).show();
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener()
        {

            @Override

            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
            }
        })
        {
            // 携带参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> hash = new HashMap<>();
                JSONObject jsonObject = new JSONObject();
                //ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
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
                    jsonObject.put("upPhone", "11111111111");
                    jsonObject.put("uuid", "54464654654654654666565");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                hash.put("json", jsonObject.toString());
                return hash;
            }
        };
        mRequestQueue.add(mStringRequest);
    }
}

