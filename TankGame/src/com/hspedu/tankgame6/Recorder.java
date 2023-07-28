package com.hspedu.tankgame6;

import java.io.*;
import java.util.Vector;

/**
 * @author Susie
 * @version 1.0
 *
 * 用于记录相关的信息，和文件交互
 * 1 记录玩家总成绩
 * 2 记录退出游戏时敌人坦克的坐标、方向
 */
public class Recorder {
    //记录我方击毁敌人坦克数量
    private static int allEnemyTankNum = 0;
    //定义I/O对象
    private static BufferedWriter bw = null;//保存数据到指定file
    private static BufferedReader br = null;//读取文件数据
    //定义记录文件路径
    //把记录文件保存到src下，项目文件
    //private static String recordFilePath = "F:\\1 recent plans\\IT\\01hsp-java\\Java notes\\createFileTemp\\myRecord.txt";
    private static String recordFilePath = "E:\\1_code_workspace\\java-code\\java-hsp\\chapter 20\\src\\myRecord.txt";
    //定义敌人坦克集合，需要在MyPanel里，启动paint时，让这里的集合持有MyPel创建的EnemyTank对象实例
    //这里提供set方法，在MyPanel调用
    private static Vector<EnemyTank> rEnemyTanks = new Vector<>();
    //提供Vector<Node>集合
    private static Vector<Node> nodes = new Vector<>();

    //返回记录文件路径，在MyPanel中调用
    public static String getRecordFilePath() {
        return recordFilePath;
    }

    public static void setrEnemyTanks(Vector<EnemyTank> rEnemyTanks) {
        Recorder.rEnemyTanks = rEnemyTanks;
    }

    //get击毁地方坦克的数量
    //在MyPanel画出累积分数结果时，设置数量为Recorder.getAllEnemyTankNum()
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    //set击毁地方坦克的数量
    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当击毁一辆敌人坦克，就应该++allEnemyTankNum
    //当敌方坦克被击毁时，调用这个方法
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }


    public static void keepRecord() throws IOException {
        bw = new BufferedWriter(new FileWriter(recordFilePath));
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(recordFilePath));

        //退出时，保存击毁地方坦克的数量--->存盘退出
        //在JFrame 中增加窗口监听器，正在关闭窗口时，保存文件，调用Recorder.recordFile()
        bw.write(allEnemyTankNum + "\r\n");
        //bw.newLine();//换行,这里使用会出现乱码,底层调用 private String lineSeparator;

        //记录退出游戏时敌人坦克的坐标、方向
        //遍历敌人坦克
        for (int i = 0; i < rEnemyTanks.size(); i++) {
            EnemyTank enemyTank = rEnemyTanks.get(i);//取出敌人坦克对象
            if (enemyTank.isLive) {
                //记录x,y,坦克方向
                String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                bw.write(record + "\r\n");
            }
        }
        if (bw != null) {
            bw.close();
        }
    }

    //读取保存的数据，存放到Nodes集合Vector中，后续遍历，初始化上一局相应的坦克数量、坐标位置
    //该方法，在继续上一局、启动时调用--MyPanel
    public static Vector<Node> readRecords() throws IOException {
        String line;
        //创建BufferedReader
        br = new BufferedReader(new FileReader(recordFilePath));
        //第一行时击毁的坦克数量
        allEnemyTankNum =Integer.parseInt( br.readLine());
        //循环读取文件，生成Node集合
        while ((line = br.readLine()) != null) {
            String[] xyd = line.split(" ");//根据空格“ ”分割
            Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                            Integer.parseInt(xyd[2]));//x, y, direction
            nodes.add(node);//将值添加到Node集合,保存为对象
        }
        if (br != null) {
            br.close();
        }
        return nodes;
    }
}
