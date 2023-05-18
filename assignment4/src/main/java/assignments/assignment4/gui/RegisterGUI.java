package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = -1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // create name label
        nameLabel = new JLabel("Masukkan nama Anda:");
        gbc.gridy++;
        mainPanel.add(nameLabel, gbc);
        
        // create name text field
        nameTextField = new JTextField();
        gbc.gridy++;
        mainPanel.add(nameTextField, gbc);
        
        // create phone label
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        gbc.gridy++;
        mainPanel.add(phoneLabel, gbc);
        
        // create phone field
        phoneTextField = new JTextField();
        gbc.gridy++;
        mainPanel.add(phoneTextField, gbc);

        // create password label
        passwordLabel = new JLabel("Masukkan password Anda:");
        gbc.gridy++;
        mainPanel.add(passwordLabel, gbc);
        
        // create password field
        passwordField = new JPasswordField();
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);
        
        // create register button
        // gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        gbc.gridy++;
        mainPanel.add(registerButton, gbc);

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
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk mendaftarkan member pada sistem.
     * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        if (nameTextField.getText().equals("") || phoneTextField.getText().equals("") || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(mainPanel, "Semua field di atas wajib diisi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String strName = nameTextField.getText();
            String strPhone = phoneTextField.getText();
            String strPass = new String(passwordField.getPassword());
            Member newMember = loginManager.register(strName, strPhone, strPass);
            if (newMember == null) {
                JOptionPane.showMessageDialog(mainPanel, "Member sudah terdaftar!", "Duplicate Member", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(mainPanel, "Berhasil membuat user dengan ID " + newMember.getId() + "!", "Registration Successfull", JOptionPane.INFORMATION_MESSAGE);
            nameTextField.setText("");
            phoneTextField.setText("");
            passwordField.setText("");
            MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");
        }
    }
}
