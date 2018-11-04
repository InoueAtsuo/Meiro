package com.example.meiro.Constant;

public class Constant {
    public static String INTENT_KEY_X = "INTENT_KEY_X";
    public static String INTENT_KEY_Y = "INTENT_KEY_Y";

    public static String REGEX = "_";

    public enum WALL {
        // 右、上ともに壁なし
        WALL_NONE,
        // 右に壁あり、上に壁なし
        WALL_VERTICAL,
        // 上に壁あり、右に壁なし
        WALL_HORIZONTAL,
        // 右、上ともに壁あり
        WALL_ALL
    }

    public enum POSITION {
        POSITION_UP,
        POSITION_DOWN,
        POSITION_RIGHT,
        POSITION_LEFT
    }
}
