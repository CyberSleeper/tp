package assignments.assignment4.gui;

import assignments.assignment4.MainFrame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.toNextDay;
import static assignments.assignment3.nota.NotaManager.getDate;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        JPanel option = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // create title label
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // create login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToLogin();
            }
        });
        gbc.insets = new Insets(5, 0, 0, 0);
        option.add(loginButton, gbc);
        
        // create register button
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToRegister();
            }
        });
        gbc.gridy = 1;
        gbc.insets = new Insets(75, 0, 75, 0);
        option.add(registerButton, gbc);
        
        // create next day button
        toNextDayButton = new JButton("Next Day");
        toNextDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextDay();
            }
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        option.add(toNextDayButton, gbc);
        
        // create date label
        dateLabel = new JLabel(getDate(), JLabel.CENTER);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(option, BorderLayout.CENTER);
        mainPanel.add(dateLabel, BorderLayout.SOUTH);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay();
        dateLabel.setText(getDate());
        JOptionPane.showMessageDialog(mainPanel, "Kamu tidur hari ini... zzz...");
    }
}
