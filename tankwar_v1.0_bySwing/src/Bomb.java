public class Bomb {
    private int x;              //爆炸坐标
    private int y;
    boolean isLife = true;
    private int life = 9;       //生命周期

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void next(){         //下一个周期
        if(life > 0) life--;
        else isLife = false;
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

    public int getLife() {
        return life;
    }
}
