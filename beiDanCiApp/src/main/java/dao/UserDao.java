package dao;

import pojo.User;
import pojo.wordBook;
import util.DruidUtils;


import java.sql.*;
import java.util.List;


public class UserDao{
    public static String  email;
    String password;
    String userName;

    public UserDao(String email, String password, String userName) {
        UserDao.email = email;
        this.password = password;
        this.userName = userName;
    }

    public UserDao(String email, String password) {
        UserDao.email = email;
        this.password = password;
    }

    public UserDao() {

    }

    public ResultSet queryUserInfo() throws SQLException {
        String sql = "select username, password, email, create_time, last_login, level, name  from user inner join word_book on user.select_wordbook = word_book.id where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        if (rs!=null){
            return rs;
        }
        else {
            System.out.println("查询用户信息失败！");
            return null;
        }
    }

    //登录: 查询数据库返回密码。
    public String login() throws SQLException {
        String email = "";
        String password = "";
        String sql = "select email, password  from user where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, UserDao.email);
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

    }

    public int setLastLoginTime() throws SQLException {
        String sql = "update user set last_login = now() where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);

        pstmt.setString(1,email);

        return pstmt.executeUpdate();
    }
    //判断是否存在
    public boolean isUserExist() throws SQLException {
        String sql = "select password, email from user where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            rs.close();
            pstmt.close();
            conn.close();
            return true;
        }
        else return false;
    }


    public int setSelectedWordbook(int wordBookId) throws SQLException {
        String sql = "update user set select_wordbook = ? where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(wordBookId));
        pstmt.setString(2,email);

        return pstmt.executeUpdate();
    }
    public int insertUser() throws SQLException {

        String sql = "insert into user (username, password,email) values(?,?,?)";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);

        pstmt.setString(1,this.userName);
        pstmt.setString(2,this.password);
        pstmt.setString(3, email);

        int i = pstmt.executeUpdate();

        return i;
    }

    //注销用户
    public int delUser(User user){
        return 0;
    }


    public int updateUsername(String userName) throws SQLException {
        String sql = "update user set username = ? where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userName);
        pstmt.setString(2,email);

        return pstmt.executeUpdate();
    }

    public int updateUserPassword(String password) throws SQLException {
        String sql = "update user set password = ? where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setString(2,email);

        return pstmt.executeUpdate();
    }

    public int deleteUser() throws SQLException {
        String sql = "delete from user where email = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,email);

        return pstmt.executeUpdate();
    }
}
