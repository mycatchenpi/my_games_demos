package com.hspedu.tankgame6;

/**
 * @author Susie
 * @version 1.0
 * Thread
 */
public class Shot implements Runnable{
    int x;//子弹的x,y坐标
    int y;
    int direct = 0;//子弹的方向
    int speed = 2;//子弹的速度
    boolean isLive = true;//子弹是否还存活，是否还可以移动

    //构造器
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {

        while (true) {

            //让子弹线程休眠发射，方便观察
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //根据方向来改变x,y坐标
            switch (direct) {
                case 0://向上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2://向下
                    y += speed;
                    break;
                case 3://向左
                    x -= speed;
                    break;
            }
            //测试
            //System.out.println("子弹 x=" + x + " y=" + y);

            //当子弹到达面板的边界时，销毁--把启动的子弹的线程销毁
            //当子弹碰到敌人的坦克时，也应该结束子弹线程 --isLive == false
            // 因为在MyPanel中，hitTank方法设定，击中敌方坦克时，s.isLive = false;
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive))  {
                System.out.println("子弹退出边界");
                isLive = false;
                break;
            }
        }
    }
}
