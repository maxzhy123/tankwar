package com.sgu.game.entity;

public class BossElement extends Element{
    public BossElement() {
    }

    public BossElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\boss.png";
        super.getSize();
    }
}
