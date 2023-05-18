package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setPreferredSize(new Dimension(700, 432));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = -1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // create id label
        idLabel = new JLabel("Masukkan ID Anda:");
        gbc.gridy++;
        mainPanel.add(idLabel, gbc);
        
        // create id text field
        idTextField = new JTextField();
        gbc.gridy++;
        mainPanel.add(idTextField, gbc);
        
        // create password label
        passwordLabel = new JLabel("Masukkan password Anda:");
        gbc.gridy++;
        mainPanel.add(passwordLabel, gbc);
        
        // create password field
        passwordField = new JPasswordField();
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);
        
        // create login button
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        gbc.gridy++;
        mainPanel.add(loginButton, gbc);
        
        // create back button
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        gbc.gridy++;
        mainPanel.add(backButton, gbc);
        add(mainPanel);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }
    
    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        String strId = idTextField.getText();
        String strPass = new String(passwordField.getPassword());
        if(MainFrame.getInstance().login(strId, strPass)){
            MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
            idTextField.setText("");
            passwordField.setText("");
        }else{
            JOptionPane.showMessageDialog(mainPanel, "ID atau password invalid!", "Login Gagal!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
