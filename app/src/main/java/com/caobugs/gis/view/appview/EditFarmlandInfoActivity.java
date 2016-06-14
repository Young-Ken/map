package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.util.constants.ConstantResult;
import com.caobugs.gis.vo.Farmland;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/6/10
 */
public class EditFarmlandInfoActivity extends Activity implements View.OnClickListener
{


    private EditText editTextTel = null;
    private EditText editTextName = null;
    private Button submit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_farmland_layout);

        editTextTel = (EditText) findViewById(R.id.edit_farmland_info_tel);
        editTextName = (EditText) findViewById(R.id.edit_farmland_info_name);
        submit = (Button) findViewById(R.id.edit_submit_farmland);
        submit.setOnClickListener(this);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            editTextTel.setText(bundle.getString(Farmland.TEL));
            editTextName.setText(bundle.getString(Farmland.FARMNAME));
        }
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if (editTextTel.getText().toString().equals("") ||
                editTextName.getText().toString().equals(""))
        {
            Toast.makeText(EditFarmlandInfoActivity.this, "请输入对应的参数", Toast.LENGTH_LONG).show();
            return;
        } else {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(editTextTel.getText().toString());
            if (!m.matches())
            {
                Toast.makeText(EditFarmlandInfoActivity.this, "手机号码不对，请仔细检查", Toast.LENGTH_LONG).show();
                return;
            }
        }

        switch (id)
        {
            case R.id.edit_submit_farmland:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Farmland.TEL, editTextTel.getText().toString());
                bundle.putString(Farmland.FARMNAME, editTextName.getText().toString());
                intent.putExtras(bundle);
                this.setResult(ConstantResult.RESULT_UPDATE, intent);
                this.finish();
                break;
        }
    }
}
