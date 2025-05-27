package dao;
import util.DruidUtils;

import java.io.IOException;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class sendQQ_Email {
    // 生成一个六位数字，范围在 100000 到 999999 之间
    String email;

    public sendQQ_Email(String email) {
        this.email = email;
    }




    public int sendQQEmail() throws IOException {
        // 设置邮件服务器属性
        Properties properties = new Properties();
        properties.load(sendQQ_Email.class.getClassLoader().getResourceAsStream("email.properties"));
//        properties.put("mail.smtp.host", "smtp.qq.com");  // QQ 邮箱 SMTP 服务器地址
//        properties.put("mail.smtp.port", "465");  // QQ 邮箱使用的 SSL 端口是 465
//        properties.put("mail.smtp.auth", "true");  // 启用身份验证
//        properties.put("mail.smtp.socketFactory.port", "465");  // 使用 465 端口
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  // 使用 SSL 连接
//        properties.put("mail.smtp.socketFactory.fallback", "false");  // 禁用回退


        // 创建会话
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("270307701@qq.com", "dkeagwtnxhkxbhde"); // 使用 QQ 邮箱及应用专用密码
            }
        });

        int emailPassword = 0;
        try {
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("270307701@qq.com")); // 发件人地址
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("3307416035@qq.com")); // 收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.email)); // 收件人地址
            message.setSubject("欢迎注册背单词软件账号！！！");
            Random random = new Random();
            emailPassword = 100000 + random.nextInt(900000);
            message.setText("你的验证码是："+emailPassword);

            // 发送邮件
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            emailPassword = -1;
        }
        return emailPassword;
    }
}
