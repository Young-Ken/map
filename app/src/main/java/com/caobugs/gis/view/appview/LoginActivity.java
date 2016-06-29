package com.caobugs.gis.view.appview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.util.constants.ConstantFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/6/3
 */
public class LoginActivity extends Activity implements View.OnClickListener
{
    private EditText telText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        telText = (EditText) findViewById(R.id.tel_text);

        Button button = (Button) findViewById(R.id.submit_user_tel);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.submit_user_tel:
                Pattern p = Pattern.compile(ConstantFile.TEL_CHECK);
                Matcher m = p.matcher(telText.getText().toString());
                if (!m.matches())
                {
                    Toast.makeText(this.getApplication(), "手机号码不对，请仔细检查", Toast.LENGTH_LONG).show();
                } else
                {
                    SharedPreferences settings = getSharedPreferences("setting", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("tel", telText.getText().toString());
                    editor.commit();
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Toast.makeText(this.getApplication(), "请正确添加手机号码", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
