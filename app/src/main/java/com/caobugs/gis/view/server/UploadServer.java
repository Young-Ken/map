package com.caobugs.gis.view.server;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/6/13
 */
public class UploadServer
{
//    private FarmlandSQL farmlandSQL = null;
//    private ArrayList<Farmland> uploadFarmlands = null;
//    private String url = "http://m.farm-keeper.com/api/farmland/uploadInfo";
//   //private String url = "http:www.baidu.com";
//    private TelephonyManager telephonyManager = null;
//    private RequestQueue mRequestQueue;
//    private StringRequest mStringRequest;
//    private String tel = "";
//    private String body;
//
//    public UploadServer()
//    {
//        super("UploadServer");
//    }
//
//    public void checkCollectionTel()
//    {
//        SharedPreferences settings = getSharedPreferences("setting", 0);
//        String tel = settings.getString("tel", "");
//
//        if (!tel.equals(""))
//        {
//            this.tel = tel;
//        }
//    }
//
//
//    @Override
//    protected void onHandleIntent(Intent intent)
//    {
//        mRequestQueue = ApplicationContext.getRequestQueue();
//        farmlandSQL = new FarmlandSQL();
//        checkCollectionTel();
//
//        if (tel.equals(""))
//        {
//            Intent dialogIntent = new Intent(getBaseContext(), LoginActivity.class);
//            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getApplication().startActivity(dialogIntent);
//        } else
//        {
//            telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
//            queryFarmland();
//            try
//            {
//                Thread.sleep(10000);
//            } catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            uploadFarmland();
//        }
//
//
//    }
//
//    public void queryFarmland()
//    {
//        uploadFarmlands = farmlandSQL.queryCanUploadFarmland();
//    }
//
//    public void uploadFarmland()
//    {
//
//        mStringRequest = new StringRequest(Request.Method.POST, url,
//
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        try
//                        {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String info = (String) jsonObject.get("info");
//                            int status = (int) jsonObject.get("status");
//                            if ("OK".equals(info) && status == 0)
//                            {
//                                for (Farmland farmland : uploadFarmlands)
//                                {
//                                    farmlandSQL.updateUploadFarmland(farmland.getId() + "", "3");
//                                }
//                                Toast.makeText(getApplicationContext(), "上传了" + uploadFarmlands.size() + "条数据", Toast.LENGTH_LONG).show();
//                            } else
//                            {
//                                Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }, new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
//            }
//        })
//        {
//            // 携带参数
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String, String> hash = new HashMap<>();
//                JSONObject jsonObject = new JSONObject();
//                JSONArray jsonArray = new JSONArray();
//                try
//                {
//                    for (Farmland farmland : uploadFarmlands)
//                    {
//                        JSONObject farmJson = new JSONObject();
//                        farmJson.put("phone", farmland.getTel());
//                        farmJson.put("polygon", GeomToString.polygonToString(farmland.getFarmGeom()));
//                        farmJson.put("realName", farmland.getFarmName());
//                        farmJson.put("villageId", farmland.getAddress() + "");
//                        farmJson.put("time", farmland.getTime());
//                        jsonArray.put(farmJson);
//                    }
//                    jsonObject.putOpt("farmlands", jsonArray);
//                    jsonObject.put("upPhone", tel);
//                } catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//                hash.put("json", jsonObject.toString());
//                return hash;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError
//            {
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//               // headers.put("abc", "value");
//                return headers;
//            }
//
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return body != null ? body.getBytes() : null;
//            }
//        };
//        mRequestQueue.add(mStringRequest);
//        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                200 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
}

