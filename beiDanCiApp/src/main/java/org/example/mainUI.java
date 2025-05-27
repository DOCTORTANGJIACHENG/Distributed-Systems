package org.example;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainUI extends JFrame {

    // 构造器
    public mainUI() {
        // 设置窗口基本属性
        setTitle("Word Memorization App");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中

        // 主面板使用 CardLayout
        JPanel mainPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // 顶部标题栏
        JPanel headerPanel = new JPanel();
        headerPanel.add(new JLabel("Word Memorization App"));

        // 中间内容面板
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Home Page!"));

        JPanel aiPanel = new JPanel();
        aiPanel.add(new JLabel("Ask AI Page"));

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("My Page"));

        JPanel wordbookPanel = new JPanel();
        wordbookPanel.add(new JLabel("Wordbook Page"));

        // 将不同的面板添加到 mainPanel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(aiPanel, "Ask AI");
        mainPanel.add(myPanel, "My");
        mainPanel.add(wordbookPanel, "Wordbook");

        // 底部导航栏
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 4)); // 4个按钮平分横向布局

        JButton homeButton = new JButton("Home");
        JButton askAIBtn = new JButton("Ask AI");
        JButton myButton = new JButton("My");
        JButton wordbookButton = new JButton("Wordbook");

        navPanel.add(homeButton);
        navPanel.add(askAIBtn);
        navPanel.add(myButton);
        navPanel.add(wordbookButton);

        // 事件监听器，点击按钮切换卡片
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // 显示首页面板
            }
        });

        askAIBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Ask AI"); // 显示问AI页面
            }
        });

        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "My"); // 显示我的页面
            }
        });

        wordbookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Wordbook"); // 显示词书页面
            }
        });

        // 创建主框架内容面板
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // 添加 headerPanel 到 contentPanel 上部
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // 添加 mainPanel（包括不同页面）到 contentPanel 中部
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        // 添加底部导航栏到 contentPanel 底部
        contentPanel.add(navPanel, BorderLayout.SOUTH);

        // 设置窗口的内容面板
        setContentPane(contentPanel);
    }

    @Test
    // 主方法，启动应用程序
    public void test(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new mainUI().setVisible(true); // 创建并显示窗口
            }
        });
    }
}
