package web;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFrame extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel timeLabel;
    private JLabel quoteLabel;
    private String[] motivationalQuotes = {
            "学习如逆水行舟，不进则退！",
            "每天背10个单词，一年就是3650个！",
            "坚持是成功的关键！",
            "知识就是力量！",
            "今天的努力是明天的收获！"
    };

    public HomeFrame(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        initUI();
        startTimeUpdate();
        quoteLabel.setText("今日箴言: " + getRandomQuote());
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 250));

        // 顶部欢迎语
        JLabel welcomeLabel = new JLabel("欢迎使用单词记忆系统", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(70, 130, 180));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // 中间内容面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 245, 250));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20));

        // 时间显示面板
        JPanel timePanel = new JPanel(new GridLayout(2, 1));
        timePanel.setBackground(new Color(240, 245, 250));

        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        timeLabel.setForeground(new Color(50, 50, 50));

        quoteLabel = new JLabel("", SwingConstants.CENTER);
        quoteLabel.setFont(new Font("楷体", Font.ITALIC, 18));
        quoteLabel.setForeground(new Color(70, 130, 180));

        timePanel.add(timeLabel);
        timePanel.add(quoteLabel);
        centerPanel.add(timePanel, BorderLayout.NORTH);

        // 按钮面板 - 使用3列布局
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 30));

        // 开始背单词按钮
        JButton startButton = createStyledButton("Start Learning", new Color(100, 149, 237));
        startButton.addActionListener(e -> {
            WordDisplayFrame wordDisplayFrame = null;
            try {
                wordDisplayFrame = new WordDisplayFrame(cardLayout, mainPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            mainPanel.add(wordDisplayFrame, "WordDisplay");
            cardLayout.show(mainPanel, "WordDisplay");
        });

        // 复习单词按钮
        JButton reviewButton = createStyledButton("Review", new Color(255, 165, 0)); // 橙色
        reviewButton.addActionListener(e -> {
            // TODO: 实现复习单词功能
            WordDisplayFrame wordDisplayFrame = null;
            try {
                wordDisplayFrame = new WordDisplayFrame(cardLayout, mainPanel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            mainPanel.add(wordDisplayFrame, "WordDisplay");
            cardLayout.show(mainPanel, "WordDisplay");
        });

        // 选择词书按钮
        JButton wordbookButton = createStyledButton("Select Books", new Color(60, 179, 113));
        wordbookButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Wordbook");
        });

        buttonPanel.add(startButton);
        buttonPanel.add(reviewButton);
        buttonPanel.add(wordbookButton);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton();

        // 使用HTML实现自动换行和居中
        button.setText("<html><div style='text-align:center;padding:0 5px;'>"+text+"</div></html>");

        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        // 更紧凑的边框设置
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        // 设置固定大小（根据文本长度调整）
        int width = text.length() <= 4 ? 100 : 120;
        button.setPreferredSize(new Dimension(width, 50));

        // 鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void startTimeUpdate() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                String currentTime = dateFormat.format(new Date());
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText("当前时间: " + currentTime);
                });
            }
        }, 0, 1000);
    }

    private String getRandomQuote() {
        int index = (int)(Math.random() * motivationalQuotes.length);
        return motivationalQuotes[index];
    }
}