package com.sgu.game.entity;

import com.sgu.game.Constant;
import com.sgu.game.Utils.CollisionUtils;
import com.sgu.game.Utils.SoundUtils;
import com.sgu.game.entity.able.Hitable;
import com.sgu.game.entity.able.Recyclable;

import java.io.IOException;
import java.util.Random;


public class EnemyTankElement extends Element implements Hitable, Recyclable {
    private int speed = 1; //移动速度
    long fireInterval = 2000;
    long moveInterval = 3000;
    long lastFireTime;
    long lastMoveTime;
    Direction d = Direction.RIGHT;
    public Direction unMove = null;

    public EnemyTankElement() {
    }
    public EnemyTankElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\enemy_1_u.gif";
        super.getSize();
        super.HP = 2;
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
                    unMove = null;
                    this.path = "tankwar_v2.0_byLwjgl\\img\\enemy_1_u.gif";
                    break;
                case DOWN:
                    unMove = null;
                    this.path = "tankwar_v2.0_byLwjgl\\img\\enemy_1_d.gif";
                    break;
                case LEFT:
                    unMove = null;
                    this.path = "tankwar_v2.0_byLwjgl\\img\\enemy_1_l.gif";
                    break;
                case RIGHT:
                    unMove = null;
                    this.path = "tankwar_v2.0_byLwjgl\\img\\enemy_1_r.gif";
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
        return CollisionUtils.isCollisionWithRect(x, y, super.width, super.height,
                colElement.x, colElement.y, colElement.width, colElement.height);
    }

    @Override
    public void draw() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastMoveTime >= moveInterval){
            lastMoveTime = currentTime;
            this.move(RandomDirect());
        }else if(d == unMove || isEdge()){
            this.move(RandomDirect());
        }else{
            this.move(d);
        }
        super.draw();
    }

    public Direction RandomDirect(){
        Random r = new Random();
        int n = r.nextInt(4);
        switch (n){
            case 0 :
                return Direction.UP;
            case 1 :
                return Direction.LEFT;
            case 2 :
                return Direction.DOWN;
            case 3 :
                return Direction.RIGHT;
        }
        return d;
    }
    public Direction getDirection() {
        return d;
    }

    @Override
    public BombElement broken() {
        super.HP--;
        return new BombElement(this);
    }

    public boolean isLife(){
        return HP > 0;
    }

    public boolean isEdge(){
        boolean flag = false;
        switch (d) {
            case UP:
                if(super.y <= 0) flag = true;
                break;
            case DOWN:
                if(super.y + super.height >= Constant.height) flag = true;
                break;
            case LEFT:
                if(super.x <= 0) flag = true;
                break;
            case RIGHT:
                if(super.x + super.width >= 0) flag = true;
                break;
        }
        return flag;
    }
}
