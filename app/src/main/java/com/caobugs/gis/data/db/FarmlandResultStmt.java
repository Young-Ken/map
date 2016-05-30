package com.caobugs.gis.data.db;

import com.caobugs.gis.geometry.LinearRing;
import com.caobugs.gis.io.WKBReader;
import com.caobugs.gis.vo.Farmland;

import java.util.ArrayList;

import jsqlite.*;
import jsqlite.Exception;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/5/10
 */
public class FarmlandResultStmt extends ResultStmt
{
    private ArrayList<Farmland> farmlands = null;
    private ArrayList<Farmland> originalFarmlands = null;
    public FarmlandResultStmt(Stmt stmt, ArrayList<Farmland> originalFarmlands) throws jsqlite.Exception
    {
        super(stmt);
        if (stmt == null)
        {
            throw new Exception("ResultStmt() stmt 为空");
        }
        this.originalFarmlands = originalFarmlands;
        addFarmland();

    }

    public FarmlandResultStmt(Stmt stmt) throws jsqlite.Exception
    {
        super(stmt);
        if (stmt == null)
        {
            throw new Exception("ResultStmt() stmt 为空");
        }
        addFarmland();
    }

    private void addFarmland() throws jsqlite.Exception
    {
        try
        {
            farmlands = new ArrayList<>();
            Farmland farmland = null;
            while (stmt.step())
            {
                int columnCount = stmt.column_count();
                farmland = new Farmland();

                boolean isRepeat = false;

                if(originalFarmlands != null)
                {
                    for(Farmland temp : originalFarmlands)
                    {
                        if(getInt(0) == temp.getId())
                        {
                            isRepeat = true;
                            break;
                        }
                    }
                }

                if(isRepeat)
                {
                    continue;
                }

                for (int i = 0; i < columnCount; i++)
                {
                    int columnType = stmt.column_type(i);
                    String name = stmt.column_name(i).toLowerCase();
                    if ("id".equals(name))
                    {
                        farmland.setId(getInt(0));
                    }else if("tel".equals(name))
                    {
                        farmland.setTel(getString(1));
                    } else if ("farmname".equals(name))
                    {
                        farmland.setFarmName(getString(2));
                    } else if ("address".equals(name))
                    {
                        farmland.setAddress(getString(3));
                    } else if ("area".equals(name))
                    {
                        farmland.setArea(getDouble(4));
                    } else if ("geom".equals(name))
                    {
                        try
                        {
                            farmland.setFarmGeom((LinearRing) new WKBReader().read(getBytes(5)));
                        } catch (java.lang.Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                farmlands.add(farmland);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Farmland> getFarmlands()
    {
        return farmlands;
    }
}
