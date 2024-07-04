package com.sgu.game.entity;

import com.sgu.game.entity.able.Hitable;

public class BossElement extends Element implements Hitable {
    public BossElement() {
    }

    public BossElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\boss.png";
        super.getSize();
        super.HP = 1;
    }

    @Override
    public BombElement broken() {
        super.HP--;
        return new BombElement(this);
    }
}
