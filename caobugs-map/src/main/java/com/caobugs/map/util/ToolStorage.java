package com.caobugs.map.util;

import android.os.Environment;
import android.os.StatFs;

import com.caobugs.map.config.ConfigFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @since 2015/8/25
 * @version 0.1
 */

public class ToolStorage {
    private static String SDPath = null;
    private static String TFPath = null;
    private static String mapCachePath = null;
    private static String dbWorkSpace = null;
    private static List<String> canUserPath = null;
    private static File internalFile = null;
    public static String internalMapCachePath = null;
    public static String extendMapCachePath = null;

    /**
     * 判断SDCard是否存在
     *
     * @return true 存在 false 不存在
     */
    public static boolean isInternalCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 取得sd路径
     *
     * @return File
     */
    public static File getInternalFile() {
        if (internalFile == null) {
            internalFile = isInternalCard() ? Environment.getExternalStorageDirectory() : null;
        }
        return internalFile;
    }

    public static String getInternalMapCachePath() {
        if (internalMapCachePath == null) {
            StringBuffer imp = new StringBuffer(getInternalFile().getPath());
            imp.append(File.separator)
                    .append(ConfigFile.ROOT)
                    .append(File.separator)
                    .append(ConfigFile.MAP_CACHE);
            internalMapCachePath = imp.toString();
        }
        return internalMapCachePath;
    }

    public static String getExtendMapCachePath() {
        if (extendMapCachePath == null) {
            if (getCanUserFile().size() >= 2) {
                StringBuffer imp = new StringBuffer(getCanUserFile().get(1));
                imp.append(File.separator)
                        .append(ConfigFile.MAP_CACHE_ROOT)
                        .append(File.separator)
                        .append(ConfigFile.MAP_CACHE);
                extendMapCachePath = imp.toString();
            }
        }
        return extendMapCachePath;
    }

    private static StatFs getStatFs() {
        File file = getInternalFile();
        return file != null ? new StatFs(file.getPath()) : null;
    }


    /**
     * 得到sdcard剩余空间
     *
     * @return 剩余空间字节数目
     */
    public static long getSDFreeSize() {
        StatFs sf = getStatFs();
        if (sf == null) {
            return -1;
        }
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        return (freeBlocks * blockSize) / 1048576;  //1048576 = 1024*1024
    }

    public static long getSDAllSize() {
        StatFs sf = getStatFs();
        if (sf == null)
            return -1;
        long blockSize = sf.getBlockSize();
        long allBlocks = sf.getBlockCount();
        return (allBlocks * blockSize) / 1048576; //1048576 = 1024*1024
    }


    public static List<String> getExtSDCardPaths() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED) && extFile.exists() && extFile.isDirectory() && extFile.canWrite()) {
            paths.add(extFile.getAbsolutePath());
        }
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                if ((!line.contains("fat") && !line.contains("fuse") && !line.contains("storage")) || line.contains("secure") || line.contains("asec") || line.contains("firmware") || line.contains("shell") || line.contains("obb") || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data") || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory() || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile.getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                paths.add(mountPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    public static String getMapCachePath() {
        if (mapCachePath == null) {
            List<String> canUserFile = getCanUserFile();
            StringBuffer tempPath = null;
            if (canUserFile.size() == 1) {
                tempPath = new StringBuffer(canUserFile.get(0));
                tempPath.append(File.separator).append(ConfigFile.MAP_CACHE_ROOT);
                if (new File(tempPath.toString()).isDirectory()) {
                    mapCachePath = canUserFile.get(0);
                }
            } else {
                tempPath = new StringBuffer(canUserFile.get(1));
                tempPath.append(File.separator).append(ConfigFile.MAP_CACHE_ROOT);
                if (new File(tempPath.toString()).isDirectory()) {
                    mapCachePath = canUserFile.get(1);
                }
            }
        }
        return mapCachePath;
    }

    public static File getMapCacheFile() {
        return new File(getMapCachePath());
    }

    public static String getDBPath() {
        if (dbWorkSpace == null) {
            StringBuffer imp = new StringBuffer(getInternalFile().getPath());
            imp.append(File.separator)
                    .append(ConfigFile.ROOT)
                    .append(File.separator)
                    .append(ConfigFile.DB_FILE);
            dbWorkSpace = imp.toString();
        }
        return dbWorkSpace;
    }

    public static synchronized List<String> getCanUserFile() {
        if (canUserPath == null) {
            List<String> cardArrayList = getExtSDCardPaths();
            if (cardArrayList == null) {
                return new ArrayList<>();
            } else {
                if (cardArrayList.size() >= 1) {
                    canUserPath = cardArrayList;
                } else {
                    if (isInternalCard()) {
                        cardArrayList.add(getInternalFile().getPath());
                        canUserPath = cardArrayList;
                    }
                }
            }
        }
        return canUserPath;
    }
}
