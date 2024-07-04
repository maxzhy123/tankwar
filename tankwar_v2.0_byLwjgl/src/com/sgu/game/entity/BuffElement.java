package com.sgu.game.entity;

import com.sgu.game.Constant;
import com.sgu.game.entity.able.Recyclable;


public class BuffElement extends Element {
    int loop = -1;
    public BuffElement() {
    }

    public BuffElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\buff.png";
        super.getSize();
    }

    public BuffElement getBuff(){
        BuffElement buff = null;
        switch (loop = (loop+1)%4){
            case 0:
                buff = new BuffElement(0,0);
                break;
            case 1:
                buff = new BuffElement(Constant.width - super.width,0);
                break;
            case 2:
                buff = new BuffElement(0,Constant.height - super.height);
                break;
            case 3:
                buff = new BuffElement(Constant.width - super.width,Constant.height - super.height);
                break;
        }
        return buff;
    }

    public boolean isGetBuff(Element e){
        return e.x == x && e.y == y;
    }
}
