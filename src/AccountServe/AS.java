package AccountServe;
import javax.swing.*;
import java.util.*;
import java.sql.*;
public class AS implements AccountServe {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/javaData";//数据库本地连接地址
    private static final String DB_USER = "root";//访问用户名
    private static final String DB_PASSWORD = "Yc104121.";//访问用户密码
    Map<String,String> users = loadAccountsFromMySql();
    //保存新用户到数据库
    @Override
    public void saveAccount(Account a) {
        String sql = "INSERT INTO accounts (userName, password) VALUES (?, ?)";//mysql语句
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, a.getUserName());//插入用户名
            pstmt.setString(2, a.getPassword());//插入密码
            pstmt.executeUpdate();
            System.out.println("New record created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while inserting data");
        }
    }
    //账户校验
    @Override
    public boolean checkAccount(Account a, JFrame parent) {
        if(!users.containsKey(a.getUserName())) {
            JOptionPane.showMessageDialog(parent, "用户不存在！", "警告", JOptionPane.WARNING_MESSAGE);
        } else if (users.get(a.getUserName()).equals(a.getPassword())) {
            JOptionPane.showMessageDialog(parent, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    //检查账号是否存在
    @Override
    public boolean exist(Account a, JFrame parent) {
        return users.containsKey(a.getUserName());
    }

    @Override
    public void alterAccount(Account a) {

    }



    //加载用户数据到HashMap方便查找
    private HashMap<String,String> loadAccountsFromMySql() {
        HashMap<String,String> resMap=new HashMap<>();
        String sql ="SELECT * FROM accounts";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String username = rs.getString("userName");
                String pwd = rs.getString("password");
                resMap.put(username, pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resMap;
    }
    //获取用户的游戏数据
    public static ArrayList<Integer> loadGameDataFromMySql(Account a) {
        ArrayList<Integer> res=new ArrayList<>();
        String sql ="SELECT avgStep,count FROM accounts while userName = ?";
        try(Connection conn =DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,a.getUserName());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int avgCount=rs.getInt("avgCount");
                int count=rs.getInt("count");
                res.add(avgCount);
                res.add(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
