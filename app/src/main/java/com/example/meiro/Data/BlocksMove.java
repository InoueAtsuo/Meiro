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

            if (x == getMaxX() - 1) {
                getDatas().put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (y == getMaxY() - 1) {
                getDatas().put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (x == 0 && y == 0) {
                getDatas().put(key, Block.of(WALL.WALL_NONE, 0));
            }
            else if (y == 0) {
                getDatas().put(key, Block.of(WALL.WALL_HORIZONTAL, 0));
            }
            else if (x == 0) {
                getDatas().put(key, Block.of(WALL.WALL_VERTICAL, 0));
            }
            else {
                String createKey = MeiroUtil.getKey((x - 1), (y - 1));
                getDatas().put(key, datas.get(createKey));
            }
        }
    }

    public boolean isEndMove(String key, POSITION direction) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        return x == getMaxX() - 2 && y == getMaxY() - 2 && direction == POSITION.POSITION_UP;
    }

    public boolean isMove(String key, POSITION direction) {
        switch (direction) {
            case POSITION_UP:
                return isMoveUp(key);
            case POSITION_DOWN:
                return isMoveDown(key);
            case POSITION_RIGHT:
                return isMoveRight(key);
            case POSITION_LEFT:
                return isMoveLeft(key);
        }
        return false;
    }

    private boolean isMoveUp(String key) {
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);
        if (getMaxY() - 2 <= y) {
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

    private boolean isMoveDown(String key) {
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

    private boolean isMoveRight(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        if (getMaxX() - 2 <= x) {
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

    private boolean isMoveLeft(String key) {
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
        mSearchKeys.addAll(getKeys());
    }

    public boolean showUpWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        else if (y == getMaxY() - 1) {
            return true;
        }

        return isSearch(MeiroUtil.getKey(x, y + 1));
    }

    public boolean showDownWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        else if (y == 0) {
            return true;
        }

        return isSearch(MeiroUtil.getKey(x, y - 1));
    }

    public boolean showRightWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        else if (x == getMaxX() - 1) {
            return true;
        }

        return isSearch(MeiroUtil.getKey(x + 1, y));
    }

    public boolean showLeftWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        if (isSearch(key)) {
            return true;
        }
        else if (x == 0) {
            return true;
        }

        return isSearch(MeiroUtil.getKey(x - 1, y));
    }

    public boolean showBlackTopRightDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        return x != getMaxX() - 1 && y != getMaxY() - 1 &&
                isSearch(MeiroUtil.getKey(x + 1, y + 1));
    }

    public boolean showBlackBotRightDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        return x != getMaxX() - 1 && y != 0 &&
                isSearch(MeiroUtil.getKey(x + 1, y - 1));
    }

    public boolean showBlackBotLeftDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        return x != 0 && y != 0 &&
                isSearch(MeiroUtil.getKey(x - 1, y - 1));
    }

    public boolean showBlackTopLeftDot(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        return x != 0 && y != getMaxY() - 1 &&
                isSearch(MeiroUtil.getKey(x - 1, y + 1));
    }
}
