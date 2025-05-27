//package web;
//
//import org.example.DeepSeekClient;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//
//public class AskAIFrame extends JPanel {
//    private JTextArea displayArea; // 显示对话内容
//    private JTextField inputField; // 用户输入框
//    private JButton sendButton; // 发送按钮
//
//    public AskAIFrame() {
//        setLayout(new BorderLayout());
//
//        // 创建聊天显示区域
//        displayArea = new JTextArea();
//        displayArea.setEditable(false);
//        displayArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
//        displayArea.setBackground(new Color(240, 240, 240));
//        displayArea.setLineWrap(true);       // 启用自动换行
//        displayArea.setWrapStyleWord(true);  // 按单词边界换行，避免截断单词
//
//        JScrollPane scrollPane = new JScrollPane(displayArea);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 禁用横向滚动
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 启用纵向滚动
//
//        // 设置视口（Viewport）的首选大小，确保内容不会越界
//        scrollPane.setPreferredSize(new Dimension(400, 300)); // 你可以根据需要调整尺寸
//
//        add(scrollPane, BorderLayout.CENTER);
//        // 创建输入面板
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new BorderLayout());
//
//        inputField = new JTextField();
//        sendButton = new JButton("Send");
//
//        inputPanel.add(inputField, BorderLayout.CENTER);
//        inputPanel.add(sendButton, BorderLayout.EAST);
//
//        add(inputPanel, BorderLayout.SOUTH);
//
//        // 发送按钮事件处理
//        sendButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String userInput = inputField.getText();
//                if (!userInput.isEmpty()) {
//                    displayArea.append("用户: " + userInput + "\n\n");
//                    inputField.setText("");
//
//                    // 这里可以集成与AI的交互逻辑，模拟AI的响应
//                    String aiResponse = null;
//                    try {
//                        aiResponse = getAIResponse(userInput);
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                    displayArea.append("AI: " + aiResponse + "\n\n");
//                }
//            }
//        });
//    }
//
//    // 模拟AI的响应
//    private String getAIResponse(String userInput) throws IOException {
//        // 这里可以使用任何API来获得AI的响应
//        // 当前示例返回固定文本作为AI的答复
//
//        return new DeepSeekClient().sendMessageToAI(userInput);
//    }
//}
package web;

import org.example.DeepSeekClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AskAIFrame extends JPanel {
    private JTextArea displayArea;
    private JTextField inputField;
    private JButton sendButton;

    public AskAIFrame() {

        setLayout(new BorderLayout());




        // 聊天显示区域
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        displayArea.setBackground(new Color(250, 250, 250));
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // 输入面板
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        inputField = new JTextField();
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // 修改字体为支持中文的字体
        displayArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        sendButton = new JButton("➤");
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // 发送消息逻辑

        sendButton.addActionListener(e -> {
            String userInput = inputField.getText().trim();
            if (userInput.isEmpty()) return;

            appendMessage("用户", userInput);
            inputField.setText("");
            sendButton.setEnabled(false);
            inputField.setEnabled(false);
            displayArea.append("AI: 正在思考...\n\n");

            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws IOException {
                    return new DeepSeekClient().sendMessageToAI(userInput);
                }

                @Override
                protected void done() {
                    try {
                        String text = displayArea.getText();
                        displayArea.setText(text.replace("AI: 正在思考...\n\n", ""));
                        String aiResponse = get();
                        appendMessage("AI", aiResponse);
                    } catch (Exception ex) {
                        appendMessage("系统", "获取 AI 回复失败：" + ex.getMessage());
                    } finally {
                        sendButton.setEnabled(true);
                        inputField.setEnabled(true);
                    }
                }
            }.execute();
        });
        inputField.addActionListener(e -> sendButton.doClick()); // 直接调用 sendButton 的点击事件
    }

    private void appendMessage(String sender, String message) {
        displayArea.append(sender + ": " + message + "\n\n");
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }
}