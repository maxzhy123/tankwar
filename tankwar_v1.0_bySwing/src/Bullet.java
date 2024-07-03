import java.io.Serializable;

public class Bullet implements Runnable, Serializable{
    private int x;              //子弹坐标
    private int y;
    private int direct;         //子弹方向
    private int speed = 10;     //子弹速度
    private boolean isLife = true;

    @Override
    public void run() {
        while (isLife) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //子弹按方向移动
            switch (direct){
                case 1:
                    y -= speed;
                    break;
                case -1:
                    y += speed;
                    break;
                case 2:
                    x -= speed;
                    break;
                case -2:
                    x += speed;
                    break;
            }
            //边界检测
            if(x<0||x>1000||y<0||y>800){
                isLife = false;
                break;
            }
        }
    }

    public Bullet(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLife() {
        return isLife;
    }

    public void setLife(boolean life) {
        isLife = life;
    }
}
