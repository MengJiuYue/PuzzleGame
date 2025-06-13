package AccountServe;
import javax.swing.*;
import java.util.*;
import java.sql.*;

public class AS implements AccountServe {
    private static final String DB_URL = "jdbc:h2:./data/game"; // 文件形式
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    Map<String,String> users = loadAccountsFromMySql();

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS accounts (
                    userName VARCHAR(255) PRIMARY KEY,
                    password VARCHAR(255),
                    avgStep INT DEFAULT 0,
                    count INT DEFAULT 0
                );
                """;
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //保存新用户到数据库
    @Override
    public boolean saveAccount(Account a) {
        String sql = "INSERT INTO accounts  VALUES (?, ?,0,0)";//mysql语句
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, a.getUserName());//插入用户名
            pstmt.setString(2, a.getPassword());//插入密码
            pstmt.executeUpdate();
            System.out.println("New record created successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while inserting data");
            return false;
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
        initDatabase();
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
