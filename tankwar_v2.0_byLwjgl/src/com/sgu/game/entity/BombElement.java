package com.sgu.game.entity;

import com.sgu.game.entity.able.Recyclable;

public class BombElement extends Element implements Recyclable {
    int life = 0;
    String[] paths = {
            "tankwar_v2.0_byLwjgl\\img\\blast_1.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_2.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_3.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_4.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_5.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_6.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_7.gif",
            "tankwar_v2.0_byLwjgl\\img\\blast_8.gif"
    };
    public BombElement() {
    }

    public BombElement(Element element) {
        super(element.x, element.y);
        super.path = paths[life++];
        super.getSize();
        super.x = element.x + element.width/2 -super.width/2;
        super.y = element.y + element.height/2 -super.height/2;
    }

    @Override
    public void draw() {
        super.draw();
        if(life < 36)
            super.path = paths[(life++/5)];
    }

    public boolean isLife(){
        return life < 8;
    }
}
