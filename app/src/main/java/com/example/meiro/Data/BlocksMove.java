package com.example.meiro.Data;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Constant.Constant.POSITION;
import com.example.meiro.Constant.Constant.WALL;
import com.example.meiro.Util.MeiroUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlocksMove extends Blocks {

    private List<String> mSearchKeys = new ArrayList<>();

    private BlocksMove(int maxX, int maxY) {
        super(maxX, maxY);
        List<String> keys = getKeys();
        for (String key : keys) {
            int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
            int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

            if (x == 0) {
                mSearchKeys.add(key);
            }
            else if (y == 0) {
                mSearchKeys.add(key);
            }
            else if (x == maxX - 1) {
                mSearchKeys.add(key);
            }
            else if (y == maxY - 1) {
                mSearchKeys.add(key);
            }
        }
    }

    public static BlocksMove of(int maxX, int maxY) {
        return new BlocksMove(maxX, maxY);
    }

    public void setWallData( Map<String, Block> datas) {

        List<String> keys = getKeys();
        for (String key : keys) {
            int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
            int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

            if (x == mMaxX - 1) {
                mDatas.put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (y == mMaxY - 1) {
                mDatas.put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (x == 0 && y == 0) {
                mDatas.put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (x == 1 && y == 0) {
                mDatas.put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (y == 0) {
                mDatas.put(key, Block.of(WALL.WALL_HORIZONTAL, 0));
            }
            else if (x == 0) {
                mDatas.put(key, Block.of(WALL.WALL_VERTICAL, 0));
            }
            else {
                String createKey = MeiroUtil.getKey((x - 1), (y - 1));
                mDatas.put(key, datas.get(createKey));
            }
        }
    }

    public boolean isEndMove(String key, POSITION position) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (x == mMaxX - 2 && y == mMaxY - 2 && position == POSITION.POSITION_UP) {
            return true;
        }

        return false;
    }

    public boolean isMoveUp(String key) {
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);
        if (mMaxY - 2 <= y) {
            return false;
        }

        WALL wall = getWall(key);
        switch(wall) {
            case WALL_ALL:
            case WALL_HORIZONTAL:
                return false;
            case WALL_VERTICAL:
            case WALL_NONE:
            default:
                    return true;
        }
    }

    public boolean isMoveDown(String key) {
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);
        if (y <= 1) {
            return false;
        }

        String downKey = MeiroUtil.getNextKey(key, POSITION.POSITION_DOWN);
        WALL wall = getWall(downKey);
        switch(wall) {
            case WALL_ALL:
            case WALL_HORIZONTAL:
                return false;
            case WALL_VERTICAL:
            case WALL_NONE:
            default:
                return true;
        }
    }

    public boolean isMoveRight(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        if (mMaxX - 2 <= x) {
            return false;
        }

        WALL wall = getWall(key);
        switch(wall) {
            case WALL_ALL:
            case WALL_VERTICAL:
                return false;
            case WALL_HORIZONTAL:
            case WALL_NONE:
            default:
                return true;
        }
    }

    public boolean isMoveLeft(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        if (x <= 1) {
            return false;
        }

        String leftKey = MeiroUtil.getNextKey(key, POSITION.POSITION_LEFT);
        WALL wall = getWall(leftKey);
        switch(wall) {
            case WALL_ALL:
            case WALL_VERTICAL:
                return false;
            case WALL_HORIZONTAL:
            case WALL_NONE:
            default:
                return true;
        }
    }

    public boolean isSearch(String key) {
        return mSearchKeys.contains(key);
    }

    public void addSearch(String key) {
        if (!isSearch(key)) {
            mSearchKeys.add(key);
        }
    }

    public void showAll() {
        mSearchKeys.clear();
        List<String> keys = getKeys();
        for (String key : keys) {
            mSearchKeys.add(key);
        }
    }

    public boolean showUpWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        if (y == mMaxY - 1) {
            return true;
        }

        if (isSearch(MeiroUtil.getKey(x, y + 1))) {
            return true;
        }

        return false;
    }

    public boolean showDownWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        if (y == 0) {
            return true;
        }

        if (isSearch(MeiroUtil.getKey(x, y - 1))) {
            return true;
        }

        return false;
    }

    public boolean showRightWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        if (x == mMaxX - 1) {
            return true;
        }

        if (isSearch(MeiroUtil.getKey(x + 1, y))) {
            return true;
        }

        return false;
    }

    public boolean showLeftWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        if (x == 0) {
            return true;
        }

        if (isSearch(MeiroUtil.getKey(x - 1, y))) {
            return true;
        }

        return false;
    }

    public boolean showBlackTopRightDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (x == mMaxX - 1 || y == mMaxY - 1) {
            return false;
        }

        if (isSearch(MeiroUtil.getKey(x + 1, y + 1))) {
            return true;
        }

        return false;
    }

    public boolean showBlackBotRightDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (x == mMaxX - 1 || y == 0) {
            return false;
        }

        if (isSearch(MeiroUtil.getKey(x + 1, y - 1))) {
            return true;
        }

        return false;
    }

    public boolean showBlackBotLeftDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (x == 0 || y == 0) {
            return false;
        }

        if (isSearch(MeiroUtil.getKey(x - 1, y - 1))) {
            return true;
        }

        return false;
    }

    public boolean showBlackTopLeftDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (x == 0 || y == mMaxY - 1) {
            return false;
        }

        if (isSearch(MeiroUtil.getKey(x - 1, y + 1))) {
            return true;
        }

        return false;
    }
}
