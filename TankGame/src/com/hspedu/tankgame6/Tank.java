package com.hspedu.tankgame6;

/**
 * @author Susie
 * @version 1.0
 */
public class Tank {
    private int x;
    private int y;
    private int direct;
    private int speed;
    boolean isLive = true;//在父类设置，所有坦克子类都可以调用

    //上下左右移动方法 封装
    public void MoveUp() {
        y -= speed;
    }
    public void MoveRight() {
        x += speed;
    }
    public void MoveDown() {
        y += speed;
    }
    public void MoveLeft() {
        x -= speed;
    }

    public int getSpeed() {
        return speed;
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

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
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
}
