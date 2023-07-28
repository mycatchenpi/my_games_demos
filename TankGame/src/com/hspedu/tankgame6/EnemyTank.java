package com.hspedu.tankgame6;

import java.util.Vector;

/**
 * @author Susie
 * @version 1.0
 * Thread
 */
@SuppressWarnings("all")
public class EnemyTank extends Tank implements Runnable {

    //在敌人坦克类，用Vector保存多个Shot对象
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    /*
    功能：避免敌人坦克重叠
     */
    //用Vector集合属性保存多个敌人坦克变量，在方法里调用进行比较
    Vector<EnemyTank> enemyTanks = new Vector<>();

    //在MyPanel里已经有敌人坦克的Vector，用set方法拿到MyPanel里的vector对象
    //在myPanel调用set方法
    //code:
    //EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
    //让创建的敌人坦克对象，持有这里定义的Vector<EnemyTank> 集合，才会真正持有坦克数量
    //   enemyTank.setEnemyTanks(enemyTanks);
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前这个敌人坦克对象，是否和其他敌人坦克对象发生碰撞
    //当前敌人坦克对象，有up、right、down、left共4个方向
    //每一个方向，地方坦克有上下move、左右move共2种形态，其x,y坐标范围不同
    //每一次判断碰撞，都需要判断2个角的坐标值，是否在另一坦克坐标值范围内
    //写完后，在run()方法里调用坦克move()方法的地方，增加条件---> !isTouchEnemyTank,如果没有重叠，就移动
    public boolean isTouchEnemyTank() {
        switch (this.getDirect()) {
            case 0://up  当前坦克向上，图形竖着
                //循环取出Vector里的对象
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //除了自己，当前坦克对象会和其他所有坦克对象进行比较，看是否重叠
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        //其他坦克上下Move时,坦克图像竖着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克左上角坐标：(this.getX(), this.getY())
                        //当前坦克右上角坐标：(this.getX() + 40, this.getY())
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 40]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右上角坐标：(this.getX + 40, this.get(Y))
                            //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 40]
                            //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }

                        //其他坦克左右Move时,坦克图像横着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克左上角坐标：(this.getX(), this.getY())
                        //当前坦克右上角坐标：(this.getX() + 40, this.getY())
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 60]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //左上角碰撞判断，坐标: (this.getX(), this.getY())
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 40) {
                                    return true;
                                }
                                //当前坦克右上角坐标：(this.getX + 40, this.get(Y))
                                if (this.getX() + 40 >= enemyTank.getX()
                                        && this.getX() + 40 <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 40) {
                                    return true;
                                }
                        }
                    }
                }
                break;

            case 1://right
                //循环取出Vector里的对象
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //除了自己，当前坦克对象会和其他所有坦克对象进行比较，看是否重叠
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        //其他坦克上下Move时,坦克图像竖着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克右上角坐标：(this.getX() + 60, this.getY())
                        //当前坦克右下角坐标：(this.getX() + 60, this.getY() + 40)
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 40]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 60]

                        //右上角碰撞判断 (this.getX() + 60, this.getY())
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角坐标：(this.getX() + 60, this.getY() + 40)
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() + 40>= enemyTank.getY()
                                    && this.getY()  + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }

                        //其他坦克左右Move时,坦克图像横着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克右上坐标：(this.getX() + 60, this.getY())
                        //当前坦克右下角坐标：(this.getX() + 60, this.getY() + 40)
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 60]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //右上角碰撞判断，坐标:(this.getX() + 60, this.getY())
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标：(this.getX() + 60, this.getY() + 40)
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;

            case 2://down
                //循环取出Vector里的对象
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //除了自己，当前坦克对象会和其他所有坦克对象进行比较，看是否重叠
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        //其他坦克上下Move时,坦克图像竖着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克左下角坐标：(this.getX(), this.getY() + 60)
                        //当前坦克右下角坐标：(this.getX() + 40, this.getY() + 60)
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 40]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 60]

                        //当前坦克左下角坐标：(this.getX(), this.getY() + 60)
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角坐标：(this.getX() + 40, this.getY() + 60)
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() + 60>= enemyTank.getY()
                                    && this.getY()  + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }

                        //其他坦克左右Move时,坦克图像横着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克左下角坐标：(this.getX(), this.getY() + 60)
                        //当前坦克右下角坐标：(this.getX() + 40, this.getY() + 60)
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 60]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克左下角坐标：(this.getX(), this.getY() + 60)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标：(this.getX() + 40, this.getY() + 60)
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;

            case 3://left
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //除了自己，当前坦克对象会和其他所有坦克对象进行比较，看是否重叠
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        //其他坦克上下Move时,坦克图像竖着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克左上角坐标：(this.getX(), this.getY())
                        //当前坦克左下角坐标：(this.getX(), this.getY() + 40)
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 40]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 60]

                        //当前坦克左上角坐标：(this.getX(), this.getY())
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克左下角坐标：(this.getX(), this.getY() + 40)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40  //this的x落在其他坦克的x范围
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY()  + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }

                        //其他坦克左右Move时,坦克图像横着
                        //判断当前坦克的左上角坐标 和 右上角坐标 的x和y值，是否在其他坦克的x,y范围内
                        //当前坦克左上角坐标：(this.getX(), this.getY())
                        //当前坦克左下角坐标：(this.getX(), this.getY() + 40)
                        //其他坦克的x值范围：[enemyTank.getX(), enemyTank.getX() + 60]
                        //其他坦克的Y值范围：[enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克左上角坐标：(this.getX(), this.getY())
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克左下角坐标：(this.getX(), this.getY() + 40)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60  //this的x落在其他坦克的x范围
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;//如果没有进入switch，就返回false
    }


    public EnemyTank(int x, int y) {
        super(x, y);
    }


    @Override
    public void run() {

        while (true) {

            //敌人子弹消亡后，可以再打一颗子弹
            if (isLive && shots.size() < 1) {
                Shot s = null;
                //如果敌人坦克还活着，而且子弹数为0（小于1），就根据敌人坦克位置，new 一颗新的子弹
                switch (getDirect()) {
                    case 0://up
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1://right
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2://down
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3://left
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                //将创建的子弹对象添加到集合
                shots.add(s);
                //启动子弹线程
                new Thread(s).start();
            }


            //根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0://up
                    //让坦克在一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        //让敌方坦克在一定范围移动
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            //竖着画的坦克,y不等于0,并且没有和别的坦克重叠，就移动,否则不移动
                            MoveUp();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1://right
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {//横着画的坦克，绘图区面板横宽1000
                            MoveRight();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://down
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {//面板竖着高度750
                            MoveDown();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://left
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            MoveLeft();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            //移动完休眠一下

            //随机改变坦克方向
            setDirect((int)(Math.random() * 4));//[0,4)--> int[0,3]
            // (int)后面的公式要单独加括号(Math.random() * 4)

            //退出机制--该坦克被打掉，就退出
            if (!isLive) {//如果isLive = false
                break;//退出
            }
            //启动线程，在创建敌人坦克对象时启动-->MyPanel构造器
        }
    }
}
