import java.security.cert.CRLSelector;

public class EnemyTank extends Tank implements Runnable{
    public EnemyTank(int x, int y) {
        super(x, y);
        setDirect(-1);
        setType(1);
        setSpeed(1);
    }

    @Override
    public void run() {
        int randomDirect = getDirect();
        int randomStep;
        while (isLife()){
            randomDirect = (int)(Math.random() * 5 - 2);    //随机移动的方向
            randomStep = (int)(Math.random() * 20 + 30);    //随机移动的步数
            if(randomDirect != 0) setDirect(randomDirect);  //如果随机出0，则不改变方向
            if(isCrash()) continue;                         //碰撞之后不作移动，直接开始改变方向
            for (int i = 0; i < randomStep; i++) {
                //边界检查，碰撞边界直接反方向移动
                if(getX() < 50) {
                    setDirect(-2);
                }
                if(getX() > 950) {
                    setDirect(2);
                }
                if(getY() < 50) {
                    setDirect(-1);
                }
                if(getY() > 750) {
                    setDirect(1);
                }
                //按随机的步数移动
                switch (getDirect()){
                    case 1:
                        moveUp();
                        break;
                    case -1:
                        moveDown();
                        break;
                    case 2 :
                        moveLeft();
                        break;
                    case -2:
                        moveRight();
                        break;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(getBullets().size() < 5) Shot(); //发射子弹
        }

    }
}
