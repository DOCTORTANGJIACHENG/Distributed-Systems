package service;

import dao.UserDao;
import dao.sendQQ_Email;
import pojo.User;

import java.io.IOException;
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
        return user.getPassWord().equals(loginPassword);

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

}
