package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JPanel form;
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
        mainPanel = new JPanel(new BorderLayout());
        form = new JPanel(new GridBagLayout());

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
        form.add(idLabel, gbc);
        
        // create id text field
        idTextField = new JTextField();
        gbc.gridy++;
        form.add(idTextField, gbc);
        
        // create password label
        passwordLabel = new JLabel("Masukkan password Anda:");
        gbc.gridy++;
        form.add(passwordLabel, gbc);
        
        // create password field
        passwordField = new JPasswordField();
        gbc.gridy++;
        form.add(passwordField, gbc);
        
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
        form.add(loginButton, gbc);
        
        // create back button
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        gbc.gridy++;
        form.add(backButton, gbc);
        mainPanel.add(form, BorderLayout.CENTER);
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
        SystemCLI systemCLI =  loginManager.getSystem(strId);
        if(systemCLI == null){
            JOptionPane.showMessageDialog(mainPanel, "ID atau password invalid!", "Login Gagal!", JOptionPane.ERROR_MESSAGE);
        }else{
            Member authMember = systemCLI.authUser(strId, strPass);
            if(authMember == null){
                JOptionPane.showMessageDialog(mainPanel, "ID atau password invalid!", "Login Gagal!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MainFrame.getInstance().login(strId, strPass);
            if(authMember.getClass().getSimpleName().equals("Employee")){
                MainFrame.getInstance().navigateTo(EmployeeSystemGUI.KEY);
            }else{
                MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
            }
            idTextField.setText("");
            passwordField.setText("");
        }
    }
}
