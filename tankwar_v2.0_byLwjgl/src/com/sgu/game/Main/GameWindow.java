package com.sgu.game.Main;

import com.sgu.game.Constant;
import com.sgu.game.Utils.CollisionUtils;
import com.sgu.game.Utils.SoundUtils;
import com.sgu.game.Utils.Window;
import com.sgu.game.entity.*;
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
    BossElement boss;


    @Override
    protected void onCreate() {
        try {
            SoundUtils.play("tankwar_v2.0_byLwjgl\\audio/start.wav");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userTank = new TankElement(0, 0);
        mapInit();
        boss = new BossElement(64*11, 64*11);
    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {
        switch (key) {
            case Keyboard.KEY_UP:
                userTank.move(Direction.UP);
                break;
            case Keyboard.KEY_DOWN:
                userTank.move(Direction.DOWN);
                break;
            case Keyboard.KEY_LEFT:
                userTank.move(Direction.LEFT);
                break;
            case Keyboard.KEY_RIGHT:
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
        for (int i = 0; i < Elements.size(); i++) {
            userTank.draw();
            Element element = Elements.get(i);
            element.draw();

            if((element instanceof BulletElement) && ((BulletElement) element).isOutSize())
                Elements.remove(element);

            if((element instanceof Collidable) && CollisionUtils.isCollisionWithElement(userTank, element)){
                userTank.unMove = userTank.getDirection();
//                switch (userTank.unMove = userTank.getDirection()){
//                    case UP :
//                        userTank.y = element.y + element.height;
//                        break;
//                    case DOWN:
//                        userTank.y = element.y - userTank.height;
//                        break;
//                    case LEFT:
//                        userTank.x = element.x + element.width;
//                        break;
//                    case RIGHT:
//                        userTank.x = element.x - userTank.width;
//                        break;
//                }
            }else{
                userTank.unMove = null;
            }
        }

    }

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

