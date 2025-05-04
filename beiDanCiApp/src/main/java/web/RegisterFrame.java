package web;

import pojo.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    UserService userService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField verificationCodeField;
    private JButton sendCodeButton;

    public RegisterFrame() {

        userService = new UserService();
        setTitle("用户注册");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        // 创建标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 240, 240));
        JLabel titleLabel = new JLabel("用户注册");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 用户名标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(usernameField, gbc);

        // 邮箱标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel emailLabel = new JLabel("邮箱:");
        emailLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel emailPanel = new JPanel(new BorderLayout(5, 0));
        emailPanel.setBackground(new Color(240, 240, 240));
        emailField = new JTextField(20);
        emailField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(200, 30));
        emailPanel.add(emailField, BorderLayout.CENTER);

        sendCodeButton = new JButton("发送验证码");
        styleButton(sendCodeButton);
        sendCodeButton.setPreferredSize(new Dimension(100, 30));
        emailPanel.add(sendCodeButton, BorderLayout.EAST);
        formPanel.add(emailPanel, gbc);

        // 验证码标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel verificationCodeLabel = new JLabel("验证码:");
        verificationCodeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        formPanel.add(verificationCodeLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        verificationCodeField = new JTextField(20);
        verificationCodeField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        verificationCodeField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(verificationCodeField, gbc);

        // 密码标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton registerButton = new JButton("注册");
        styleButton(registerButton);
        buttonPanel.add(registerButton);

        JButton backButton = new JButton("返回登录");
        styleButton(backButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 添加事件监听器
        sendCodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                User user = new User(email);
                userService.setUser(user);
                try {
                    if (userService.sendQQEmail()) {
                        JOptionPane.showMessageDialog(null, "验证码已发送到您的邮箱");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "发送失败，请输入邮箱地址");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // TODO: 添加发送验证码的逻辑
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 可以添加保存注册信息的逻辑
                User user = new User(emailField.getText(), new String(passwordField.getPassword()), usernameField.getText(), verificationCodeField.getText());
                userService.setUser(user);
                try {
                    if(userService.register()) JOptionPane.showMessageDialog(null, "注册成功！");
                    else System.out.println("注册失败！！！");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBackground(new Color(66, 139, 202));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegisterFrame().setVisible(true);
            }
        });
    }
}
