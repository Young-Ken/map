package com.caobugs.gis.view.server;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.caobugs.gis.R;
import com.caobugs.gis.util.constants.ConstantFile;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.vo.RunTimeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/7/7
 */
public class UpdateAppService extends Service
{

    private static final int notificationID = 199;
    private File updateDir = null;
    private File updateFile = null;
    private NotificationCompat.Builder builder = null;
    private NotificationManager manager = null;
    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;
    private StringBuffer url = new StringBuffer(ConstantFile.URL_HTTP);

    // 通知栏跳转Intent
    //private Intent updateIntent = null;
    private Version version = null;

    private Intent intent = null;


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        this.intent = intent;
        url.append("/api/version");
        new ServerVersionThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void notification()
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            StringBuffer downPath = new StringBuffer(File.separator);
            downPath.append(ConstantFile.ROOT).append(File.separator).append(ConstantFile.APP).append(File.separator).append(ConstantFile.DOWNLOAD);
            updateDir = new File(Environment.getExternalStorageDirectory(), downPath.toString());
            updateFile = new File(updateDir.getPath(), "田块采集_" + version.getVersionNumber() + "_" + version.getVersionCode() + ".apk");
        }

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("正在下载新版田块采集APP...")
                .setContentText("0%")
                .setTicker("田块采集更新啦 O(∩_∩)O")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.logo)
                .setProgress(100, 0, false);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        manager.notify(notificationID, builder.build());

        new Thread(new updateRunnable()).start();
    }


    public void updateVersion()
    {
        if (!version.getForceUpdate())
        {
            Message message = showAlertDialog.obtainMessage();
            showAlertDialog.sendMessage(message);
        } else
        {
            notification();
        }
    }

    public Handler showAlertDialog = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            JSONArray messages = version.getUpdateInfo();
            StringBuffer messageBuffer = new StringBuffer();
            for (int i = 0; i < messages.length(); i++)
            {
                try
                {
                    messageBuffer.append(messages.getString(i));
                    messageBuffer.append("\n");

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            new AlertDialog.Builder(MapManger.getInstance().getMap().getContext())
                    .setTitle("系统提示")
                    .setMessage(messageBuffer.toString())
                    .setPositiveButton("更新", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    notification();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    stop();
                }
            }).show();
        }
    };

    public void getServiceVersionName()
    {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try
        {
            packInfo = packageManager.getPackageInfo(getPackageName(), PackageManager. GET_SERVICES);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        if(version != null)
        {
            if (packInfo.versionCode < version.getVersionCode())
            {
                updateVersion();
            }
        }
    }


    class updateRunnable implements Runnable
    {
        Message message = updateHandler.obtainMessage();

        public void run()
        {
            message.what = DOWNLOAD_COMPLETE;
            try
            {
                if (!updateDir.exists())
                {
                    updateDir.mkdirs();
                }
                if (!updateFile.exists())
                {
                    updateFile.createNewFile();
                }

                long downloadSize = downloadUpdateFile(
                       version.getDownloadUrl(),
                        updateFile);
                if (downloadSize > 0)
                {
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                // 下载失败
                updateHandler.sendMessage(message);

            }
        }
    }


    public long downloadUpdateFile(String downloadUrl, File saveFile)
            throws Exception
    {
        // 这样的下载代码很多，我就不做过多的说明
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try
        {
            RunTimeData.getInstance().setDowningAPP(true);
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection
                    .setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0)
            {
                httpConnection.setRequestProperty("RANGE", "bytes="
                        + currentSize + "-");
            }
            httpConnection.setConnectTimeout(50000);
            httpConnection.setReadTimeout(100000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404)
            {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0)
            {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;

                if ((downloadCount == 0)
                        || (int) (totalSize * 100 / updateTotalSize) - 5 > downloadCount)
                {
                    downloadCount += 5;
                    builder.setContentText(downloadCount+"%");
                    builder.setProgress(100, downloadCount, false);
                    manager.notify(notificationID, builder.build());
                }
            }

            builder.setProgress(100,100, false);
            manager.notify(notificationID, builder.build());

        } finally
        {
            if (httpConnection != null)
            {
                httpConnection.disconnect();
            }
            if (is != null)
            {
                is.close();
            }
            if (fos != null)
            {
                fos.close();
            }
        }
        return totalSize;
    }


    private Handler updateHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case DOWNLOAD_COMPLETE:
                    Toast.makeText(getApplicationContext(), "下载成功！",Toast.LENGTH_LONG).show();
                    Uri uri = Uri.fromFile(updateFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri,
                            "application/vnd.android.package-archive");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case DOWNLOAD_FAIL:
                    Toast.makeText(getApplicationContext(), "下载失败！",Toast.LENGTH_LONG).show();
                    break;
            }
            stop();
        }
    };

    public void stop()
    {
        if(intent != null)
        {
            stopService(intent);
        }
        if (manager != null)
        {
            manager.cancel(notificationID);
        }

        RunTimeData.getInstance().setDowningAPP(false);
    }

    private OkHttpClient client = null;

    class ServerVersionThread extends Thread
    {
        @Override
        public void run()
        {
            client = new OkHttpClient();
            final Request request = new Request.Builder().url(url.toString()).build();
            client.newCall(request).enqueue(new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    toast("请求新版本失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    try
                    {
                        if (response.code() == 200)
                        {
                            version = new Version();
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            version.setDownloadUrl(jsonObject.getString("downloadUrl"));
                            version.setForceUpdate(jsonObject.getBoolean("forceUpdate"));
                            version.setVersionCode(jsonObject.getInt("versionCode"));
                            version.setVersionNumber(jsonObject.getString("versionNumber"));
                            version.setUpdateInfo(jsonObject.getJSONArray("updateInfo"));
                            getServiceVersionName();
                        } else
                        {
                            toast("请求新版本失败");
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void toast(final String message)
    {
        stopService(intent);
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MapManger.getInstance().getMap().getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
class Version
{
    private String downloadUrl = null;
    private boolean forceUpdate = false;
    private JSONArray updateInfo = null;
    private int versionCode = -1;
    private String versionNumber = null;

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }


    public void setForceUpdate(boolean forceUpdate)
    {
        this.forceUpdate = forceUpdate;
    }

    public boolean getForceUpdate()
    {
        return forceUpdate;
    }
    public int getVersionCode()
    {
        return versionCode;
    }

    public String getVersionNumber()
    {
        return versionNumber;
    }

    public JSONArray getUpdateInfo()
    {
        return updateInfo;
    }

    public void setUpdateInfo(JSONArray updateInfo)
    {
        this.updateInfo = updateInfo;
    }

    public void setVersionCode(int versionCode)
    {
        this.versionCode = versionCode;
    }

    public void setVersionNumber(String versionNumber)
    {
        this.versionNumber = versionNumber;
    }
}

