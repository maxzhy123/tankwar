package com.sgu.game.Main;

import com.sgu.game.Constant;
import com.sgu.game.Utils.CollisionUtils;
import com.sgu.game.Utils.DrawUtils;
import com.sgu.game.Utils.SoundUtils;
import com.sgu.game.Utils.Window;
import com.sgu.game.entity.*;
import com.sgu.game.entity.able.Collidable;
import com.sgu.game.entity.able.Hitable;
import com.sgu.game.entity.able.Recyclable;
import com.sgu.game.entity.wall.*;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class GameWindow extends Window {
    public GameWindow(String title, int width, int height, int fps) {
        super(title, width, height, fps);
    }
    ArrayList<Element> Elements = new ArrayList();
    TankElement userTank;
    ArrayList<EnemyTankElement> enemyTanks = new ArrayList<>();
    BossElement boss;
    BuffElement buff;

    @Override
    protected void onCreate() {
        try {
            SoundUtils.play("tankwar_v2.0_byLwjgl\\audio\\start.wav");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Elements[0]是玩家坦克，Elements[1]是boss，Elements[2]——Elements[enemyTanks+2]是敌人坦克，
        userTank = new TankElement(64*2,  0);
        Elements.add(userTank);
        boss = new BossElement(64*11, 64*11);
        Elements.add(boss);
        enemyTanks.add(new EnemyTankElement(0, 64*8));
        enemyTanks.add(new EnemyTankElement(64*18, 0));
        enemyTanks.add(new EnemyTankElement(64*8, 64*11));
        Elements.addAll(enemyTanks);
        mapInit();
    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {
        switch (key) {
            case Keyboard.KEY_W:
                userTank.move(Direction.UP);
                break;
            case Keyboard.KEY_S:
                userTank.move(Direction.DOWN);
                break;
            case Keyboard.KEY_A:
                userTank.move(Direction.LEFT);
                break;
            case Keyboard.KEY_D:
                userTank.move(Direction.RIGHT);
                break;
            case Keyboard.KEY_SPACE:
                BulletElement bullet = userTank.fire();
                if(bullet != null)
                    Elements.add(bullet);
        }
    }

    @Override
    protected void onDisplayUpdate() {
        boolean userColFlag = true;
        boolean enemyColFlag = true;
        boolean isGameOver = false;
        boolean isBuff = false;
        if(userTank.HP <= 0 || boss.HP <= 0) isGameOver = true;

        if(isGameOver){
            try {
                DrawUtils.draw("tankwar_v2.0_byLwjgl\\img\\gameover.png", 64*6, 64*2);
                Elements.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < Elements.size(); i++) {

            Element element = Elements.get(i);
            element.draw();



            if(element instanceof Recyclable){
                if(!((Recyclable) element).isLife()) Elements.remove(element);
            }

            //判断子弹击中墙体或者坦克
            if(element instanceof BulletElement){
                //element对象为子弹
                BulletElement bullet = (BulletElement) element;
                for (int j = 0; j < Elements.size(); j++) {
                    //element2对象为可击打的对象，如部分墙体
                    Element element2 = Elements.get(j);
                    if((element2 instanceof Hitable) && bullet.isCollide(element2)){
                        Elements.remove(bullet);
                        Elements.add(((Hitable) element2).broken());
                        try {
                            SoundUtils.play("tankwar_v2.0_byLwjgl\\audio\\boom.wav");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            //判断主坦克是否碰撞墙体
            if(element instanceof Collidable && userColFlag){
                if(userTank.isCollide(element)) {
                    userTank.unMove = userTank.getDirection();
                    userColFlag = false;
                }
            }

            //判断敌人坦克是否碰撞墙体
            if(element instanceof Collidable && enemyColFlag){
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTankElement enemyTank = enemyTanks.get(j);
                    if(enemyTank.isCollide(element)) {
                        enemyTank.unMove = enemyTank.getDirection();
                        enemyColFlag = false;
                    }
                }
            }

            //敌方坦克发射子弹
            if(element instanceof EnemyTankElement){
                BulletElement bullet = ((EnemyTankElement) element).fire();
                if(bullet != null)
                    Elements.add(bullet);
            }

            //判断坦克吃到buff
            if(element instanceof BuffElement) {
                for (int j = 0; j < Elements.size(); j++) {
                    if(((BuffElement) element).isGetBuff(userTank)){
                        Elements.remove(element);
                        userTank.setSpeed(32);
                        userTank.setFireInterval(100);
                        userTank.getBuffTime = System.currentTimeMillis();
                        isBuff = true;
                    }
                }
            }

            //判断buff生效时间以及生成buff
            if(isBuff || buff == null){
                if(System.currentTimeMillis() - userTank.getBuffTime > 5000) {
                    userTank.setSpeed(16);
                    userTank.setFireInterval(300);
                    isBuff = false;
                    buff = buff.getBuff();
                    Elements.add(buff);
                }
            }
        }
    }

    //地图初始化
    private void mapInit(){
        //循环的次数 屏幕的宽度/64
        for (int i = 0; i < Constant.width / 64; i++) {
            if ((i >= 3 && i <= 5) || (i >= 12 && i <= 16)) {
                Elements.add(new GrassWallElement((i * 64), 64));
            } else {
                Elements.add(new BrickWallElement((i * 64), 64));
            }
        }

        for (int i = 0; i < Constant.width / 64 - 3; i++) {
            if (i >= 8 && i <= 13) {
                Elements.add(new GrassWallElement((i * 64) + 192, 192));
            } else {
                Elements.add(new SteelWallElement((i * 64) + 192, 192));
            }
        }

        for (int i = 0; i < Constant.width / 64 - 7; i++) {
            if (i % 2 == 0) {
                Elements.add(new BrickWallElement((i * 64) + 384, 320));
            } else {
                Elements.add(new SteelWallElement((i * 64) + 384, 320));
            }
        }

        for (int i = 0; i < Constant.width / 64 - 11; i++) {
            
            Elements.add(new BrickWallElement((i * 64) + 512, 448));
        }

        for (int i = 0; i < Constant.width / 64 - 13; i++) {
            
            Elements.add(new SteelWallElement((i * 64) + 512 + 192, 448 + 128));
        }

        for (int i = 0; i < Constant.width / 64 - 19; i++) {
            
            Elements.add(new BrickWallElement((i * 64) + 640, 640));
        }

        for (int i = 0; i < 3; i++) {
            if (i == 1) continue;
            
            Elements.add(new BrickWallElement((i * 64) + 640, 640 + 64));
        }

        for (int i = 0; i < Constant.height / 64 - 2; i++) {
            if (i == 8) continue;
            
            Elements.add(new WaterWallElement(64, (i * 64) + 128));
        }

        for (int i = 0; i < Constant.height / 64 - 5; i++) {
            if (i == 8) continue;
            
            Elements.add(new BrickWallElement(256, (i * 64) + 256));
        }

        for (int i = 0; i < Constant.height / 64 - 7; i++) {
            
            Elements.add(new WaterWallElement(448 - 64, (i * 64) + 448));
        }

        for (int i = 0; i < 2; i++) {
            
            Elements.add(new WaterWallElement(1024, (i * 64) + 640));
        }

        for (int i = 0; i < Constant.height / 64 - 6; i++) {
            if (i == Constant.height / 64 - 6 - 1) break;
            
            Elements.add(new BrickWallElement(1280, (i * 64) + 384));
        }
    }
}

