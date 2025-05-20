package com.yunmeng.ui;

import Account.Account;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class registerJFrame extends JFrame implements MouseListener {


    JTextField user=new JTextField();
    JPasswordField password=new JPasswordField();
    JPasswordField rePassword=new JPasswordField();
    JTextField check=new JTextField();
    JLabel passwordShow=new JLabel(new ImageIcon("image/login/显示密码.png"));
    JLabel passwordHidden=new JLabel(new ImageIcon("image/login/显示密码按下.png"));
    JLabel rePasswordShow=new JLabel(new ImageIcon("image/login/显示密码.png"));
    JLabel rePasswordHidden=new JLabel(new ImageIcon("image/login/显示密码按下.png"));
    JLabel res=new JLabel(new ImageIcon("image/register/注册按钮.png"));
    JLabel reSet=new JLabel(new ImageIcon("image/register/重置按钮.png"));
    JLabel checkLabel=new JLabel();

    private String checkCode;

    private String generateCheckCode(){
        Random r=new Random();
        StringBuilder str=new StringBuilder();
        int kind,upLetter,downLetter,digit;
        for (int i = 0; i < 6;) {
            kind=r.nextInt(1,4);
            upLetter=r.nextInt(26);
            downLetter=r.nextInt(26);
            digit=r.nextInt(10);
            switch (kind){
                case 1:
                    str.append(digit);
                    break;
                case 2:
                    str.append((char)('a'+downLetter));
                    break;
                case 3:
                    str.append((char)('A'+upLetter));
                    break;
            }
            i++;
        }
        return str.toString();
    }
    public registerJFrame(){
        this.setSize(488,500);
        //界面标题并且置顶居中
        this.setTitle("注册界面");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);

        //关闭退出程序
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setLayout(null);
        this.addMouseListener(this);
        img();

        this.setVisible(true);
    }
    private void img(){
        user.setBounds(130,145,250,30);
        JLabel userLabel=new JLabel(new ImageIcon("image/register/注册用户名.png"));
        userLabel.setBounds(35,150,79,17);
        this.add(user);
        this.add(userLabel);

        password.setBounds(130,185,250,30);
        JLabel passwordLabel=new JLabel(new ImageIcon("image/register/注册密码.png"));
        passwordLabel.setBounds(40,190,64,16);

        rePassword.setBounds(130,225,250,30);
        JLabel rePasswordLabel=new JLabel(new ImageIcon("image/register/再次输入密码.png"));
        rePasswordLabel.setBounds(30,230,96,17);

        check.setBounds(165,264,200,44);
        checkCode=generateCheckCode();
        checkLabel.setText(checkCode);
        checkLabel.setBounds(40,265,120,40);
        checkLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // 设置文本居中对齐
        checkLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // 设置字体大小为24号字（可以根据需要调整）
        Font font = new Font("Arial", Font.PLAIN, 18);
        checkLabel.setFont(font);
        // 设置文字颜色为红色
        checkLabel.setForeground(Color.RED);


        res.setBounds(60,340,128,47);

        reSet.setBounds(250,340,128,47);

        passwordShow.setBounds(385,185,18,29);
        passwordHidden.setBounds(385,185,18,29);

        rePasswordShow.setBounds(385,225,18,29);
        rePasswordHidden.setBounds(385,225,18,29);

        passwordShow.addMouseListener(this);
        passwordHidden.addMouseListener(this);
        rePasswordShow.addMouseListener(this);
        rePasswordHidden.addMouseListener(this);
        res.addMouseListener(this);
        reSet.addMouseListener(this);
        checkLabel.addMouseListener(this);
        

        this.add(user);
        this.add(userLabel);
        this.add(password);
        this.add(passwordLabel);
        this.add(rePassword);
        this.add(rePasswordLabel);
        this.add(passwordShow);
        this.add(passwordHidden);
        this.add(rePasswordShow);
        this.add(rePasswordHidden);
        this.add(check);
        this.add(checkLabel);
        this.add(res);
        this.add(reSet);
        //添加背景
        JLabel background=new JLabel( new ImageIcon("image/register/background.png"));
        background.setBounds(5,40,470,390);
        this.add(background);

        passwordHidden.setVisible(false);
        rePasswordHidden.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj=e.getSource();
        if(obj==passwordShow){
            passwordShow.setVisible(false);
            passwordHidden.setVisible(true);
            password.setEchoChar('\0');
        } else if (obj==passwordHidden) {
            passwordShow.setVisible(true);
            passwordHidden.setVisible(false);
            password.setEchoChar('*');
        }  else if(obj==rePasswordShow){
            rePasswordShow.setVisible(false);
            rePasswordHidden.setVisible(true);
            rePassword.setEchoChar('\0');
        } else if (obj==rePasswordHidden) {
            rePasswordShow.setVisible(true);
            rePasswordHidden.setVisible(false);
            rePassword.setEchoChar('*');
        }else if(obj==checkLabel){
            checkCode=generateCheckCode();
            checkLabel.setText(checkCode);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj=e.getSource();
        if(obj==res){
            res.setIcon(new ImageIcon("image/register/注册按下.png"));
            //注册逻辑
            String userName=user.getText();
            String userPassword=new String(password.getPassword());
            String reUserPassword=new String(rePassword.getPassword());
            boolean checkData=false;
            if(userName.isEmpty()){
                JOptionPane.showMessageDialog(this, "用户名不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
            }else if(userPassword.isEmpty()){
                JOptionPane.showMessageDialog(this, "密码不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
            }else if(reUserPassword.isEmpty()){
                JOptionPane.showMessageDialog(this, "重复密码不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
            }else{
                //没有空指针的情况
                if(userPassword.equals(reUserPassword)){
                    checkData=true;
                }else{
                    JOptionPane.showMessageDialog(this, "两次输入密码不一致！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
            if(checkData){
                System.out.println("看看我有没有创建账户");
                Account a = new Account(userName,userPassword);
                //确定用户不存在再添加到数据库
                if(a.exist(a,this)){
                    a.saveAccount(a);
                    this.setVisible(false);
                    new loginJFrame();
                }else {
                    JOptionPane.showMessageDialog(this, "用户已经存在！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }

        } else if (obj==reSet) {
            reSet.setIcon(new ImageIcon("image/register/重置按下.png"));
            user.setText("");
            password.setText("");
            rePassword.setText("");
            check.setText("");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj=e.getSource();
        if (obj==res) {
            res.setIcon(new ImageIcon("image/register/注册按钮.png"));
        } else if (obj == reSet) {
            reSet.setIcon(new ImageIcon("image/register/重置按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
