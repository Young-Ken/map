package com.caobugs.gis.view.control;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.util.constants.ConstantFile;
import com.caobugs.gis.view.appview.adapter.BaseSpinnerAdapter;
import com.caobugs.gis.view.appview.adapter.PositionSpinnerAdapter;
import com.caobugs.gis.vo.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/7/13
 */
public class PositionSpinner extends LinearLayout implements  Spinner.OnItemSelectedListener
{
    private Context context;
    private PositionRunTimeData.Position position;
    private String positionID;
    private TextView positionText = null;
    private Spinner positionSpinner = null;
    private BaseSpinnerAdapter positionAdapter = null;
    private OkHttpClient client = null;
    private PositionRunTimeData data = null;
    private Handler callBackHandler = null;
    private  StringBuffer url = new StringBuffer(ConstantFile.URL_HTTP);
    private ArrayList<Position> initArray = new ArrayList<>();
    private View view = null;
    private ProgressBar progressBar = null;
    private Handler updateUI = null;

    public PositionSpinner(Context context)
    {
        super(context);
        this.context = context;
    }

    public PositionSpinner(Context context, Handler callBackHandler,
                           PositionRunTimeData.Position position, String positionID)
    {
        this(context);
        data = PositionRunTimeData.getInstance();
        initArray.add(new Position("0","请选择"));
        this.callBackHandler = callBackHandler;
        this.position = position;
        this.positionID = positionID;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.spinner_view_layout, this);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_position);
        positionAdapter = new PositionSpinnerAdapter(context, initArray);

        positionText = (TextView) view.findViewById(R.id.position_text);
        positionSpinner = (Spinner) view.findViewById(R.id.position_spinner);
        positionSpinner.setAdapter(positionAdapter);
        positionSpinner.setOnItemSelectedListener(this);
        createHandle();
        init();
    }
    public void createHandle()
    {
        updateUI = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                int what = msg.what;
                switch (what)
                {
                    case 0:
                        if (Looper.myLooper() == null)
                        {
                            Looper.prepare();
                        }
                        Toast.makeText(context, "加载省市数据失败", Toast.LENGTH_LONG).show();
                        break;
                }
                if (progressBar.getVisibility() == VISIBLE)
                {
                    progressBar.setVisibility(GONE);
                }
            }
        };
    }

    public void init()
    {


        url.append("/api/position/").append( position.name()).append(File.separator).append(position);
        switch (position)
        {
            case province:
                positionText.setText(R.string.province_name);
                break;
            case city:
                positionText.setText(R.string.city_name);
                break;
            case county:
                positionText.setText(R.string.county_name);
                break;
            case town:
                positionText.setText(R.string.town_name);
                break;
            case village:
                positionText.setText(R.string.village_name);
                break;
        }
        getTownData();
    }

    public OkHttpClient getClient()
    {
        if(client == null)
        {
            client = new OkHttpClient();
        }
        return client;
    }

    public void getTownData()
    {
        if(progressBar.getVisibility() == GONE)
        {
            progressBar.setVisibility(VISIBLE);
        }
        Request request = new Request.Builder().url(url.toString()).get().build();
        getClient().newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Message message = updateUI.obtainMessage();
                message.what = 0;
                updateUI.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                try
                {
                    JSONArray positionArray = new JSONArray(response.body().string());
                    if (positionArray.length() > 0)
                    {
                        initArray = new ArrayList<>();
                        initArray.add(new Position("0","请选择"));
                    }

                    for (int i = 0; i < positionArray.length(); i++)
                    {
                        JSONObject positionObject = positionArray.getJSONObject(i);
                        Position position = new Position(positionObject.getString("positionId"), positionObject.getString("positionName"));
                        initArray.add(position);
                    }
                    positionAdapter.setArrayList(initArray);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Message message = updateUI.obtainMessage();
                message.what = 1;
                updateUI.sendMessage(message);
            }
        });
    }




    public void init(PositionRunTimeData.Position position, String positionID)
    {
        this.position = position;
        this.positionID = positionID;
        reset();
        init();
    }

    public void reset()
    {
        initArray = new ArrayList<>();
        initArray.add(new Position("0","请选择"));
        positionAdapter = new PositionSpinnerAdapter(context, initArray);
        positionSpinner.setAdapter(positionAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int p, long id)
    {
        if(p == 0)
        {
            return;
        }

        Message message = callBackHandler.obtainMessage();
        message.what = position.ordinal();

        PositionRunTimeData positionRunTimeData = PositionRunTimeData.getInstance();
        Position temp = initArray.get(p);
        switch (position)
        {
            case province:
                positionRunTimeData.setProvinceName(temp.getName());
                break;
            case city:
                positionRunTimeData.setCityName(temp.getName());
                break;
            case county:
                positionRunTimeData.setCountyName(temp.getName());
                break;
            case town:
                positionRunTimeData.setTownName(temp.getName());
                break;
            case village:
                positionRunTimeData.setVillageName(temp.getName());
                positionRunTimeData.setVillageID(temp.getPositionID());
                break;
        }
        message.arg1 = Integer.parseInt(temp.getPositionID());
        callBackHandler.dispatchMessage(message);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

}
