package dao;

import pojo.User;
import util.DruidUtils;


import java.sql.*;
import java.util.List;


public class UserDao{
    String email;
    String password;
    String userName;

    public UserDao(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public UserDao(String email, String password) {
        this.email = email;
        this.password = password;
    }


    //登录
    public String login() throws SQLException {
        String email = "";
        String password = "";
        String sql = "select email, password  from user where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,this.email);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            email = rs.getString("email");
            System.out.println(email);
            password = rs.getString("password");
            System.out.println(password);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return password;
//        if(Objects.equals(this.password, password)) {
//            return true;
//        }else {
//            System.out.println("密码错误");
//            System.out.println(this.password);
//            return false;
//        }
    }

    //判断是否存在
    public boolean isUserExist() throws SQLException {
        String sql = "select password, email from user where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,this.email);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            rs.close();
            pstmt.close();
            conn.close();
            return true;
        }
        else return false;
    }

    public int insertUser() throws SQLException {

        String sql = "insert into user (username, password,email) values(?,?,?)";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);

        pstmt.setString(1,this.userName);
        pstmt.setString(2,this.password);
        pstmt.setString(3,this.email);

        int i = pstmt.executeUpdate();

        return i;
    }

    //注销用户
    public int delUser(User user){
        return 0;
    }

    //查询所有用户
    public List<User> findAllUser(){
        return null;
    }

}
