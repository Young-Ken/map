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
public class FarmlandInfoActivity extends Activity implements
        View.OnClickListener, Spinner.OnItemSelectedListener
{
    private EditText editTextTel = null;
    private EditText editTextFarmName = null;
    private Button buttonSubmit = null;
    private Spinner town_name = null;
    private Spinner village_name = null;
    private String villageID = null;
    private RunTimeData runTimeData = null;
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
        town_name.setSelection(0, true);
        village_name.setOnItemSelectedListener(this);
        village_name.setSelection(0, true);

        runTimeData = RunTimeData.getInstance();
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
        if (runTimeData.getTown() != null && runTimeData.getTown().size() > 0)
        {
            town = runTimeData.getTown();
        } else
        {
            town = new PositionSQL().selectByCounty(county.getText().toString());
            runTimeData.setTown(town);
        }

        BaseSpinnerAdapter baseSpinnerAdapter = new PositionSpinnerAdapter(this, town);
        town_name.setAdapter(baseSpinnerAdapter);
        if (runTimeData.getSelectedTown() != -1)
        {
            town_name.setSelection(runTimeData.getSelectedTown(), true);
        }

        if(runTimeData.getVillage() != null && runTimeData.getVillage().size() > 0)
        {
            BaseSpinnerAdapter baseSpinnerAdapter1 = new PositionSpinnerAdapter(this, runTimeData.getVillage());
            village_name.setAdapter(baseSpinnerAdapter1);
            village_name.setSelection(runTimeData.getSelectedVillage(), true);
            if(runTimeData.getSelectedVillage() > 0)
            {
                villageID = runTimeData.getVillage().get(runTimeData.getSelectedVillage()).getPositionID();
            }else {
                villageID = null;
            }

        }
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (editTextTel.getText().toString().equals("") ||
                editTextTel.getText().toString().equals("") || villageID == null || villageID.equals("-1"))
        {
            Toast.makeText(FarmlandInfoActivity.this, "请输入对应的参数", Toast.LENGTH_LONG).show();
            return;
        }
        switch (id)
        {
            case R.id.submit_farmland:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
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


                if(position == runTimeData.getSelectedTown())
                {
                    break;
                }
                Position town = (Position) parent.getAdapter().getItem(position);
                if (!town.getPositionID().equals("-1") || position == 0)
                {
                    runTimeData.setSelectedTown(position);
                    runTimeData.setSelectedVillage(-1);
                    runTimeData.setVillage(null);
                }
                ArrayList<Position> village = null;

                if (runTimeData.getVillage() != null && runTimeData.getVillage().size() > 0)
                {
                    village = runTimeData.getVillage();
                } else
                {
                    village = new PositionSQL().selectByTown(town.getPositionID());
                    runTimeData.setVillage(village);
                }

                BaseSpinnerAdapter baseSpinnerAdapter = new PositionSpinnerAdapter(this, village);
                village_name.setAdapter(baseSpinnerAdapter);
                if (runTimeData.getSelectedVillage() != -1)
                {
                    village_name.setSelection(runTimeData.getSelectedVillage(), true);
                }

                break;
            case R.id.village_name:
                if(position == 0)
                {
                    villageID = null;
                }

                if(position == runTimeData.getSelectedVillage())
                {
                    break;
                }

                Position v = (Position) parent.getAdapter().getItem(position);
                if (!v.getPositionID().equals("-1"))
                {
                    runTimeData.setSelectedVillage(position);
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
