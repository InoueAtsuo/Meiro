package com.example.meiro.Data;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Constant.Constant.POSITION;
import com.example.meiro.Constant.Constant.WALL;
import com.example.meiro.Util.MeiroUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlocksCreate extends Blocks {

    private List<String> mNoEndKeys = new ArrayList<>();

    private BlocksCreate(int maxX, int maxY) {
        super(maxX, maxY);
        List<String> keys = getKeys();
        for (String key : keys) {
            mDatas.put(key, Block.of(getFirstWall(key), getFirstConnect(key)));
            mNoEndKeys.add(key);
        }

        String removeKey = MeiroUtil.getKey(mMaxX - 1, mMaxY - 1);
        Iterator<String> i = mNoEndKeys.iterator();
        while(i.hasNext()) {
            String key = i.next();
            if (key.equals(removeKey)) {
                i.remove();
            }
        }
    }

    public static BlocksCreate of(int maxX, int maxY) {
        return new BlocksCreate(maxX, maxY);
    }

    public boolean isCreateEnd() {
        return mNoEndKeys.isEmpty();
    }

    public void createMeiro() {
        while(!isCreateEnd()) {
            String key = (String) MeiroUtil.getRandomValue(mNoEndKeys);
            List<POSITION> breakNexts = getNextPosition(key);
            if (breakNexts.isEmpty()){
                removeNoEndKey(key);
            }
            else {
                POSITION nextPosition = (POSITION) MeiroUtil.getRandomValue(breakNexts);
                breakWall(key, nextPosition);
            }
        }

        removeLastWall(MeiroUtil.getKey(mMaxX - 1, mMaxY - 1));
    }

    private WALL getFirstWall(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);
        if (x == mMaxX - 1 && y == mMaxY - 1) {
            return WALL.WALL_VERTICAL;
        }
        return WALL.WALL_ALL;
    }

    private int getFirstConnect(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);
        return x + y * mMaxX;
    }

    private List<POSITION> getNextPosition(String key) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        List<POSITION> breakNexts = new ArrayList<>();

        if (y < mMaxY - 1) {
            String upKey = MeiroUtil.getNextKey(key, POSITION.POSITION_UP);
            if (y != mMaxY - 2 || x != mMaxX - 1) {
                if (mDatas.get(key).getConnect() != mDatas.get(upKey).getConnect()) {
                    breakNexts.add(POSITION.POSITION_UP);
                }
            }
        }
        if (x < mMaxX - 1) {
            String rightKey = MeiroUtil.getNextKey(key, POSITION.POSITION_RIGHT);
            if (x != mMaxX - 2 || y != mMaxY - 1) {
                if (mDatas.get(key).getConnect() != mDatas.get(rightKey).getConnect()) {
                    breakNexts.add(POSITION.POSITION_RIGHT);
                }
            }
        }
        if (y > 0) {
            String downKey = MeiroUtil.getNextKey(key, POSITION.POSITION_DOWN);
            if (mDatas.get(key).getConnect() != mDatas.get(downKey).getConnect()) {
                breakNexts.add(POSITION.POSITION_DOWN);
            }
        }
        if (x > 0) {
            String leftKey = MeiroUtil.getNextKey(key, POSITION.POSITION_LEFT);
            if (mDatas.get(key).getConnect() != mDatas.get(leftKey).getConnect()) {
                breakNexts.add(POSITION.POSITION_LEFT);
            }
        }

        return breakNexts;
    }

    private void breakWall(String key, POSITION nextPosition) {
        String nextKey = MeiroUtil.getNextKey(key, nextPosition);
        Block block = mDatas.get(key);
        Block nextBlock = mDatas.get(nextKey);
        switch(nextPosition) {
            case POSITION_UP:
                if (block.getWall() == WALL.WALL_ALL){
                    block.setWall(WALL.WALL_VERTICAL);
                } else {
                    block.setWall(WALL.WALL_NONE);
                }
                mDatas.put(key, block);
                break;
            case POSITION_DOWN:
                if (nextBlock.getWall() == WALL.WALL_ALL){
                    nextBlock.setWall(WALL.WALL_VERTICAL);
                } else {
                    nextBlock.setWall(WALL.WALL_NONE);
                }
                mDatas.put(nextKey, nextBlock);
                break;
            case POSITION_RIGHT:
                if (block.getWall() == WALL.WALL_ALL){
                    block.setWall(WALL.WALL_HORIZONTAL);
                } else {
                    block.setWall(WALL.WALL_NONE);
                }
                mDatas.put(key, block);
                break;
            case POSITION_LEFT:
                if (nextBlock.getWall() == WALL.WALL_ALL){
                    nextBlock.setWall(WALL.WALL_HORIZONTAL);
                } else {
                    nextBlock.setWall(WALL.WALL_NONE);
                }
                mDatas.put(nextKey, nextBlock);
                break;
            default:
                break;
        }

        setConnect(key, nextKey);
        removeNoEndKey(key);
        removeNoEndKey(nextKey);
    }

    private void removeNoEndKey(String key) {
        List<POSITION> breakNexts = getNextPosition(key);
        if (breakNexts.isEmpty()) {
            for(int i = 0; i < mNoEndKeys.size(); i++) {
                if (key.equals(mNoEndKeys.get(i))) {
                    mNoEndKeys.remove(i);
                }
            }
        }
    }

    private void setConnect(String key, String nextKey){
        int keyConnect = mDatas.get(key).getConnect();
        int nextConnect = mDatas.get(nextKey).getConnect();

        if (keyConnect > nextConnect) {
            setConnectAll(nextConnect, keyConnect);
        }
        else {
            setConnectAll(keyConnect, nextConnect);
        }
    }

    private void setConnectAll(int beforeConnect, int afterConnect){
        List<String> keys = getKeys();
        for(String key : keys) {
            Block block = mDatas.get(key);
            if (block.getConnect() == beforeConnect) {
                block.setConnect(afterConnect);
                mDatas.put(key, block);
            }
        }
    }

    private void removeLastWall(String key) {
        List<POSITION> preGoalList = new ArrayList<>();
        preGoalList.add(POSITION.POSITION_DOWN);
        preGoalList.add(POSITION.POSITION_LEFT);
        POSITION lastPosition = (POSITION) MeiroUtil.getRandomValue(preGoalList);
        String lastKey = MeiroUtil.getNextKey(key, lastPosition);
        Block lastBlock = mDatas.get(lastKey);
        if (lastPosition == POSITION.POSITION_DOWN) {
            lastBlock.setWall(WALL.WALL_VERTICAL);
        }
        else {
            lastBlock.setWall(WALL.WALL_HORIZONTAL);
        }
        mDatas.put(lastKey, lastBlock);
    }
}
