package com.sgu.game.entity;

import com.sgu.game.Constant;

public class BulletElement extends Element {
    Direction d;
    //调用了子弹的无参构造 所以并没有给子弹的path属性赋值 导致在绘制子弹图片时 出现空指针异常
    public BulletElement() {
        super();
    }
    public BulletElement(int x, int y) {
        super(x, y);
        super.path = "tankwar_v2.0_byLwjgl\\img\\bullet_u.gif";
        super.getSize();
    }

    public BulletElement(int x, int y, int width, int height, Direction d) {
        //让子弹记录当前坦克的方向
        this.d = d;
        //创建子弹对象之前先计算出子弹图片的宽和高
        switch (d) {
            case UP:
                super.path = "tankwar_v2.0_byLwjgl\\img\\bullet_u.gif";
                //先根据坦克方向决定子弹的图片方向 再去计算子弹图片的宽高
                super.getSize();
                super.x = x + (width - super.width) / 2;
                super.y = y - super.height;
                break;
            case DOWN:
                super.path = "tankwar_v2.0_byLwjgl\\img\\bullet_d.gif";
                super.getSize();
                super.x = x + (width - super.width) / 2;
                super.y = y + height;
                break;
            case LEFT:
                super.path = "tankwar_v2.0_byLwjgl\\img\\bullet_l.gif";
                super.getSize();
                super.x = x - super.width;
                super.y = y + (height - super.height) / 2;
                break;
            case RIGHT:
                super.path = "tankwar_v2.0_byLwjgl\\img\\bullet_r.gif";
                super.getSize();
                super.x = x + width;
                super.y = y + (height - super.height) / 2;
                break;
        }
    }

    //此处只需要让子弹类重写元素类中的draw方法即可
    @Override
    public void draw() {
        //调用父类的绘制方法 DrawUtils.draw(path, x, y); 把子弹图片展示在屏幕上
        super.draw();
        //父类的绘制方法调用完毕之后 子类重写的绘制方法需要自主的更改子弹的坐标
        switch (d) {
            case UP:
                //Y轴坐标在减小
                super.y -= 10;
                break;
            case DOWN:
                //Y轴坐标在增加
                super.y += 10;
                break;
            case LEFT:
                //X轴坐标在减小
                super.x -= 10;
                break;
            case RIGHT:
                //X轴坐标在增加
                super.x += 10;
                break;
        }
    }
    public boolean isOutSize(){
        boolean flag = false;
        if(super.x + super.width < 0 || super.y + super.height < 0 || super.x > Constant.width || super.y > Constant.height){
            flag = true;
        }
        return flag;
    }
}
