package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.data.db.sql.PositionSQL;
import com.caobugs.gis.util.constants.ConstantFile;
import com.caobugs.gis.util.constants.ConstantResult;
import com.caobugs.gis.location.GpsInfo;
import com.caobugs.gis.view.appview.adapter.BaseSpinnerAdapter;
import com.caobugs.gis.view.appview.adapter.PositionSpinnerAdapter;
import com.caobugs.gis.view.control.PositionRunTimeData;
import com.caobugs.gis.view.control.PositionSpinner;
import com.caobugs.gis.vo.Farmland;
import com.caobugs.gis.vo.Position;
import com.caobugs.gis.vo.RunTimeData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/5
 */
public class FarmlandInfoActivity extends Activity implements View.OnClickListener

{
    private EditText editTextTel = null;
    private EditText editTextFarmName = null;
    private Button buttonSubmit = null;
    private PositionRunTimeData positionRunTimeData = null;
    private Context context = null;
    private LinearLayout spinnerLayout = null;
    private LinearLayout positionInfoLayout = null;
    private LinkedHashMap<String, PositionSpinner> positionSpinners = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        setContentView(R.layout.farmland_info_layout);
        initView();
    }

    public void initView()
    {
        positionRunTimeData = PositionRunTimeData.getInstance();
        editTextTel = (EditText) findViewById(R.id.farmland_info_tel);
        editTextFarmName = (EditText) findViewById(R.id.farmland_info_name);
        buttonSubmit = (Button) findViewById(R.id.submit_farmland);
        spinnerLayout = (LinearLayout) findViewById(R.id.position_spinner_layout);
        positionInfoLayout = (LinearLayout) findViewById(R.id.position_info_layout);
        buttonSubmit.setOnClickListener(this);

        if(positionRunTimeData.getVillageID() == null)
        {
            initPosition();
            positionInfoLayout.setVisibility(View.GONE);
        } else
        {
            fillPosition();
            spinnerLayout.setVisibility(View.GONE);
        }
    }


    private void initPosition()
    {
        PositionSpinner provinceSpinner = new PositionSpinner(context, handler, PositionRunTimeData.Position.province, "");
        spinnerLayout.addView(provinceSpinner);
        positionSpinners.put("0", provinceSpinner);
    }

    Handler handler = new Handler()
    {
        @Override
        public void dispatchMessage(Message message)
        {
            PositionSpinner layout = null;
            String positionID = message.arg1 + "";
            layout = positionSpinners.get(message.what + 1 + "");

            if (message.what < 4)
            {
                if (layout != null)
                {
                    resetLevel(message.what);
                    layout.init(PositionRunTimeData.Position.values()[message.what + 1], positionID);
                } else
                {
                    layout = new PositionSpinner(context, handler, PositionRunTimeData.Position.values()[message.what + 1], positionID);
                    spinnerLayout.addView(layout);
                    positionSpinners.put(message.what + 1 + "", layout);
                }
            }
        }
    };

    public void resetLevel(int position)
    {
        Iterator<Map.Entry<String, PositionSpinner>> iterator = positionSpinners.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<String, PositionSpinner> map = iterator.next();
            String key = map.getKey();
            if (Integer.parseInt(key) > position)
            {
                PositionSpinner temp = map.getValue();
                temp.reset();
            }
        }
    }

    public void fillPosition()
    {
        EditText province = (EditText) findViewById(R.id.province_name);
        province.setText(positionRunTimeData.getProvinceName());

        EditText city = (EditText) findViewById(R.id.city_name);
        city.setText(positionRunTimeData.getCityName());

        EditText county = (EditText) findViewById(R.id.county_name);
        county.setText(positionRunTimeData.getCountyName());

        EditText town = (EditText) findViewById(R.id.town_name);
        town.setText(positionRunTimeData.getTownName());

        EditText village = (EditText) findViewById(R.id.village_name);
        village.setText(positionRunTimeData.getVillageName());
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        String villageID = positionRunTimeData.getVillageID();

        if (villageID == null)
        {
            Toast.makeText(FarmlandInfoActivity.this, "请输入对应的参数", Toast.LENGTH_LONG).show();
            return;
        }
        switch (id)
        {
            case R.id.submit_farmland:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if (!editTextTel.getText().toString().equals(""))
                {
                    Pattern p = Pattern.compile(ConstantFile.TEL_CHECK);
                    Matcher m = p.matcher(editTextTel.getText().toString());
                    if (!m.matches())
                    {
                        Toast.makeText(FarmlandInfoActivity.this, "手机号码不对，请仔细检查", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                bundle.putString(Farmland.TEL, editTextTel.getText().toString());
                bundle.putString(Farmland.FARMNAME, editTextFarmName.getText().toString());
                bundle.putString(Farmland.ADDRESS, villageID);
                intent.putExtras(bundle);
                this.setResult(ConstantResult.RESULT_OK, intent);
                this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        setResult(ConstantResult.RESULT_FAILD);
        super.onBackPressed();
    }
}
