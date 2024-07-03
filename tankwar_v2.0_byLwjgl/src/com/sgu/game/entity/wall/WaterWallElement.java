package com.sgu.game.entity.wall;

import com.sgu.game.entity.Element;
import com.sgu.game.entity.able.Collidable;

public class WaterWallElement extends Element implements Collidable {
    public WaterWallElement() {
    }

    public WaterWallElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\water.gif";
        super.getSize();
    }

}
