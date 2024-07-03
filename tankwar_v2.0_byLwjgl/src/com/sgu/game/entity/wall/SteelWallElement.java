package com.sgu.game.entity.wall;

import com.sgu.game.entity.BombElement;
import com.sgu.game.entity.Element;
import com.sgu.game.entity.able.Recyclable;
import com.sgu.game.entity.able.Collidable;
import com.sgu.game.entity.able.Hitable;

public class SteelWallElement extends Element implements Collidable, Hitable, Recyclable {

    public SteelWallElement() {
    }

    public SteelWallElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\steel.gif";
        super.getSize();
        super.HP = 6;
    }

    @Override
    public boolean isLife() {
        return super.HP > 0;
    }

    @Override
    public BombElement broken() {
        super.HP--;
        return new BombElement(this);
    }
}
