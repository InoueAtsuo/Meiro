package com.example.meiro.Util;

import com.example.meiro.Constant.Constant;
import com.example.meiro.Constant.Constant.POSITION;

import java.util.List;
import java.util.Random;

/**
 * 共通クラス
 */
public class MeiroUtil {

    public static Object getRandomValue(List dataList) {
        int val = getRandom(dataList.size());
        return dataList.get(val);
    }

    private static int getRandom(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public static String getKey(int x, int y) {
        return String.valueOf(x) + Constant.REGEX + String.valueOf(y);
    }

    public static String getNextKey(String key, POSITION nextPosition) {
        int x = Integer.valueOf(key.split(Constant.REGEX)[0]);
        int y = Integer.valueOf(key.split(Constant.REGEX)[1]);

        String nextKey = null;
        switch(nextPosition) {
            case POSITION_UP:
                nextKey = MeiroUtil.getKey(x, y + 1);
                break;
            case POSITION_DOWN:
                nextKey = MeiroUtil.getKey(x, y - 1);
                break;
            case POSITION_RIGHT:
                nextKey = MeiroUtil.getKey(x + 1, y);
                break;
            case POSITION_LEFT:
                nextKey = MeiroUtil.getKey(x - 1, y);
                break;
            default:
                break;
        }
        return nextKey;
    }
}
