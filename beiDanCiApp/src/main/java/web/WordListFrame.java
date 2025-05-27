package web;

import pojo.Word;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WordListFrame extends JPanel {
    private JTable wordTable;
    private JLabel titleLabel;

    public interface BackListener {
        void onBackRequested();
    }

    // 修改构造函数参数，接收包含单词、音标、中文的数据
    public WordListFrame(BackListener listener, String wordbookName, List<Word> words) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(250, 250, 250));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. 顶部标题（显示当前词书名称）
        titleLabel = new JLabel("词书: " + wordbookName, JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 2. 中间表格（显示单词、音标、中文）
        wordTable = createWordTable(words);
        JScrollPane scrollPane = new JScrollPane(wordTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // 3. 底部按钮（返回词书列表）
        JButton backButton = new JButton("返回词书列表");
        backButton.addActionListener(e -> listener.onBackRequested());

        // 美化按钮
        backButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // 创建单词表格
    private JTable createWordTable(List<Word> words) {
        // 列名
        String[] columns = {"英文单词", "音标", "中文释义"};

        // 准备表格数据
        Object[][] data = new Object[words.size()][3];
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            data[i][0] = word.getWord();
            data[i][1] = word.getPhonetic();
            data[i][2] = word.getMeaning();
        }

        // 创建不可编辑的表格模型
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        // 美化表格
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(28); // 设置行高
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        // 设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // 单词列
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // 音标列
        table.getColumnModel().getColumn(2).setPreferredWidth(250); // 中文列

        // 添加音标列的特殊渲染
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                // 设置音标专用字体
                label.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        return table;
    }
}