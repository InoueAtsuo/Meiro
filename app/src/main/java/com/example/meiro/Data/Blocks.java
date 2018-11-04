package com.example.meiro.Data;

import com.example.meiro.Constant.Constant.WALL;
import com.example.meiro.Util.MeiroUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blocks {

    private Map<String, Block> mDatas = new HashMap<>();

    private int mMaxX;
    private int mMaxY;

    public Blocks(int maxX, int maxY) {
        mMaxX = maxX;
        mMaxY = maxY;
    }

    public int getMaxX() {
        return mMaxX;
    }

    public int getMaxY() {
        return mMaxY;
    }

    public Map<String, Block> getDatas() {
        return mDatas;
    }

    public static Blocks of(int x, int y) {
        return new Blocks(x, y);
    }

    public WALL getWall(String key) {
        return mDatas.get(key).getWall();
    }

    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        for (int j = mMaxY - 1; 0 <= j; j--) {
            for (int i = 0; i < mMaxX; i++) {
                String key = MeiroUtil.getKey(i, j);
                keys.add(key);
            }
        }

        return keys;
    }
}
