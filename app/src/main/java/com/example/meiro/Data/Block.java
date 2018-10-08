package com.example.meiro.Data;

import com.example.meiro.Constant.Constant.WALL;

public class Block {

    private WALL wall;

    private int connect;

    public WALL getWall() {
        return wall;
    }

    public void setWall(WALL wall) {
        this.wall = wall;
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }

    private Block(WALL wall, int connect){
        this.wall = wall;
        this.connect = connect;
    }

    public static Block of(WALL wall, int connect) {
        return new Block(wall, connect);
    }
}
