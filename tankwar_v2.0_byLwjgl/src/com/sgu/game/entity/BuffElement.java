package com.sgu.game.entity;

import java.util.Random;

public class BuffElement extends Element{
    public BuffElement() {
    }

    public BuffElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\buff_4040.png";
        super.getSize();
    }
}
