package com.caobugs.gis.location.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.caobugs.gis.util.LogUtil;
import com.caobugs.gis.util.TAG;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/6/2
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver
{

    private boolean wifiNetwork = false;
    private boolean gprsNetwork = false;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        NetworkInfo networkInfo = null;
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction()))
        {
            networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_NETWORK_INFO, 0);
            switch (wifiState)
            {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
        {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

            if(info != null)
            {
                if (info.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (wifi.isConnected())
                    {
                        LogUtil.e(TAG.WIFI,"链接成功");
                        wifiNetwork = true;
                    } else
                    {
                        LogUtil.e(TAG.WIFI,"断开链接成功");
                        wifiNetwork = false;
                    }
                } else if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if (gprs.isConnected())
                    {
                        LogUtil.e(TAG.WIFI,"链接成功");
                        gprsNetwork = true;
                    } else
                    {
                        LogUtil.e(TAG.WIFI,"断开链接成功");
                        gprsNetwork = false;
                    }
                }
            }
        }
    }

}
