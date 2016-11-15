package com.caobugs.gis.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/30
 */
public class LogUtil
{

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int SHOWLEVEL = NOTHING;
    public static final String SEPARATOR = ",";

    public static void v(String message)
    {
       v(null,message);
    }

    public static void v(String tag, String message)
    {
       log(LogUtil.VERBOSE,tag,message);
    }

    public static void d(String message)
    {
       d(null,message);
    }

    public static void d(String tag, String message)
    {
        log(LogUtil.DEBUG,tag,message);
    }

    public static void i(String message)
    {
       i(null,message);
    }

    public static void i(String tag, String message)
    {
       log(LogUtil.INFO, tag, message);
    }

    public static void w(String message)
    {
        w(null, message);
    }

    public static void w(String tag, String message)
    {
      log(LogUtil.WARN, tag, message);
    }

    public static void e(String tag, String message)
    {
       log(LogUtil.ERROR, tag, message);
    }

    private static void log(int logLevel,String tag, String message)
    {
        if(SHOWLEVEL > logLevel)
        {
            return;
        }
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

        if(TextUtils.isEmpty(tag))
        {
            tag = getDefaultTag(stackTraceElement);
        }

        switch (logLevel)
        {
            case 1:
                Log.v(tag, getLogInfo(stackTraceElement) + message);
                break;
            case 2:
                Log.d(tag, getLogInfo(stackTraceElement) + message);
                break;
            case 3:
                Log.i(tag, getLogInfo(stackTraceElement) + message);
                break;
            case 4:
                Log.w(tag, getLogInfo(stackTraceElement) + message);
                break;
            case 5:
                Log.e(tag, getLogInfo(stackTraceElement) + message);
                break;
            default:
                Log.i(tag, getLogInfo(stackTraceElement) + message);
                break;
        }
    }

    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     * 则TAG为MainActivity
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement)
    {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        return  stringArray[0];
    }

    /**
     * 输出日志所包含的信息
     */
    public static String getLogInfo(StackTraceElement stackTraceElement)
    {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取生日输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("threadID=").append(threadID).append(SEPARATOR);
        logInfoStringBuilder.append("threadName=").append(threadName).append(SEPARATOR);
        logInfoStringBuilder.append("fileName=").append(fileName).append(SEPARATOR);
        logInfoStringBuilder.append("className=").append(className).append(SEPARATOR);
        logInfoStringBuilder.append("methodName=").append(methodName).append(SEPARATOR);
        logInfoStringBuilder.append("lineNumber=").append(lineNumber);
        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }
}
