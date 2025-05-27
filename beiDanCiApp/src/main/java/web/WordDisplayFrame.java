package web;

import pojo.Word;
import service.WordService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class WordDisplayFrame extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel meaningLabel;
    private JLabel exampleLabel;
    private JLabel imageLabel;
    private JLabel wordLabel;
    private JLabel phoneticLabel;
    private JPanel buttonPanel;
    private JButton nextButton;
    private ArrayList<Word> words;
    private ArrayList<Word> shuffledWords;
    private int currentIndex;

    public WordDisplayFrame(CardLayout cardLayout, JPanel mainPanel) throws SQLException {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        initUI();
    }

    private void initUI() throws SQLException {
        WordService wordService = new WordService();
        words = wordService.initWordbyWordbook();
        shuffledWords = new ArrayList<>(words);
        Collections.shuffle(shuffledWords);
        currentIndex = 0;

        // 主面板设置
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 返回按钮
        JButton backButton = new JButton("← 返回");
        backButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        backButton.setContentAreaFilled(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 250));
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // 单词内容面板 - 使用GridBagLayout实现精确布局
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(245, 245, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);

        // 英文单词
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 40));
        contentPanel.add(wordLabel, gbc);

        // 音标
        phoneticLabel = new JLabel("", SwingConstants.CENTER);
        phoneticLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        phoneticLabel.setForeground(new Color(100, 100, 100));
        contentPanel.add(phoneticLabel, gbc);

        // 图片标签
        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(250, 250));
        gbc.insets = new Insets(20, 0, 20, 0);
        contentPanel.add(imageLabel, gbc);

        // 例句
        exampleLabel = new JLabel("", SwingConstants.CENTER);
        exampleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        exampleLabel.setForeground(new Color(80, 80, 80));
        exampleLabel.setMaximumSize(new Dimension(600, 60)); // 限制宽度防止换行
        gbc.insets = new Insets(10, 0, 10, 0);
        contentPanel.add(exampleLabel, gbc);

        // 中文释义（初始隐藏）
        meaningLabel = new JLabel("", SwingConstants.CENTER);
        meaningLabel.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        meaningLabel.setForeground(new Color(30, 144, 255));
        meaningLabel.setVisible(false);
        contentPanel.add(meaningLabel, gbc);

        // 使用固定大小的面板包装内容，防止滚动
        JPanel fixedPanel = new JPanel();
        fixedPanel.setPreferredSize(new Dimension(650, 550));
        fixedPanel.setBackground(new Color(245, 245, 250));
        fixedPanel.add(contentPanel);
        add(fixedPanel, BorderLayout.CENTER);

        // 底部按钮面板
        buttonPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));

        // 创建按钮
        JButton familiarButton = createActionButton("熟悉", new Color(50, 205, 50));
        JButton unfamiliarButton = createActionButton("不熟悉", new Color(255, 140, 0));
        JButton unknownButton = createActionButton("不知道", new Color(220, 20, 60));

        // 下一个按钮
        nextButton = createActionButton("下一个单词 →", new Color(70, 130, 180));
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> {
            meaningLabel.setVisible(false);
            imageLabel.setVisible(false);
            nextButton.setVisible(false);
            familiarButton.setVisible(true);
            unfamiliarButton.setVisible(true);
            unknownButton.setVisible(true);
            showNextWord();
        });

        // 按钮事件
        ActionListener buttonListener = e -> {
            meaningLabel.setVisible(true);
            imageLabel.setVisible(true);
            nextButton.setVisible(true);
            familiarButton.setVisible(false);
            unfamiliarButton.setVisible(false);
            unknownButton.setVisible(false);
        };

        familiarButton.addActionListener(buttonListener);
        unfamiliarButton.addActionListener(buttonListener);
        unknownButton.addActionListener(buttonListener);

        buttonPanel.add(familiarButton);
        buttonPanel.add(unfamiliarButton);
        buttonPanel.add(unknownButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 显示第一个单词
        showCurrentWord();
    }

    private void showCurrentWord() {
        if (currentIndex < shuffledWords.size()) {
            Word currentWord = shuffledWords.get(currentIndex);
            wordLabel.setText(currentWord.getWord());
            phoneticLabel.setText(currentWord.getPhonetic());
            exampleLabel.setText("<html><div style='text-align:center;width:600px;'>" +
                    "Example: " + currentWord.getExample() + "</div></html>");
            meaningLabel.setText(currentWord.getMeaning());
            loadImage(currentWord.getWord());
        } else {
            wordLabel.setText("已完成所有单词");
            phoneticLabel.setText("");
            exampleLabel.setText("");
            meaningLabel.setText("");
            imageLabel.setIcon(null);
            nextButton.setEnabled(false);
        }
    }

    private void showNextWord() {
        currentIndex++;
        showCurrentWord();
    }

    private void loadImage(String word) {
        try {
            URL imageUrl = getClass().getResource("/images/" + word + ".png");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageUrl));
                // 保持图片比例缩放
                int width = 250, height = 250;
                if (icon.getIconWidth() > icon.getIconHeight()) {
                    height = (int)(width * (double)icon.getIconHeight() / icon.getIconWidth());
                } else {
                    width = (int)(height * (double)icon.getIconWidth() / icon.getIconHeight());
                }
                Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("");
            }
        } catch (Exception e) {
            imageLabel.setIcon(null);
            imageLabel.setText("");
        }
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}