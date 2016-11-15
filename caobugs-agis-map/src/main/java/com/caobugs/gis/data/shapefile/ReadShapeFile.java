package com.caobugs.gis.data.shapefile;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.caobugs.gis.util.constants.ConstantFile;
import com.caobugs.gis.view.layer.ShapeLayer;
import com.caobugs.gis.view.map.BaseMap;
import com.caobugs.gis.view.map.MapManger;
import com.caobugs.gis.data.shapefile.event.OnShapeStatusListener;
import com.caobugs.gis.data.shapefile.shp.Analytical;
import com.caobugs.gis.data.shapefile.shp.ShapeFile;
import com.caobugs.gis.data.shapefile.shp.ShapeFileManger;
import com.caobugs.gis.util.file.ToolFile;
import com.caobugs.gis.util.file.ToolStorage;
import com.caobugs.gis.util.file.readfilenames.IReadFilesNames;
import com.caobugs.gis.util.file.readfilenames.ReadFilesNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/10/29
 */
public class ReadShapeFile implements IReadFilesNames,OnShapeStatusListener
{
    public ReadShapeFile()
    {
        //File rootShape = ToolStorage.getWorkSpaceFile();
        //rootShape = ToolFile.appendFile(rootShape, ConstantFile.ROOT, ConstantFile.SHAPE_ROOT, ConstantFile.SHAPE_FILE);

        //rootShape = ToolFile.appendFile(rootShape, ConstantFile.ROOT, ConstantFile.SHAPE_ROOT,"text");

//        String[] strings = {"shp"};
//        String[] fileName = {"Point","gaoxin"};
//        new ReadFilesNames(this, fileName, strings, true).execute(rootShape);
    }

    @Override
    public void allFileName(List<String[]> list)
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        for(String[] strings : list)
        {
            ShapeThread thread = new ShapeThread(strings, this);
            threadPool.execute(thread);
        }
     }

    @Override
    public void  onShapeStatusChanged(String shapeName)
    {
        synchronized (ReadShapeFile.class)
        {
            BaseMap map = MapManger.getInstance().getMap();
            ShapeFileManger manger = ShapeFileManger.getInstance();
            List<ShapeFile> list = manger.getList();

            for (ShapeFile shapeFile : list)
            {
                map.addLayer(new ShapeLayer(shapeFile));
            }

            Message message = new Message();
            message.what = 0;
            new ShapeHandler(Looper.getMainLooper()).sendMessage(message);

        }
    }

    class ShapeHandler extends Handler
    {
        ShapeHandler(Looper looper)
        {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what)
            {
                case 0:
                MapManger.getInstance().getMap().refresh();

            }
            super.handleMessage(msg);
        }
    }

    class ShapeThread extends Thread
    {
        private String[] strings = null;
        private OnShapeStatusListener listener = null;
        public ShapeThread(String[] strings, OnShapeStatusListener listener)
        {
            this.strings = strings;
            this.listener = listener;
        }
        @Override
        public void run()
        {
            try
            {
                RandomAccessFile accessFile = new RandomAccessFile(new File(strings[1]), "rw");
                Analytical analytical = Analytical.getInstance(listener);
                analytical.analyticalShape(accessFile, strings[0]);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
