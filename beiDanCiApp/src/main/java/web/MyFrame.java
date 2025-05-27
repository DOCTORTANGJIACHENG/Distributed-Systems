package web;

import dao.UserDao;
import pojo.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.border.*;




public class MyFrame extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JTextField emailField;
    private JTextField levelField;
    private JTextField bookField;
    private JTextField createdField;
    private JTextField lastLoginField;

    public MyFrame() throws SQLException {
        UserService userService = new UserService();
        User userInfo = userService.getUserInfo();

        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 252));

        // 标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel titleLabel = new JLabel("个人信息", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // 信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        infoPanel.setBackground(getBackground());

        // 添加各个信息行
        infoPanel.add(createFieldRow("用户名:", usernameField = createField(userInfo.getUserName())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoPanel.add(createPasswordRow("密码:", passwordField = new JPasswordField(userInfo.getPassWord())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoPanel.add(createFieldRow("邮箱号:", emailField = createField(userInfo.getE_Mail())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoPanel.add(createFieldRow("用户等级:", levelField = createField(String.valueOf(userInfo.getLevel()))));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoPanel.add(createFieldRow("词书选择:", bookField = createField(userInfo.getSelect_wordbook())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoPanel.add(createFieldRow("创号时间:", createdField = createField(userInfo.getCreate_time())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoPanel.add(createFieldRow("最后登录:", lastLoginField = createField(userInfo.getLast_login())));

        add(infoPanel, BorderLayout.CENTER);

        // 按钮面板 - 使用GridLayout确保按钮均匀分布
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        buttonPanel.setBackground(getBackground());

        JButton refreshBtn = createStyledButton("刷新", new Color(65, 105, 225));
        JButton deleteUserBtn = createStyledButton("注销用户", new Color(65, 105, 225));
        JButton editUserBtn = createStyledButton("编辑用户名", new Color(65, 105, 225));
        JButton changePwdBtn = createStyledButton("修改密码", new Color(65, 105, 225));

        // 添加按钮事件
        editUserBtn.addActionListener(e -> {
            try {
                showEditUsernameDialog();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        changePwdBtn.addActionListener(e -> {
            try {
                showChangePasswordDialog();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        refreshBtn.addActionListener(e -> {
            try {
                refreshUserInfo();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        deleteUserBtn.addActionListener(e -> {
            try {
                deleteUserInfo();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 添加按钮到面板
        buttonPanel.add(refreshBtn);
        buttonPanel.add(editUserBtn);
        buttonPanel.add(changePwdBtn);
        buttonPanel.add(deleteUserBtn);

        // 使用额外的面板包装按钮面板，确保在SOUTH区域有合适的高度
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        add(southPanel, BorderLayout.SOUTH);

        UserDao userDao = new UserDao();
        if (userDao.setLastLoginTime()>0){
                System.out.println("更新登录时间成功！");
            }
            else System.out.println("更新登录时间失败！");
    }

    private void deleteUserInfo() throws SQLException {
        int option = JOptionPane.showConfirmDialog(
                this,
                "确定要注销当前用户吗？此操作不可恢复！",
                "警告",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            UserService userService = new UserService();

            if(userService.deleteUser()){
                JOptionPane.showMessageDialog(this, "用户注销成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }


            // 打开登录界面
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame(); // 假设你的登录界面类名为LoginFrame
                loginFrame.setVisible(true);
            });
        }
    }


    private JPanel createFieldRow(String labelText, JTextField field) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(getBackground());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(100, 30));

        field.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(240, 240, 245));
        field.setEditable(false);

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(field, BorderLayout.CENTER);

        return rowPanel;
    }

    private JPanel createPasswordRow(String labelText, JPasswordField passwordField) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(getBackground());

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(100, 30));

        passwordField.setEchoChar('●');
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setBackground(Color.WHITE);

        showPassword = new JCheckBox("显示密码");
        showPassword.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        showPassword.setBackground(getBackground());
        showPassword.setFocusPainted(false);
        showPassword.addItemListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected() ? (char)0 : '●');
        });

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(getBackground());
        rightPanel.add(passwordField, BorderLayout.CENTER);
        rightPanel.add(showPassword, BorderLayout.EAST);

        rowPanel.add(label, BorderLayout.WEST);
        rowPanel.add(rightPanel, BorderLayout.CENTER);
        passwordField.setEditable(false);

        return rowPanel;
    }

    private JTextField createField(String value) {
        JTextField field = new JTextField(value);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        field.setEditable(false);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(240, 240, 245));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void showEditUsernameDialog() throws SQLException {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("输入新用户名:");
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        panel.add(label, BorderLayout.NORTH);

        JTextField newUsernameField = new JTextField();
        newUsernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        newUsernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(newUsernameField, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "编辑用户名",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String newUsername = newUsernameField.getText().trim();
            if (!newUsername.isEmpty()) {
                UserService userService = new UserService();
                if(userService.setUserName(newUsername)){
                    usernameField.setText(newUsername);
                    JOptionPane.showMessageDialog(this, "用户名修改成功", "成功",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else System.out.println("用户名修改失败！");
            } else {
                JOptionPane.showMessageDialog(this, "用户名不能为空", "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshUserInfo() throws SQLException {
        UserService userService = new UserService();
        User userInfo = userService.getUserInfo();

        this.usernameField.setText(userInfo.getUserName());
        passwordField.setText(userInfo.getPassWord());
        emailField.setText(userInfo.getE_Mail());
        levelField.setText(String.valueOf(userInfo.getLevel()));
        bookField.setText(userInfo.getSelect_wordbook());
        createdField.setText(userInfo.getCreate_time());
//        lastLoginField.setText(userInfo.getLast_login());
    }

    private void showChangePasswordDialog() throws SQLException {
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 210)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

        oldPasswordField.setFont(font);
        oldPasswordField.setBorder(border);
        newPasswordField.setFont(font);
        newPasswordField.setBorder(border);
        confirmPasswordField.setFont(font);
        confirmPasswordField.setBorder(border);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("修改密码"));
        panel.add(new JLabel());
        panel.add(new JLabel("原密码:"));
        panel.add(oldPasswordField);
        panel.add(new JLabel("新密码:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("确认密码:"));
        panel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "修改密码",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String old = new String(oldPasswordField.getPassword());
            String newPass = new String(newPasswordField.getPassword());
            String confirm = new String(confirmPasswordField.getPassword());

            if (!old.equals(new String(passwordField.getPassword()))) {
                showErrorDialog("原密码错误");
            } else if (!newPass.equals(confirm)) {
                showErrorDialog("两次输入的新密码不一致");
            } else if (newPass.length() < 6) {
                showErrorDialog("密码长度不能少于6位");
            } else {

                UserService userService = new UserService();
                if(userService.setNewPassword(newPass)) {
                    passwordField.setText(newPass);
                    JOptionPane.showMessageDialog(this, "密码修改成功", "成功",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else System.out.println("修改密码失败！");

            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
    }


}