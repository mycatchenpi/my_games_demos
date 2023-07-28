package com.hspedu.tankgame6;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * @author Susie
 * @version 1.0
 */
public class HspTankGame06 extends JFrame {

    //定义MyPanel
    MyPanel mp = null;

    public static void main(String[] args) throws IOException {

        HspTankGame06 hspTankGame02 = new HspTankGame06();
    }

    public HspTankGame06() throws IOException {
        //后面游戏初始化时（HspTankGame构造器），根据输入的值判断，是继续上一局，还是开始新游戏
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入(0/1)： 0-新开游戏  1-继续游戏");
        String key  = scanner.next();

        mp = new MyPanel(key);
        //将mp放入Thread并启动,重绘子弹(将MyPanel当做线程)
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1300, 950);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame 中增加窗口监听器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                    System.out.println("您击毁敌方坦克= " + Recorder.getAllEnemyTankNum());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);//退出监听器
            }
        });

    }




//    //编写方法，启动游戏时可以选择，是新开游戏，还是继续上局游戏
//    public static void choseGameType() throws IOException {
//        System.out.println("请输入(0/1)： 0-新开游戏  1-继续游戏");
//        Scanner scanner = new Scanner(System.in);
//        char input = scanner.next().charAt(0);
//        switch (input) {
//            case '0'://新开游戏
//
//                break;
//            case '1'://继续游戏
//                //读取保存的文件
//                String filePath = "F:\\1 recent plans\\IT\\01hsp-java\\Java notes\\createFileTemp\\myRecord.txt";
//                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
//                break;
//        }
//    }
}
