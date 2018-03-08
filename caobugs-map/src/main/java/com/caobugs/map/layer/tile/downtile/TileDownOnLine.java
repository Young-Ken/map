/*
 * Copyright (C) 2018 FarmFriend Co., Ltd. All rights reserved.
 */
package com.caobugs.map.layer.tile.downtile;

import com.caobugs.map.model.api.ILatLongBox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Young Ken
 * @since 2018-02
 */
public class TileDownOnLine {

    private int mLevel;
    private ILatLongBox mBox;
    private ExecutorService mThreadPool = null;

    public TileDownOnLine() {

        mThreadPool = Executors.newScheduledThreadPool(10);

    }

    public void downTile(ILatLongBox box, int level) {

        mBox = box;
        mLevel = level;
    }


}

