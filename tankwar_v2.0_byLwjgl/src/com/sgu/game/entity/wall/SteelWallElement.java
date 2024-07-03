package com.sgu.game.entity.wall;

import com.sgu.game.entity.Element;

public class SteelWallElement extends Element implements Collidable{

    public SteelWallElement() {
    }

    public SteelWallElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\steel.gif";
        super.getSize();
    }

}
