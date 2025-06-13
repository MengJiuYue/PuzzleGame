package AppUi;
import AccountServe.Account;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class loginJFrame extends JFrame implements MouseListener {

    JTextField user=new JTextField();
    JPasswordField password=new JPasswordField();
    JLabel passwordShow=new JLabel(new ImageIcon(getClass().getResource("/image/login/显示密码.png")));
    JLabel passwordHidden=new JLabel(new ImageIcon(getClass().getResource("/image/login/显示密码按下.png")));
    JLabel login=new JLabel(new ImageIcon(getClass().getResource("/image/login/登录按钮.png")));
    JLabel res=new JLabel(new ImageIcon(getClass().getResource("/image/login/注册按钮.png")));

    public loginJFrame(){
        this.setSize(488,430);
        //界面标题并且置顶居中
        this.setTitle("登录界面");
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

        user.setBounds(80,120,315,40);
        JLabel userLabel=new JLabel(new ImageIcon(getClass().getResource("/image/login/用户名.png")));
        userLabel.setBounds(25,130,47,17);
        this.add(user);
        this.add(userLabel);


        password.setBounds(80,185,315,40);
        JLabel passwordLabel=new JLabel(new ImageIcon(getClass().getResource("/image/login/密码.png")));
        passwordLabel.setBounds(30,195,32,16);
        passwordShow.addMouseListener(this);
        passwordShow.setBounds(400,190,18,29);
        passwordHidden.addMouseListener(this);
        passwordHidden.setBounds(400,190,18,29);
        passwordHidden.setVisible(false);
        this.add(password);
        this.add(passwordLabel);
        this.add(passwordShow);
        this.add(passwordHidden);
        //登录 注册
        login.addMouseListener(this);
        login.setBounds(80,280,128,47);
        this.add(login);
        res.addMouseListener(this);
        res.setBounds(250,280,128,47);
        this.add(res);
        //添加背景
        JLabel background=new JLabel( new ImageIcon(getClass().getResource("/image/login/background.png")));
        background.setBounds(5,10,470,390);
        this.add(background);
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
        }

    }

    // 修改后的loginJFrame类中的mousePressed方法
    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == login) {
            try {
                login.setIcon(new ImageIcon(getClass().getResource("/image/login/登录按下.png")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //登录逻辑------------------------------------------------------------
            String userName = user.getText();
            String userPassword= new String(password.getPassword());
            Account a = new Account(userName,userPassword);
            boolean flag=a.checkAccount(a,this);

             // 传递当前JFrame作为父组件
            if (flag) {
                loginJFrame.this.setVisible(false);
                new GameJFrame(a);
            }else{
                JOptionPane.showMessageDialog(this, "密码错误！", "警告", JOptionPane.WARNING_MESSAGE);
            }
            System.out.println("登录按下");
        } else if (obj == res) {
            try {
                //跳转到注册页面
                res.setIcon(new ImageIcon(getClass().getResource("/image/login/注册按下.png")));
                this.setVisible(false);
                new registerJFrame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //控制云运行提示
            System.out.println("注册按下");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj=e.getSource();
        if (obj==login) {
            login.setIcon(new ImageIcon(getClass().getResource("/image/login/登录按钮.png")));
            System.out.println("登录恢复");
        } else if (obj == res) {
            res.setIcon(new ImageIcon(getClass().getResource("/image/login/注册按钮.png")));
            System.out.println("注册恢复");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
