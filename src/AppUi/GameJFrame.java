package AppUi;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import AccountServe.Account;
import Log.LS;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    final static String DB_URL="jdbc:mysql://localhost:3306/javaData";
    final static String DB_USER="root";
    final static String DB_PASSWORD="Yc104121.";

    private final static String  ALL_PATH="all.jpg";
    private final static String  WIN_PATH="image/win.png";

     int x;
     int y;
    int[][] imgIndex = initializeImgIndex();
    int step=0;
    static int [] gameData = {1,1};

    String kind="animal";
    private static String PATH="image/animal/animal3/";
    Account nowAccount;

    JMenuItem replay=new JMenuItem("重新游戏");
    JMenuItem relogin=new JMenuItem("重新登录");
    JMenuItem closeGame=new JMenuItem("关闭游戏");
    JMenu about=new JMenu("关于我们");
    JMenuItem aboutWe=new JMenuItem("关于我们");
    JMenu change=new JMenu("更换图片");
    JMenuItem girl=new JMenuItem("美女");
    JMenuItem animal=new JMenuItem("动物");
    JMenuItem sport=new JMenuItem("运动");


    public GameJFrame(Account a){
        nowAccount=a;

        setJFrame();

        setBar();

        loadGameData(gameData,nowAccount);
        initializeGameData();

        System.out.println("当前账户"+nowAccount.getUserName()+" "+nowAccount.getPassword());

        //显示界面`
        this.setVisible(true);
    }

    private void initializeGameData() {
        this.getContentPane().removeAll();
        //加载图片
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                JLabel label=new JLabel(new ImageIcon(STR."\{PATH}\{imgIndex[i][j]}.jpg"));
                label.setBounds(105*j+83,105*i+134,105,105);
                label.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.add(label);
            }
        }
        //添加背景
        JLabel background=new JLabel( new ImageIcon("image/background.png"));
        background.setBounds(40,40,500,560);
        this.add(background);
        //玩家数据
        JLabel stepCount=new JLabel(STR."步数:\{step}");
        stepCount.setBounds(50,30,50,20);
        this.add(stepCount);

        /* mysql游戏数据 */
        JLabel avgStep=new JLabel(STR."每局平均步数:\{gameData[0]}");
        avgStep.setBounds(380,30,100,20);
        this.add(avgStep);

        JLabel count=new JLabel(STR."完成局数:\{gameData[1]}");
        count.setBounds(100,30,100,20);
        this.add(count);

        JLabel stepRank=new JLabel(STR."平均步数排名:\{getRank()}");
        stepRank.setBounds(480,30,100,20);
        this.add(stepRank);

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
        about.add(aboutWe);

        bar.add(function);
        bar.add(about);

        this.setJMenuBar(bar);

        replay.addActionListener(this);
        relogin.addActionListener(this);
        closeGame.addActionListener(this);
        aboutWe.addActionListener(this);
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
                    initializeGameData();
                    System.out.println("左");
                }

            }
            break;
            case 38://上
            {
                if(x+1<4){
                    imgIndex[x][y]=imgIndex[++x][y];
                    imgIndex[x][y]=0;
                    initializeGameData();
                    System.out.println("上");
                }
            }
            break;
            case 39://右
            {
                if(y-1>=0){
                    imgIndex[x][y]=imgIndex[x][--y];
                    imgIndex[x][y]=0;
                    initializeGameData();
                    System.out.println("右");
                }
            }
            break;
            case 40://下
            {
                if(x-1>=0){
                    imgIndex[x][y]=imgIndex[--x][y];
                    imgIndex[x][y]=0;
                    initializeGameData();
                    System.out.println("下");
                }
            }
            break;
            default:
                break;
        }
        if(select>=37&&select<=40) step++;//步数计数
        if(checkWin(imgIndex)) {
            //显示胜利图片
            showNewImg(false,WIN_PATH,203,283,197,73);
        }
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
        if(e.getKeyCode()==65) initializeGameData();
        if (e.getKeyCode()==87){
            //非开发用户无权限一键胜利
            if("test".equals(nowAccount.getUserName())){
                int num=1;
                for (int i = 0; i < 4; i++) {
                    for(int j=0;j<4;j++){
                        if(num<16) imgIndex[i][j]=num++;
                        else imgIndex[i][j]=0;
                    }
                }
                x=3;y=3;
                initializeGameData();
            }else{
                JOptionPane.showMessageDialog(this, "非开发人员无操作权限！", "警告", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object item=e.getSource();
        if(item==replay){//重新游戏
            upGameData(step);
            step=0;
            imgIndex = initializeImgIndex();
            loadGameData(gameData,nowAccount);
            initializeGameData();
        }else if(item==relogin){
            this.setVisible(false);
            loadGameData(gameData,nowAccount);
            initializeGameData();
            new loginJFrame();
        }else if(item==closeGame){
            upGameData(step);
            System.exit(0);
        }else if(item==aboutWe){
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
        PATH= STR."image/\{kind}/\{kind}\{Index}/";
        step=0;
        imgIndex = initializeImgIndex();
        loadGameData(gameData,nowAccount);
        initializeGameData();
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

    private  void loadGameData(int []gameData,Account nowAccount){
        try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            String sql = "SELECT *FROM accounts WHERE userName = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,nowAccount.getUserName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                gameData[0]=rs.getInt("avgStep");//avgStep
                gameData[1]=rs.getInt("count");//count
                System.out.println(gameData[0]+" "+gameData[1]);
            }else {
                LS.saveLog("游戏数据查询异常,异常账户"+nowAccount.getUserName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void upGameData(int step){
        String sql="UPDATE accounts SET avgStep= ?,count= ? WHERE userName = ?";
        int avgStep,count;
        count=gameData[1]+1;
        avgStep=(gameData[0]*gameData[1]+step)/count;

        try(Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,avgStep);
            stmt.setInt(2,count);
            stmt.setString(3,nowAccount.getUserName());
            stmt.executeUpdate();
            System.out.println("数据正常更新");
    }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private int getRank(){
        String sql= """
                select
                    accounts.userName,
                    rank() over (
                    order by accounts.avgStep
                    ) as 'rank'
                from accounts;""";
        try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                if(rs.getString("userName").equals(nowAccount.getUserName())){
                    return rs.getInt("rank");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}