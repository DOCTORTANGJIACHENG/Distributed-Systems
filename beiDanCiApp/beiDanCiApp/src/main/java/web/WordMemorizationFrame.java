package web;

import pojo.Word;
import service.WordService;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class WordMemorizationFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final int WINDOW_WIDTH = 757;
    private static final int WINDOW_HEIGHT = 903;

    public WordMemorizationFrame() throws SQLException {
        // 设置窗口基本属性
        setTitle("Word Memorization App");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false); // 禁止调整窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中

        // 主面板使用 CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // 顶部标题栏
        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 50));
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Word Memorization App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // 创建各个功能面板
        JPanel homePanel = new HomeFrame(cardLayout, mainPanel);
        JPanel askAIPanel = createAIPanel();
        JPanel myPanel = createMyPanel();
        JPanel wordbookPanel = createWordbookPanel();

        // 将不同的面板添加到 mainPanel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(askAIPanel, "Ask AI");
        mainPanel.add(myPanel, "My");
        mainPanel.add(wordbookPanel, "Wordbook");

        // 底部导航栏
        JPanel navPanel = createNavigationPanel();
        navPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 60));

        // 创建主框架内容面板
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // 添加 headerPanel 到 contentPanel 上部
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // 添加 mainPanel（包括不同页面）到 contentPanel 中部
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        // 添加底部导航栏到 contentPanel 底部
        contentPanel.add(navPanel, BorderLayout.SOUTH);

        // 设置窗口的内容面板
        setContentPane(contentPanel);
        pack(); // 确保窗口尺寸准确
    }

    private JPanel createAIPanel() {
        JPanel aiPanel = new JPanel(new BorderLayout());
        aiPanel.add(new AskAIFrame(), BorderLayout.CENTER);
        return aiPanel;
    }

    private JPanel createMyPanel() throws SQLException {
        JPanel myPanel = new JPanel(new BorderLayout());
        myPanel.add(new MyFrame(), BorderLayout.CENTER);
        return myPanel;
    }

    private JPanel createWordbookPanel() throws SQLException {
        JPanel wordbookPanel = new JPanel(new BorderLayout());
        WordbookFrame wordbookFrame = new WordbookFrame(cardLayout, mainPanel);
        wordbookPanel.add(wordbookFrame, BorderLayout.CENTER);

        wordbookFrame.setWordbookListener(wordbook -> {
            WordService wordService = new WordService();
            List<Word> words = wordService.getWordsByWordbook(wordbook);

            // 创建单词列表界面
            JPanel wordListPanel = new WordListFrame(() -> {
                cardLayout.show(mainPanel, "Wordbook");
            }, wordbook.getName(), words);

            mainPanel.add(wordListPanel, "WordList");
            cardLayout.show(mainPanel, "WordList");
        });

        return wordbookPanel;
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 4, 0, 0));
        navPanel.setBackground(new Color(240, 240, 240));
        navPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));

        // 创建导航按钮
        JButton homeButton = createNavButton("Home", "Home");
        JButton askAIBtn = createNavButton("Ask AI", "Ask AI");
        JButton myButton = createNavButton("My", "My");
        JButton wordbookButton = createNavButton("Wordbook", "Wordbook");

        navPanel.add(homeButton);
        navPanel.add(askAIBtn);
        navPanel.add(myButton);
        navPanel.add(wordbookButton);

        return navPanel;
    }

    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(new Color(70, 70, 70));
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setFocusPainted(false);

        // 添加悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });

        // 添加点击事件
        button.addActionListener(e -> cardLayout.show(mainPanel, cardName));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                WordMemorizationFrame frame = new WordMemorizationFrame();
                frame.setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}