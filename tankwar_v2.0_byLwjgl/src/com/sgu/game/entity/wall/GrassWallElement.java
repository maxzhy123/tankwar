package com.sgu.game.entity.wall;

import com.sgu.game.entity.Element;

public class GrassWallElement extends Element {
    public GrassWallElement() {
    }

    public GrassWallElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\grass.gif";
        super.getSize();
    }

}
