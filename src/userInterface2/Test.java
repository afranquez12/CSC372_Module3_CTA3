package userInterface2;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem dateTimeItem, saveItem, changeColorItem, exitItem;
    private JButton toggleTextAreaVisibilityButton;
    private boolean textAreaVisible = false; // Initial state of the text area visibility

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Test frame = new Test();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Test() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Initialize the text area but do not add it initially
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(200, 50)); // Smaller, controlled size

        // Toggle button to show/hide the text area
        toggleTextAreaVisibilityButton = new JButton("Toggle Text Area");
        toggleTextAreaVisibilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTextAreaVisibility();
            }
        });
        contentPane.add(toggleTextAreaVisibilityButton, BorderLayout.NORTH);

        // Menu bar setup
        setupMenuBar();

        // Setup menu actions
        setupMenuActions();
    }

    private void toggleTextAreaVisibility() {
        if (textAreaVisible) {
            contentPane.remove(scrollPane);
        } else {
            contentPane.add(scrollPane, BorderLayout.CENTER);
        }
        textAreaVisible = !textAreaVisible;
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        menuBar.add(menu);
        setJMenuBar(menuBar);

        dateTimeItem = new JMenuItem("Show Date and Time");
        saveItem = new JMenuItem("Save to File");
        changeColorItem = new JMenuItem("Change Background Color");
        exitItem = new JMenuItem("Exit");

        menu.add(dateTimeItem);
        menu.add(saveItem);
        menu.add(changeColorItem);
        menu.add(exitItem);
    }

    private void setupMenuActions() {
        dateTimeItem.addActionListener(e -> showDateTime());
        saveItem.addActionListener(e -> saveToFile());
        changeColorItem.addActionListener(e -> changeBackgroundColor());
        exitItem.addActionListener(e -> System.exit(0));
    }

    private void showDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        textArea.append("Current Date and Time: " + now.format(formatter) + "\n");
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(textArea.getText());
            writer.write("\n");
            JOptionPane.showMessageDialog(this, "Content saved to log.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving to file");
        }
    }

    private void changeBackgroundColor() {
        Random random = new Random();
        float hue = 0.05f + random.nextFloat() * 0.05f; // Random hue close to orange
        Color randomOrange = Color.getHSBColor(hue, 1.0f, 1.0f);
        contentPane.setBackground(randomOrange);
        JOptionPane.showMessageDialog(this, "Background color changed to a random hue of orange");
    }
}

