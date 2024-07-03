import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

public abstract class Tank implements Serializable {
    private int x; //中心横坐标
    private int y;//中心纵坐标
    private int type;//坦克类型：0为user；1为enemy
    private int speed = 10;
    private int direct;//坦克方向：1为上；-1为下；2为左；-2为右。
    private boolean isLife = true;
    private Vector<Bullet> bullets = new Vector<>(); //本坦克射出子弹的集合

    //检查坦克是否发生碰撞
    public boolean isCrash(){
        for (EnemyTank enemyTank : MyPanel.enemyTanks) {
            if(enemyTank != this){
                switch (direct) {
                    //检查在上方是否有坦克
                    // （y - enemyTank.getY() > 0 && y - enemyTank.getY() < 60） ： 前方距离小于0的范围内
                    // （Math.abs(x-enemyTank.getX()) < 40）：坦克左右各20的范围内
                    case 1 :
                        if(y - enemyTank.getY() > 0 && y - enemyTank.getY() < 60 && Math.abs(x-enemyTank.getX()) < 40){
                            return true;
                        }break;
                    case -1:
                        if(enemyTank.getY() - y > 0 && enemyTank.getY() - y < 60 && Math.abs(x-enemyTank.getX()) < 50){
                            return true;
                        }break;
                    case 2 :
                        if(Math.abs(y-enemyTank.getY()) < 70 && x - enemyTank.getX() > 0 && x - enemyTank.getX() < 60){
                            return true;
                        }break;
                    case -2:
                        if(Math.abs(y-enemyTank.getY()) < 70 && enemyTank.getX() - x > 0 && enemyTank.getX() - x < 60){
                            return true;
                        }break;
                }
            }
        }
        return false;
    }

    public void moveUp() {
        if(isCrash()) return;   //碰撞之后无法移动
        y -= speed;

    }

    public void moveDown() {
        if(isCrash()) return;
        y += speed;

    }

    public void moveLeft() {
        if(isCrash()) return;
        x -= speed;
    }

    public void moveRight() {
        if(isCrash()) return;
        x += speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //设计，发射子弹，启动子弹线程
    public void Shot(){
        int direct = getDirect(); //使用本坦克方向初始化子弹方向
        switch (direct){
            case 1 :
                bullets.add(new Bullet(getX() ,getY()-35,direct)); //在子弹集合中添加子弹
                break;
            case -1 :
                bullets.add(new Bullet(getX() ,getY()+35,direct));
                break;
            case 2 :
                bullets.add(new Bullet(getX()-35 ,getY(),direct));
                break;
            case -2 :
                bullets.add(new Bullet(getX()+35 ,getY(),direct));
                break;
        }
        new Thread(bullets.lastElement()).start();          //启动子弹线程
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public boolean isLife() {
        return isLife;
    }

    public void setLife(boolean life) {
        isLife = life;
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }
}
