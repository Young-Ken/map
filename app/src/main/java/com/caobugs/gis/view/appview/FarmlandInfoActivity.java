package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.enumeration.ConstantResult;
import com.caobugs.gis.location.GpsInfo;
import com.caobugs.gis.vo.Farmland;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/5
 */
public class FarmlandInfoActivity extends Activity implements View.OnClickListener
{
    private EditText editTextTel = null;
    private EditText editTextFarmName = null;
    private EditText editTextAddress = null;
    private EditText editTextarea = null;

    private Button buttonSubmit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmland_info_layout);
        fillPosition();

        editTextTel = (EditText) findViewById(R.id.farmland_info_tel);

        editTextFarmName = (EditText) findViewById(R.id.farmland_info_name);

        editTextAddress = (EditText) findViewById(R.id.farmland_info_address);

        editTextarea = (EditText) findViewById(R.id.farmland_info_area);

        buttonSubmit = (Button) findViewById(R.id.submit_farmland);
        buttonSubmit.setOnClickListener(this);

    }

    public void fillPosition()
    {
        GpsInfo gpsInfo = GpsInfo.getInstance();

        EditText province = (EditText) findViewById(R.id.province_name);
        province.setText(gpsInfo.getProvince());

        EditText city = (EditText) findViewById(R.id.city_name);
        city.setText(gpsInfo.getCity());

        EditText county = (EditText) findViewById(R.id.county_name);
        county.setText(gpsInfo.getCounty());

    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.submit_farmland:
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putString(Farmland.TEL, editTextTel.getText().toString());
                bundle.putString(Farmland.FARMNAME, editTextFarmName.getText().toString());
                bundle.putString(Farmland.ADDRESS, editTextAddress.getText().toString());
                intent.putExtras(bundle);
                this.setResult(ConstantResult.RESULT_OK, intent);
                this.finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        setResult(ConstantResult.RESULT_FAILD);
        super.onBackPressed();
    }
}
