package com.snail.gis.tool.file.readfilenames;

import com.snail.gis.tool.file.ToolFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/10/13
 */
public class ReadFilesNames extends ReadFileAsyncTask<File, Void, List<String[]>>
{
    /**
     * 包含扩展名的数组
     */
    private String[] extensionName = null;

    /**
     * 回调接口
     */
    private IReadFilesNames iReadFilesNames = null;

    /**
     * 包含扩展名
     */
    private boolean isContain = true;

    /**
     * 读文件目录小的所用文件
     * @param iReadFilesNames 回调接口
     */
    public ReadFilesNames(IReadFilesNames iReadFilesNames)
    {
        this.iReadFilesNames = iReadFilesNames;
    }

    /**
     * 读目录下extensionName数组包含的扩展名的文件
     * @param iReadFilesNames 回调接口
     * @param extensionName 包含扩展名
     * @param isContain 包含扩展名,默认是true
     */
    public ReadFilesNames(IReadFilesNames iReadFilesNames, String[] extensionName, boolean isContain)
    {
        this(iReadFilesNames);
        this.extensionName = extensionName;
        this.isContain = isContain;
    }

    /**
     * 从写方法
     * @param files 文件名
     */
    @Override
    protected void onPostExecute(List<String[]> files)
    {
        super.onPostExecute(files);
        iReadFilesNames.allFileName(files);
    }

    /**
     * 从写方法
     * @param files 文件名
     * @return 文件集合
     */
    @Override
    protected List<String[]> doInBackground(File... files)
    {
        List<String[]> restult = new ArrayList<>();
        if (files.length == 0)
        {
            return restult;
        }

        File file = files[0];
        if (file.isDirectory())
        {
            File[] tempList = file.listFiles();

            //这样写是为了效率，代码有些重复
            if (extensionName == null)
            {
                for (File f : tempList)
                {
                    if (f.isFile())
                    {
                        String[] arrayFileName = ToolFile.getFileNameExtensName(f.getName());
                        if (arrayFileName != null && arrayFileName.length > 0)
                        {
                            String shapeFileName = file.getPath() + File.separator + f.getName();
                            String[] strings = new String[2];
                            strings[0] = arrayFileName[0];
                            strings[1] = shapeFileName;
                            restult.add(strings);
                        }
                    }
                }
            } else
            {
                for (File f : tempList)
                {
                    if (f.isFile())
                    {
                        String[] arrayFileName = ToolFile.getFileNameExtensName(f.getName());
                        if (arrayFileName != null && arrayFileName.length > 0)
                        {
                            for (String eName : extensionName)
                            {
                                if (eName.equals(arrayFileName[1]) == isContain)
                                {
                                    String shapeFileName = file.getPath() + File.separator + f.getName();
                                    String[] strings = new String[2];
                                    strings[0] = arrayFileName[0];
                                    strings[1] = shapeFileName;
                                    restult.add(strings);
                                }
                            }
                        }
                    }
                }
            }
        }
        return restult;
    }
}
