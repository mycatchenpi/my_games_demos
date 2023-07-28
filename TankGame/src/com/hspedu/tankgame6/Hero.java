package com.hspedu.tankgame6;

/**
 * @author Susie
 * @version 1.0
 */
public class Hero extends Tank {//自己的坦克
    //定义一个Shot对象,表示一个shot行为（线程），自己的坦克打出子弹
    Shot shot = null;
    //定义一个集合，将多个子弹对象放入，可以打出多发子弹，后面绘制时遍历集合画出子弹
    //Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        //将每次hero打出的子弹控制在5颗
//        if (shots.size() == 5) {
//            return;
//        }

        //创建一个Shot实例对象
        //根据当前Hero坦克的位置和方向来创建Shot对象
        switch (getDirect()) { //得到Hero对象方向
            case 0:// up
                shot = new Shot(getX() + 20, getY(), 0);//创建子弹，得到启动点坐标和子弹移动的方向
                break;
            case 1://right
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://down
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://left
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //将根据坦克位置创建的子弹放入集合,到MyPanel初始化时，取出单个子弹对象
        //shots.add(shot);

        //启动Shot线程j
        new Thread(shot).start();
    }
}
