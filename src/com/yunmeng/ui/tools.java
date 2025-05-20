package com.yunmeng.ui;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class tools {
    private static final String DATA_FILE_PATH="src/data.text";
    ArrayList<account> accounts=new ArrayList<>();
//查找用户索引,没有返回-1
 private int findUser(String user){
     if(accounts.isEmpty()) return -1;
     for (int i = 0; i < accounts.size(); i++) {
         if (accounts.get(i).getUser().equals(user)) return i;
     }
     return -1;
 }
//添加账户
public boolean addAccount(JFrame parent,String user,String password){
     if (findUser(user)==-1){
         account a=new account(user,password);
         accounts.add(a);
         JOptionPane.showMessageDialog(parent, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
         saveData(user,password);
         return true;
     }else{
         JOptionPane.showMessageDialog(parent, "用户已经注册！", "警告", JOptionPane.WARNING_MESSAGE);
         return false;
     }
}
//检查账户返回登录情况
// 修改后的tools类中的checkAccount方法
    public boolean checkAccount(JFrame parent, String user, char[] passwordData) {
        int userIndex = findUser(user);
        String password = new String(passwordData);
        if (userIndex == -1) {
            JOptionPane.showMessageDialog(parent, "用户不存在！", "警告", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            if (accounts.get(userIndex).getPassword().equals(password)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(parent, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    private void saveData(String user,String password){
     try(BufferedWriter bw=new BufferedWriter(new FileWriter(DATA_FILE_PATH,true))) {
         bw.write(user+","+password);
         bw.newLine();
         bw.flush();
     } catch (Exception e) {
         e.printStackTrace();
     }
    }

    public void loadData(){
        try(BufferedReader br=new BufferedReader(new FileReader(DATA_FILE_PATH))) {
            String line;
            while((line=br.readLine())!=null){
                String [] userData=line.split(",");
                accounts.add(new account(userData[0],userData[1]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
