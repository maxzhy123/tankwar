package com.sgu.game.entity;

import com.sgu.game.Utils.DrawUtils;

import java.io.IOException;

public abstract class Element {
    //墙体的坐标
    public int x;
    public int y;
    public int HP;

    //墙体的宽度和高度
    public int width;
    public int height;

    public String path;

    //绘制墙体的方法
    public void draw() {
        try {
            DrawUtils.draw(path, x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Element() {
    }

    public Element(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void getSize() {
        try {
            int[] size = DrawUtils.getSize(path);
            this.width = size[0];
            this.height = size[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
