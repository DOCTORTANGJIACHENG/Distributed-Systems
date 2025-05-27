package web;

import pojo.wordBook;
import service.UserService;
import service.wordBookService;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordbookFrame extends JPanel {
    public interface WordbookListener {
        void onWordbookSelected(wordBook wordbook) throws SQLException;
    }


    private CardLayout cardLayout;
    private JPanel mainPanel;
    private WordbookListener listener;
    private JList<String> wordbookList; // 词书列表
    private JTextArea descriptionArea;  // 词书描述
    private JLabel titleLabel;         // 词书标题


    // 模拟词书数据
    private List<wordBook> wordbooks = new ArrayList<>();


    public void setWordbookListener(WordbookListener listener) {
        this.listener = listener;
    }

    // 在双击事件中触发回调
    private void onWordbookDoubleClicked(wordBook wordbook) throws SQLException {
        System.out.println("双击的词书: " + wordbook.getName());
        if (listener != null) {
            listener.onWordbookSelected(wordbook);
        }
    }
    public WordbookFrame(CardLayout cardLayout,JPanel mainPanel) throws SQLException {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        // 初始化模拟数据
        initMockData();

        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. 顶部标题
        titleLabel = new JLabel("词书库");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. 左侧词书列表（带滚动条）
        wordbookList = new JList<>(getWordbookNames());
        wordbookList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        wordbookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordbookList.setBackground(Color.WHITE);
        wordbookList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));


        // 2. 添加双击事件监听
        wordbookList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 双击
                    int index = wordbookList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        try {
                            onWordbookDoubleClicked(wordbooks.get(index));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        JScrollPane listScrollPane = new JScrollPane(wordbookList);
        listScrollPane.setPreferredSize(new Dimension(150, 400));

        // 3. 右侧词书描述区域
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(new Color(240, 240, 240));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane descScrollPane = new JScrollPane(descriptionArea);

        // 左右分栏布局
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane,
                descScrollPane
        );
        splitPane.setDividerLocation(150); // 初始分割位置
        add(splitPane, BorderLayout.CENTER);

        // 4. 底部按钮（例如：加载词书）
        JButton loadButton = new JButton("加载词书");
        loadButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        loadButton.setBackground(new Color(70, 130, 180));
        loadButton.setForeground(Color.WHITE);
        loadButton.setFocusPainted(false);
        loadButton.addActionListener(e -> {
            try {
                loadSelectedWordbook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 列表选择事件：更新描述
        wordbookList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = wordbookList.getSelectedIndex();
                if (index >= 0) {
                    descriptionArea.setText(wordbooks.get(index).getDescription());
                }
            }
        });
    }

    // 模拟数据初始化
    private void initMockData() throws SQLException {

        wordBookService wordBookService = new wordBookService();
        wordBookService.getWordBooks(wordbooks);

        for (wordBook wordbook : wordbooks) {
            System.out.println(wordbook);
        }

    }

    // 获取词书名称数组（用于JList显示）
    private String[] getWordbookNames() {
        return wordbooks.stream()
                .map(wordBook::getName)
                .toArray(String[]::new);
    }

    // 加载选中的词书
    private void loadSelectedWordbook() throws SQLException {
        int index = wordbookList.getSelectedIndex();
        if (index >= 0) {
            System.out.println(index);
            JOptionPane.showMessageDialog(this,
                    "已加载词书: " + wordbooks.get(index).getName(),
                    "提示",
                    JOptionPane.INFORMATION_MESSAGE);

            UserService userService = new UserService();
            if (userService.setSelectedWordbookByDoubleClick(wordbooks.get(index).getId())){
                System.out.println("更新用户选择的词书成功！");
            }
            else System.out.println("更新选择的词书失败！");
            cardLayout.show(mainPanel, "Home");
        }
    }



}