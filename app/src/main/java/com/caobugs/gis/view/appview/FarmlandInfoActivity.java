package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.data.db.sql.PositionSQL;
import com.caobugs.gis.enumeration.ConstantResult;
import com.caobugs.gis.location.GpsInfo;
import com.caobugs.gis.tool.ApplicationContext;
import com.caobugs.gis.view.appview.adapter.BaseSpinnerAdapter;
import com.caobugs.gis.view.appview.adapter.PositionSpinnerAdapter;
import com.caobugs.gis.vo.Farmland;
import com.caobugs.gis.vo.Position;
import com.caobugs.gis.vo.RunTimeData;

import java.util.ArrayList;


/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/5
 */
public class FarmlandInfoActivity extends Activity implements View.OnClickListener, Spinner.OnItemSelectedListener
{
    private EditText editTextTel = null;
    private EditText editTextFarmName = null;
    private Button buttonSubmit = null;
    private Spinner town_name = null;
    private Spinner village_name = null;
    private String villageID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmland_info_layout);

        editTextTel = (EditText) findViewById(R.id.farmland_info_tel);
        editTextFarmName = (EditText) findViewById(R.id.farmland_info_name);
        buttonSubmit = (Button) findViewById(R.id.submit_farmland);
        town_name = (Spinner) findViewById(R.id.town_name);
        village_name = (Spinner) findViewById(R.id.village_name);


        buttonSubmit.setOnClickListener(this);
        town_name.setOnItemSelectedListener(this);
        village_name.setOnItemSelectedListener(FarmlandInfoActivity.this);

        fillPosition();
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

        ArrayList<Position> town = null;
        if(RunTimeData.getInstance().getTown() != null && RunTimeData.getInstance().getTown().size() > 0)
        {
            town = RunTimeData.getInstance().getTown();
        } else {
            town = new PositionSQL().selectByCounty(county.getText().toString());
            RunTimeData.getInstance().setTown(town);
        }

        BaseSpinnerAdapter baseSpinnerAdapter = new PositionSpinnerAdapter(this, town);
        town_name.setAdapter(baseSpinnerAdapter);
        if(RunTimeData.getInstance().getSelectedTown() != -1)
        {
            town_name.setSelection(RunTimeData.getInstance().getSelectedTown(),true);
        }
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if(editTextTel.getText().toString().equals("") ||
                editTextTel.getText().toString().equals("")
                || villageID == null || villageID.equals("-1"))
        {
            Toast.makeText(FarmlandInfoActivity.this,"请输入对应的参数",Toast.LENGTH_LONG).show();
            return;
        }
        switch (id)
        {
            case R.id.submit_farmland:
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
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
    public void onBackPressed() {
        setResult(ConstantResult.RESULT_FAILD);
        super.onBackPressed();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (parent == null)
        {
            return;
        }
        int parentId = parent.getId();
        switch (parentId)
        {
            case R.id.town_name:
                Position town = (Position) parent.getAdapter().getItem(position);
                if(!town.getPositionID().equals("-1"))
                {
                    RunTimeData.getInstance().setSelectedTown(position);
                }
                ArrayList<Position> village = null;

                if(RunTimeData.getInstance().getVillage() != null && RunTimeData.getInstance().getVillage().size() > 0)
                {
                    village = RunTimeData.getInstance().getVillage();
                } else {
                    village =  new PositionSQL().selectByTown(town.getPositionID());
                    RunTimeData.getInstance().setVillage(village);
                }

                BaseSpinnerAdapter baseSpinnerAdapter = new PositionSpinnerAdapter(this, village);
                village_name.setAdapter(baseSpinnerAdapter);
                if(RunTimeData.getInstance().getSelectedVillage() != -1)
                {
                    village_name.setSelection(RunTimeData.getInstance().getSelectedVillage(),true);
                }

                break;
            case R.id.village_name:
                Position v = (Position) parent.getAdapter().getItem(position);
                if(!v.getPositionID().equals("-1"))
                {
                    RunTimeData.getInstance().setSelectedVillage(position);
                }
                villageID = v.getPositionID();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
