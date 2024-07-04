package com.sgu.game.entity;

import com.sgu.game.Constant;
import com.sgu.game.Main.GameWindow;
import com.sgu.game.Utils.CollisionUtils;
import com.sgu.game.Utils.SoundUtils;
import com.sgu.game.entity.able.Hitable;

import java.io.IOException;


public class TankElement extends Element implements Hitable {
    private int speed = 16; //移动速度
    private long fireInterval = 300;
    private long lastFireTime;

    public long getBuffTime;
    Direction d = Direction.UP;
    public Direction unMove = null;

    public TankElement() {
    }
    public TankElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\tank_u.gif";
        super.getSize();
        super.HP = 10;
    }

    //需要让move方法知道键盘按下的方向是哪里 ↓
    public void move(Direction d) {
        if(d.equals(unMove)) {
            return;
        }
        //如果传递进来的方向和当前坦克方向相同 纯粹的就是改变坐标
        if (d == this.d) {
            switch (d) {
                case UP:
                    this.y -= speed;
                    break;
                case DOWN:
                    this.y += speed;
                    break;
                case LEFT:
                    this.x -= speed;
                    break;
                case RIGHT:
                    this.x += speed;
                    break;
            }
        } else {
            //如果传递进来的方向和当前坦克方向不一致 那么需要先改变绘制的图片的方向 再修改坐标
            switch (d) {
                case UP:
                    this.path = "tankwar_v2.0_byLwjgl\\img\\tank_u.gif";
                    break;
                case DOWN:
                    this.path = "tankwar_v2.0_byLwjgl\\img\\tank_d.gif";
                    break;
                case LEFT:
                    this.path = "tankwar_v2.0_byLwjgl\\img\\tank_l.gif";
                    break;
                case RIGHT:
                    this.path = "tankwar_v2.0_byLwjgl\\img\\tank_r.gif";
                    break;
            }
            //记录当前坦克的真正指向
            this.d = d;
        }
        //如果向左移动x的坐标值变成负数说明越界 就把x的值修正为起点值0
        if (x < 0) {
            x = 0;
        }
        //如果向上移动y的坐标值变成负数说明越界 就把y的值修正为起点值0
        if (y < 0) {
            y = 0;
        }
        //如果向右移动x的坐标值大于屏幕宽度-坦克宽度 就把x的值修正为(屏幕宽度-坦克宽度)
        if (x > Constant.width - this.width) {
            x = Constant.width - this.width;
        }
        //如果向下移动y的坐标值大于屏幕高度-坦克高度 就把y的值修正为(屏幕高度-坦克高度)
        if (y > Constant.height - this.height) {
            y = Constant.height - this.height;
        }
    }

    /**
     * 02-坦克的开火方法 可以生成子弹
     * @return
     */
    public BulletElement fire() {
        BulletElement bullet = null;
        long currentFireTime = System.currentTimeMillis();
        if(currentFireTime - lastFireTime >= fireInterval){
            lastFireTime = currentFireTime;
            //x y 是坦克的坐标
            //w h 传入坦克的宽高
            //Direction d 传入坦克的方向
            bullet =  new BulletElement(x, y,width,height,d);
            try {
                SoundUtils.play("tankwar_v2.0_byLwjgl\\audio\\fire.wav");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bullet;
    }

    public boolean isCollide(Element colElement){
        int x = super.x;
        int y = super.y;
        boolean flag;
        switch (d){
            case UP :
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
        flag = CollisionUtils.isCollisionWithRect(x, y, super.width, super.height,
                colElement.x, colElement.y, colElement.width, colElement.height);

        if(!flag) unMove = null;
        return flag;
    }
    public Direction getDirection() {
        return d;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setFireInterval(long fireInterval) {
        this.fireInterval = fireInterval;
    }

    @Override
    public BombElement broken() {
        super.HP--;
        return new BombElement(this);
    }
}
