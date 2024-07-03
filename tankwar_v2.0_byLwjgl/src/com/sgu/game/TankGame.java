package com.sgu.game;

import com.sgu.game.Main.GameWindow;

public class TankGame {
    public static void main(String[] args) {
        GameWindow window = new GameWindow(Constant.title, Constant.width, Constant.height, Constant.fps);
        window.start();
    }
}
