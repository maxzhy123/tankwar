package com.sgu.game.entity.wall;


import com.sgu.game.entity.Element;

public class BrickWallElement extends Element implements Collidable{
    public BrickWallElement() {
    }

    public BrickWallElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\wall.gif";
        super.getSize();
    }
}

