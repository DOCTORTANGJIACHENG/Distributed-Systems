package service;

import dao.UserDao;
import dao.sendQQ_Email;
import pojo.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    User user;
    int sendEmailPassword;
    public UserService() {
    }

    public UserService(User user) {
        if(user != null)
        {
            this.user = user;
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean login() throws SQLException {
        UserDao userDao = new UserDao(user.getE_Mail(), user.getPassWord());
        String loginPassword = userDao.login();
        if(user.getPassWord().equals(loginPassword)){
//            if (userDao.setLastLoginTime()>0){
//                System.out.println("更新登录时间成功！");
//            }
//            else System.out.println("更新登录时间失败！");

            return true;
        }
        else return false;
    }

    public boolean setSelectedWordbookByDoubleClick(int wordBookId) throws SQLException {

        UserDao userDao = new UserDao();
        int i = userDao.setSelectedWordbook(wordBookId);
        return i > 0;
    }


    public boolean sendQQEmail() throws IOException {
        sendQQ_Email sendQQEmail = new sendQQ_Email(user.getE_Mail());
        int emailPassword = sendQQEmail.sendQQEmail();
        if(emailPassword>0){
            this.sendEmailPassword = emailPassword;
        }
        return emailPassword > 0;
    }

    public boolean register() throws SQLException {
        UserDao userDao = new UserDao(user.getE_Mail(),user.getPassWord(),user.getUserName());
        if(userDao.isUserExist())
        {
            System.out.println("用户已存在！！！");
            return false;
        }
        else {
            if(Integer.parseInt(user.getEmailPassword())==this.sendEmailPassword)
            {
                int i = userDao.insertUser();
                return i>0;
            }
            else {
                System.out.println("验证码错误");
                return false;

            }
        }
    }


    public User getUserInfo() throws SQLException {
        UserDao userDao = new UserDao();
        User user = new User();
        ResultSet resultSet = userDao.queryUserInfo();

        while (resultSet.next()){
            user.setUserName(resultSet.getString("username"));
            user.setPassWord(resultSet.getString("password"));
            user.setE_Mail(resultSet.getString("email"));
            user.setCreate_time(resultSet.getString("create_time"));
            user.setLast_login(resultSet.getString("last_login"));
            user.setLevel(resultSet.getInt("level"));
            user.setSelect_wordbook(resultSet.getString("name"));
        }
        return user;
    }


    public boolean setUserName(String userName) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.updateUsername(userName) > 0;
    }

    public Boolean setNewPassword(String password) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.updateUserPassword(password) > 0;
    }

    public boolean deleteUser() throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.deleteUser() > 0;
    }
}
