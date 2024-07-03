import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable {
    static Vector<EnemyTank> enemyTanks = null; //敌人坦克集合
    UserTank userTank = null;                   //玩家坦克
    static Vector<Bomb> bombs = null;           //爆炸位置集合
    Image image1 = null, image2 = null, image3 = null; //爆炸图片
    public MyPanel(int key) {
        //根据传入key值判断是否读档：1：新游戏	2：继续游戏
        switch (key){
            case 1 :
                enemyTanks = new Vector<>();            //初始化敌人
                while (enemyTanks.size() <= 6) {
                   enemyTanks.add(new EnemyTank((int)(Math.random()*800 + 100), (int)(Math.random()*600 + 100)));//随机敌人位置
                }
                for (EnemyTank enemyTank : enemyTanks) {    //启动敌人线程
                    new Thread(enemyTank).start();
                }
                userTank = new UserTank(500,700); //玩家坦克初始位置
                break;
            case 2:
                if(!new File("resources\\myRecord.txt").exists()) {
                    System.out.println("存档不存在，请开始新的游戏");
                    System.exit(1);
                }else {
                    Recorder.readRecord(this);          //读档方法
                    for (EnemyTank enemyTank : enemyTanks) {    //启动敌人线程
                        new Thread(enemyTank).start();
                        for (Bullet bullet : enemyTank.getBullets()) {
                            new Thread(bullet).start();         //启动敌人的子弹线程
                        }
                    }
                    for (Bullet bullet : userTank.getBullets()) {
                        new Thread(bullet).start();             //启动玩家的子弹线程
                    }
                }
                break;
            default:
                System.out.println("您的输入有误，请重新输入：1：新游戏\t2：继续游戏");
        }
        image1 = Toolkit.getDefaultToolkit().getImage("tankwar_v1.0_bySwing\\resources\\bomb\\bomb_1.gif");
        image2 = Toolkit.getDefaultToolkit().getImage("tankwar_v1.0_bySwing\\resources\\bomb\\bomb_2.gif");
        image3 = Toolkit.getDefaultToolkit().getImage("tankwar_v1.0_bySwing\\resources\\bomb\\bomb_3.gif");
        bombs = new Vector<>();
        bombs.add(new Bomb(1,1));                           //初始化图片
        new AePlayWave("tankwar_v1.0_bySwing\\resources\\bgm.wav").start();     //启动背景音乐
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,800);   //绘制背景，大小为1000px * 800px
        for (int i = 0; i < bombs.size(); i++) {        //根据生命周期绘制爆炸效果
            Bomb bomb = bombs.get(i);
            drawBomb(bomb, g);
            drawBomb(bomb, g);
        }
        for (int i = 0; i < enemyTanks.size(); i++) {             //绘制敌人
            Tank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank, g);
        }
        drawTank(userTank, g);    //绘制玩家坦克
        showInfo(g);                //显示信息栏
    }

    //绘制爆炸方法
    private void drawBomb(Bomb bomb, Graphics g) { //绘制爆炸
        while (bomb.getLife() > 0) {
            if (bomb.getLife() > 6) {
                g.drawImage(image1, bomb.getX()-30, bomb.getY()-30, 60, 60, this);
            } else if (bomb.getLife() > 3) {
                g.drawImage(image2, bomb.getX()-30, bomb.getY()-30, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.getX()-30, bomb.getY()-30, 60, 60, this);
            }
            bomb.next();
            if (!bomb.isLife) {
                bombs.remove(bomb);
            }
        }
    }

    //绘制坦克和其子弹方法
    public void drawTank(Tank tank, Graphics g) {
        if(!tank.isLife()) return;      //不绘制死亡坦克
        int tankX = tank.getX();
        int tankY = tank.getY();
        Vector<Bullet> tankBullets = tank.getBullets();
        switch (tank.getType()){                //判断坦克类型
            case 0 :
                g.setColor(Color.yellow);       //玩家为黄色
                break;
            case 1:
                g.setColor(Color.red);          //敌人为红色
                break;
        }
        switch (tank.getDirect()){ //判断坦克方向
            case 1 :
                g.fill3DRect(tankX-20, tankY-30, 10, 60, false);    //左履带
                g.fill3DRect(tankX+10, tankY-30, 10, 60, false);    //右履带
                g.fill3DRect(tankX-10, tankY-20, 20, 40, false);    //主体
                g.fillOval (tankX-10, tankY-10, 20, 20);                   //炮塔
                g.drawLine(tankX,tankY,tankX, tankY-35);                                   //炮管
                break;
            case -1 :
                g.fill3DRect(tankX-20, tankY-30, 10, 60, false);
                g.fill3DRect(tankX+10, tankY-30, 10, 60, false);
                g.fill3DRect(tankX-10, tankY-20, 20, 40, false);
                g.fillOval (tankX-10, tankY-10, 20, 20);
                g.drawLine(tankX,tankY,tankX, tankY+35);
                break;
            case 2 :
                g.fill3DRect(tankX-30, tankY-20, 60, 10, false);
                g.fill3DRect(tankX-30, tankY+10, 60, 10, false);
                g.fill3DRect(tankX-20, tankY-10, 40, 20, false);
                g.fillOval (tankX-10, tankY-10, 20, 20);
                g.drawLine(tankX,tankY,tankX-35, tankY);
                break;
            case -2 :
                g.fill3DRect(tankX-30, tankY-20, 60, 10, false);
                g.fill3DRect(tankX-30, tankY+10, 60, 10, false);
                g.fill3DRect(tankX-20, tankY-10, 40, 20, false);
                g.fillOval (tankX-10, tankY-10, 20, 20);
                g.drawLine(tankX,tankY,tankX+35, tankY);
                break;
        }
        //绘制子弹
        if(!tankBullets.isEmpty()){
            for (int i = 0; i < tankBullets.size(); i++) {
                if(tankBullets.elementAt(i).isLife()) {
                    g.fillOval(tankBullets.elementAt(i).getX()-2, tankBullets.elementAt(i).getY()-2,4,4); //绘制子弹
                }else{
                    tankBullets.remove(i);  //不存活则删除该子弹
                }
            }
        }
    }

    //绘制信息栏方法
    public void showInfo(Graphics g){
        drawTank(new EnemyTank(1050, 80), g);
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 25));   //文字格式
        g.drawString("已击毁坦克",1030,30);
        g.drawString(":\t"+Recorder.destroyedNum, 1080 ,90);
    }

    //判断坦克是否被击中方法
    public boolean HitTank(Bullet bullet, Tank tank){
        switch (tank.getDirect()){
            case 1:
            case -1:
                if(bullet.getX() > tank.getX()-25 && bullet.getX() < tank.getX()+25 && bullet.getY() > tank.getY()-35 && bullet.getY() < tank.getY()+35){
                    bombs.add(new Bomb(tank.getX(), tank.getY()));//添加爆炸
                    bullet.setLife(false);  //杀死子弹
                    tank.setLife(false);    //杀死坦克
                    return true;
                }
            case 2:
            case -2:
                if(bullet.getX() > tank.getX()-35 && bullet.getX() < tank.getX()+35 && bullet.getY() > tank.getY()-25 && bullet.getY() < tank.getY()+25){
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                    bullet.setLife(false);
                    tank.setLife(false);
                    return true;
                }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W : // W键向上移动
                if(userTank.getY() > 50) userTank.moveUp();
                userTank.setDirect(1);
                break;
            case KeyEvent.VK_A : // A键向左移动
                if(userTank.getX() > 50) userTank.moveLeft();
                userTank.setDirect(2);
                break;
            case KeyEvent.VK_S : // S键向下移动
                if(userTank.getY() < 750) userTank.moveDown();
                userTank.setDirect(-1);
                break;
            case KeyEvent.VK_D : // D键向右移动
                if(userTank.getX() < 950) userTank.moveRight();
                userTank.setDirect(-2);
                break;
            case KeyEvent.VK_J : //发射子弹
                userTank.Shot();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            for (Bullet bullet : userTank.getBullets()) { //判断玩家子弹是否命中敌人
                for (Tank enemyTank : enemyTanks) {
                    if (enemyTank.isLife()) {
                        if(HitTank(bullet, enemyTank)) Recorder.destroyedNum++; //击中则摧毁敌人数量加一
                    }
                }
            }

            for (Tank enemyTank : enemyTanks) {         //判断玩家是否被子弹击中
                for (Bullet bullet : enemyTank.getBullets()) {
                    if(bullet.isLife()) {
                        HitTank(bullet, userTank);
                    }
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
        }
    }
}
