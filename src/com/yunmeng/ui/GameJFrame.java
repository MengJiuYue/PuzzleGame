package com.yunmeng.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    private static String ALL_PATH="all.jpg";
    private static String WIN_PATH="image/win.png";
     int x;
     int y;
    int[][] imgIndex = initializeImgIndex();
    int step=0;
    String kind="animal";
    private static String PATH="image/animal/animal3/";
    JMenuItem replay=new JMenuItem("重新游戏");
    JMenuItem relogin=new JMenuItem("重新登录");
    JMenuItem closeGame=new JMenuItem("关闭游戏");
    JMenu about=new JMenu("关于我们");
    JMenuItem QQ=new JMenuItem("QQ");
    JMenu change=new JMenu("更换图片");
    JMenuItem girl=new JMenuItem("美女");
    JMenuItem animal=new JMenuItem("动物");
    JMenuItem sport=new JMenuItem("运动");


    public GameJFrame(){
        setJFrame();

        setBar();

        initializeImg();

        //显示界面`
        this.setVisible(true);
    }

    private void initializeImg() {
        this.getContentPane().removeAll();
        //加载图片
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                JLabel label=new JLabel(new ImageIcon(PATH+imgIndex[i][j]+".jpg"));
                label.setBounds(105*j+83,105*i+134,105,105);
                label.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.add(label);
            }
        }
        //添加背景
        JLabel background=new JLabel( new ImageIcon("image/background.png"));
        background.setBounds(40,40,500,560);
        this.add(background);

        JLabel count=new JLabel("步数:"+step);
        count.setBounds(50,30,100,20);
        this.add(count);

        this.getContentPane().repaint();
    }

    private  int[][] initializeImgIndex() {
        //打乱图片索引
        Random r=new Random();
        int []index={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        for (int i = 0; i < index.length; i++) {
            int R=r.nextInt(0,16);
            int temp=index[i];
            index[i]=index[R];
            index[R]=temp;
        }
        int [][]imgIndex=new int[4][4];
        int num=0;
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                imgIndex[i][j]=index[num++];
                if(imgIndex[i][j]==0){
                    x=i;
                    y=j;
                }
            }
        }
        return imgIndex;
    }

    private void setBar() {
        //菜单栏
        JMenuBar bar=new JMenuBar();

        JMenu function =new JMenu("功能");
        function.add(replay);
        function.add(relogin);
        function.add(closeGame);
        function.add(change);
        change.add(animal);
        change.add(girl);
        change.add(sport);
        about.add(QQ);

        bar.add(function);
        bar.add(about);

        this.setJMenuBar(bar);

        replay.addActionListener(this);
        relogin.addActionListener(this);
        closeGame.addActionListener(this);
        QQ.addActionListener(this);
        animal.addActionListener(this);
        girl.addActionListener(this);
        sport.addActionListener(this);
    }

    private void setJFrame() {
        //界面宽高
        this.setSize(603,680);

        //界面标题并且置顶居中
        this.setTitle("拼图游戏单机版 V1.0");
        this.setLocationRelativeTo(null);

        //关闭退出程序
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //取消默认居中
        this.setLayout(null);

        this.addKeyListener(this);
    }


    private void move(int select) {
        switch(select){
            case 37://左
            {
                if(y+1<4){
                    imgIndex[x][y]=imgIndex[x][++y];
                    imgIndex[x][y]=0;
                    initializeImg();
                    System.out.println("左");
                }

            }
            break;
            case 38://上
            {
                if(x+1<4){
                    imgIndex[x][y]=imgIndex[++x][y];
                    imgIndex[x][y]=0;
                    initializeImg();
                    System.out.println("上");
                }
            }
            break;
            case 39://右
            {
                if(y-1>=0){
                    imgIndex[x][y]=imgIndex[x][--y];
                    imgIndex[x][y]=0;
                    initializeImg();
                    System.out.println("右");
                }
            }
            break;
            case 40://下
            {
                if(x-1>=0){
                    imgIndex[x][y]=imgIndex[--x][y];
                    imgIndex[x][y]=0;
                    initializeImg();
                    System.out.println("下");
                }
            }
            break;
            default:
                break;
        }
        if(select>=37&&select<=40) step++;
        if(checkWin(imgIndex)) showNewImg(false,WIN_PATH,203,283,197,73);
    }

    private boolean checkWin(int [][]data){
        int [][]win={
                {1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}
        };
        for (int i = 0; i < data.length; i++) {
            for (int i1 = 0; i1 < data[i].length; i1++) {
                if(win[i][i1]!=data[i][i1]) return false;
            }
        }
        return true;
    }
    private void showNewImg(boolean flag, String path,int x,int y,int w,int h){
        this.getContentPane().removeAll();
        //flag判断path是不是标准流格式图片,不是就直接路径
        JLabel New=new JLabel(new ImageIcon(flag?PATH+path:path));
        New.setBounds(x,y,w,h);
        this.add(New);
        //添加背景
        JLabel background=new JLabel( new ImageIcon("image/background.png"));
        background.setBounds(40,40,500,560);
        this.add(background);
        this.getContentPane().repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        move(e.getKeyCode());

        if(e.getKeyCode()==65)//显示完整图片
            showNewImg(true, ALL_PATH,40,40,508,560);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==65) initializeImg();
        if (e.getKeyCode()==87){
            int num=1;
            for (int i = 0; i < 4; i++) {
                for(int j=0;j<4;j++){
                    if(num<16)imgIndex[i][j]=num++;
                    else imgIndex[i][j]=0;
                }
            }
            x=3;y=3;
            initializeImg();;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object item=e.getSource();
        if(item==replay){
            step=0;
            imgIndex = initializeImgIndex();
            initializeImg();

        }else if(item==relogin){
            this.setVisible(false);
            new loginJFrame();
        }else if(item==closeGame){
            System.exit(0);
        }else if(item==QQ){
            JDialog jdl=new JDialog();
            JLabel qq=new JLabel(new ImageIcon("image/about.png"));
            qq.setBounds(0,0,258,258);
            jdl.getContentPane().add(qq);
            jdl.setSize(344,344);
            jdl.setAlwaysOnTop(true);
            jdl.setLocationRelativeTo(null);
            jdl.setModal(true);
            jdl.setVisible(true);
        } else if (item==animal) {
            changeImg("animal");
        } else if (item==girl) {
            changeImg("girl");
        } else if (item==sport) {
            changeImg("sport");
        }


    }

    private void changeImg(String kind) {
        this.kind=kind;
        int Index=getImgIndex(kind);
        PATH="image/"+kind+"/"+kind+Index+"/";
        step=0;
        imgIndex = initializeImgIndex();
        initializeImg();
    }

    private int getImgIndex(String kind){
        int []kinds=new int[2];
        switch (kind) {
            case "animal" -> {
                kinds[0] = 1;
                kinds[1] = 9;
            }
            case "girl" -> {
                kinds[0] = 1;
                kinds[1] = 14;
            }
            case "sport" -> {
                kinds[0] = 1;
                kinds[1] = 11;
            }
        }
        Random r=new Random();
        return r.nextInt(kinds[0],kinds[1]);
    }
}
