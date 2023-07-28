package com.hspedu.tankgame6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Susie
 * @version 1.0
 *
 * 坦克大战绘图区
 * 监听 KeyListener
 * 绘制子弹\
 * 攻击坦克
 * 炸弹爆炸
 */
//为了让子弹能不断重绘，让MyPanel 实现 Runnable接口（可以当做线程），重写run，在run里不断重绘
public class MyPanel extends JPanel implements KeyListener, Runnable {

    //定义自己的坦克
    static Hero hero = null;
    //定义敌人的坦克，放在数组Vector中（多线程）
    static Vector<EnemyTank> enemyTanks = new Vector();
    //定义Node集合，保存Recorder.readRecords()获得的node对象
    Vector<Node> nodes = new Vector<>();

    //定义一个Vector,存放炸弹
    //当子弹击中坦克时，就加入一个bomb对象到bombs
    static Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //定义3张炸弹的图片，显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel(String key) throws IOException {
        //调用Recorder.readRecords(),读取上一局保存的数据
        //后面游戏初始化时（HspTankGame构造器），根据输入的值判断，是继续上一局，还是开始新游戏
        //这里初始化坦克对象时，也要根据选择，判断初始化EnemyTank集合的数据（新游戏），还是Node集合的数据（保存上一局）

        //nullPointerException
        //如果文件存在
        //如果文件不存在
        //获得记录文件的地址
        File file = new File(Recorder.getRecordFilePath());
        if (file.exists()) {
            nodes = Recorder.readRecords();
        } else {
            System.out.println("没有上一局游戏的记录，请开启新的游戏");
            key = "0";//将key重置为0，开启新游戏
        }

        hero = new Hero(600, 100);
        hero.setSpeed(3);

        switch (key) {
            case "0"://开新游戏
                //初始化敌人的坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    //让在EnemyTank类创建的敌人坦克对象，持有这里定义的Vector<EnemyTank> 集合，才会真正持有坦克数量
                    enemyTank.setEnemyTanks(enemyTanks);
                    //让在Recorder类里的recorderEnemyTank集合，持有这里创建的enemyTanks实例集合
                    Recorder.setrEnemyTanks(enemyTanks);

                    enemyTank.setDirect(2);//方向向下
                    //这里需要设置speed,坦克才能动起来，因为 MoveUp() {y -= speed;}这里调用的是speed,不设置默认 int speed = 0
                    enemyTank.setSpeed(2);
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();

                    //在enemyTank中初始化shot对象
                    //给enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());

                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);//每个敌人的坦克都添加了子弹对象
                    //启动shot对象，才能实现线程
                    new Thread(shot).start();

                    enemyTanks.add(enemyTank);//初始化后放入数组
                }
                break;
            case "1"://继续上一局
                //初始化敌人的坦克
                /**
                 * 从保存上一局数据的nodes中获得数据
                 * 获得保存上一局单个对象
                 * 获得上一局坦克的坐标值
                 */
                for (int i = 0; i < nodes.size(); i++) {//从保存上一局数据的nodes中获得数据
                    Node node = nodes.get(i);//获得保存上一局单个对象
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());//获得上一局坦克的坐标值
                    //让在EnemyTank类创建的敌人坦克对象，持有这里定义的Vector<EnemyTank> 集合，才会真正持有坦克数量
                    enemyTank.setEnemyTanks(enemyTanks);
                    //让在Recorder类里的recorderEnemyTank集合，持有这里创建的enemyTanks实例集合
                    Recorder.setrEnemyTanks(enemyTanks);

                    /**
                     * 获取上一局坦克的方向
                     */
                    enemyTank.setDirect(node.getDirect());//获取上一局坦克的方向
                    //这里需要设置speed,坦克才能动起来，因为 MoveUp() {y -= speed;}这里调用的是speed,不设置默认 int speed = 0
                    enemyTank.setSpeed(2);
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();

                    //在enemyTank中初始化shot对象
                    //给enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());

                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);//每个敌人的坦克都添加了子弹对象
                    //启动shot对象，才能实现线程
                    new Thread(shot).start();

                    enemyTanks.add(enemyTank);//初始化后放入数组
                }
                break;
        }

        //初始化3张图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

        //在这里，播放指定的音乐
        new AePlayWave("src\\111.wav").start();
    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {
        //先画出，输出玩家总成绩的面板内容
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累积击毁敌方坦克", 1020, 30);
        //画一个敌方坦克
        drawTank(1020, 60, g,0, 0);
        //画出统计结果，数字
        g.setColor(Color.BLACK);//这里需要重新设置画笔颜色，上一步修改了画笔颜色
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }


    //1 拿到画笔
    //2 画出图形
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色

        //画出积分面板
        showInfo(g);

        //1 画出自己的坦克，封装成方法
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
            //这里注意，画坦克时方向不能直接给int值，否则会锁是方向，无法切换
        }
//        else {
//            hitHeroInfo(g);
//        }

        //1.2 画出hero射击的单颗子弹
        if (hero.shot != null && hero.shot.isLive) {
            //g.fill3DRect(hero.shot.x, hero.shot.y, 1, 1, false);
            g.draw3DRect(hero.shot.x, hero.shot.y, 2, 2, false);
            System.out.println("子弹被绘制");
        }

        //画出hero的子弹集合,然后，去设置在按下J键后，可以发射多颗子弹
//        for (int i = 0; i < hero.shots.size() ; i++) {
//            Shot shot = hero.shots.get(i);
//            if (shot != null && shot.isLive == true) {
//                g.draw3DRect(shot.x, shot.y, 2, 2, false);
//            } else { //否则就删除无效子弹
//                hero.shots.remove(shot);
//            }
//        }

        //如果bombs集合中有炸弹，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前炸弹的life值画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb.life = 0, 就把这个炸弹从Bombs集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //2 画出敌人的tank--遍历取出敌人坦克数组，得到每个敌人坦克，再获取每个敌人坦克的X,Y
        for (int i = 0; i < enemyTanks.size(); i++) {
            //从Vector中取出一个地方坦克对象
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前敌方坦克是否存活
            if (enemyTank.isLive) {//如果敌方坦克还活着，才画出该坦克；否则就不再绘制了，甚至可以删除
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);

                //2.1 画出敌人坦克的子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出enemyTank成员中的shot
                    Shot shot = enemyTank.shots.get(j);
                    //绘制敌人坦克的子弹
                    if (shot.isLive) {//isLive == true
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        //子弹isLive == false,移除shot,否则会一直绘制
                        enemyTank.shots.remove(shot);
                    }

                }
            }
        }



    }

    /**
     *
     * @param x 坦克左上角的X坐标
     * @param y 坦克左上角的y坐标
     * @param g 画笔
     * @param direct 坦克方向（上下左右）
     * @param type 坦克类型
     */
    //编写方法，画出坦克
    // 1 用不同颜色的坦克，区分敌我
    // 2 坦克的运动方向，决定了画出的坦克的朝向
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型，给坦克设置不同颜色
        switch (type) {
            case 0://敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1://自己的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克的方向，来绘制坦克
        //0 向上  1 向右  2 向下  3 向左
        switch (direct) {
            case 0://向上
                g.fill3DRect(x, y, 10, 60, false);//坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间盖子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间圆形盖子
                g.drawLine(x + 20, y, x + 20, y + 30);//炮管
                break;
            case 1://向右
                g.fill3DRect(x, y, 60, 10, false);//坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间盖子
                g.fillOval(x + 20, y + 10, 20, 20);//坦克中间圆形盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//炮管
                break;
            case 2://向下
                g.fill3DRect(x, y, 10, 60, false);//坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间盖子
                g.fillOval(x + 10, y + 20, 20, 20);//坦克中间圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮管
                break;
            case 3: //向左
                g.fill3DRect(x, y, 60, 10, false);//坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间盖子
                g.fillOval(x + 20, y + 10, 20, 20);//坦克中间圆形盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//炮管
                break;
            default:
                System.out.println("暂时未处理");
        }
    }

    public void hitHero() {
        //判断敌人的子弹是否击中我方坦克
        //敌人有多个坦克，多颗子弹，遍历取出
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);//取出敌人坦克
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);//遍历enemy坦克对象的所有子弹
                //将敌人的子弹和我方坦克的范围进行比较
                hitTank(shot, hero);
                //需要在run方法调用hitHero()方法，进行判断
            }

        }

    }

    //我方坦克有多颗子弹，判断是否击中敌人坦克，不能只判断单颗子弹，如果多颗子弹击中敌人，所有击中的子弹都应该销毁
    //要从我方坦克的子弹集合取出所有子弹，再用所有子弹去和敌人的所有坦克比较，碰到的所有子弹，和被击中的所有坦克，销毁
    //这里单独列出比较是否击中的方法
       public void hitEnemyTank() {
//        //遍历我方所有子弹
//        for (int i = 0; i < hero.shots.size(); i++) {
//            Shot shot = hero.shots.get(i);//取出我方所有子弹
//            //判断我方子弹是否击中敌人坦克
//            //该方法在run方法里需要调用
//            if (shot != null && shot.isLive) {//if自己的子弹还没有销毁
//                //shot != null,按了J以后hero才会打子弹，运行时会报nullPointerException
//                //遍历敌人的所有坦克，看到底子弹击中了哪一个
//                for (int j = 0; j < enemyTanks.size(); j++) {
//                    EnemyTank enemyTank = enemyTanks.get(j);//拿到每一个敌人的坦克，看是否击中
//                    hitTank(hero.shot, enemyTank);//hero.shot 我方子弹, enemyTank 敌人坦克
//                    //假如打中了某个地方坦克，也就是我方子弹的范围在敌方坦克范围，销毁我方子弹和敌方坦克
//                }
//            }
//        }
           if (hero.shot != null && hero.shot.isLive) {//if自己的子弹还没有销毁
               //shot != null,按了J以后hero才会打子弹，运行时会报nullPointerException
               //遍历敌人的所有坦克，看到底子弹击中了哪一个
               for (int j = 0; j < enemyTanks.size(); j++) {
                   EnemyTank enemyTank = enemyTanks.get(j);//拿到每一个敌人的坦克，看是否击中
                   hitTank(hero.shot, enemyTank);//hero.shot 我方子弹, enemyTank 敌人坦克
                   //假如打中了某个地方坦克，也就是我方子弹的范围在敌方坦克范围，销毁我方子弹和敌方坦克
               }
           }
       }

    //编写方法，判断我方的子弹是否击中敌人的坦克
    //什么时候判断子弹是否击中敌人坦克？--run方法重绘时
    //这里传参时，类型定为父类Tank,可以传入敌人坦克对象和我方坦克对象
    public static void hitTank(Shot s, Tank tank) {
        //判断s是否击中坦克
        switch (tank.getDirect()) {
            case 0://up
            case 2://down
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    //把子弹销毁
                    s.isLive = false;
                    //敌人坦克销毁
                    tank.isLive = false;
                    //一旦击中坦克，就从集合里删除该坦克
                    if (tank instanceof EnemyTank) {
                    enemyTanks.remove(tank);
                    }
                    if (tank instanceof Hero) {
                        System.out.println("GAME OVER! 您的坦克被击毁！");
                        System.exit(0);
                    }

                    //如果打掉的是敌方坦克，Recorder.addAllEnemyNum()
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建Bomb对象，加入到bombs集合中
                    //炸弹的x,y坐标和被击中的敌方坦克坐标一样
                    //每个炸弹由3张图片构成
                    //如何显示炸弹？在Paint方法绘制
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://right
            case 3://left
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                && s.y > tank.getY() && s.y < tank.getY() + 40){
                    //销毁子弹
                    s.isLive = false;
                    //敌人坦克销毁
                    tank.isLive = false;
                    //一旦击中坦克，就从集合里删除该坦克
                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                    }
                    if (tank instanceof Hero) {
                        System.out.println("GAME OVER! 您的坦克被击毁！");
                        System.exit(0);
                    }

                    //如果打掉的是敌方坦克，Recorder.addAllEnemyNum()
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }

                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //w--上 0;  D--right 1; S--down 2;  A--left 3
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {//控制我方坦克移动的边界
                hero.MoveUp();//可以向上移动
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.MoveRight();//可以向右移动
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.MoveDown();//可以向下移动
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.MoveLeft();//可以向左移动
            }
        }

        //如果用户按下J，发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //判断hero的已经打出的子弹是否消亡
            //第一次shot为空，创建子弹对象后，等子弹生命消亡后，按J键时再创建新的子弹
            if (hero.shot == null || !hero.shot.isLive) {
                hero.shotEnemyTank();
            }
            //重绘
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //Hero被击中，画出提示信息
    public void hitHeroInfo(Graphics g) {
        g.setColor(Color.RED);
        Font font = new Font("宋体", Font.BOLD, 100);
        g.drawString("GAME OVER! 您的坦克被击毁！", 300, 800);
    }

    @Override
        public void run () {//每隔一段时间，重绘区域
            //休眠100毫秒
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //判断是否击中敌人坦克
                //hitEnemyTank();//有设计多颗子弹的情况
                hitEnemyTank();

                //判断敌方坦克是否击中Hero
                hitHero();
                //hero.isLive = false;


                //重绘
                //System.out.println("刷新绘图区");
                this.repaint();
            }
        }

    }