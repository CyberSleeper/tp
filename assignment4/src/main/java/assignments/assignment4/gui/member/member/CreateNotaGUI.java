package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import static assignments.assignment3.nota.NotaManager.getDate;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private String[] jenisPaket = {"Express", "Fast", "Reguler"};
    private JPanel mainPanel;
    private JPanel form;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // create paket label
        paketLabel = new JLabel("Paket Laundry:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(paketLabel, gbc);
        
        // create paket combobox
        paketComboBox = new JComboBox<>(jenisPaket);
        gbc.gridx = 1;
        gbc.gridy = 0;
        form.add(paketComboBox, gbc);

        // create show paket button
        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        form.add(showPaketButton, gbc);

        // create berat label
        beratLabel = new JLabel("Berat Cucian (Kg):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(beratLabel, gbc);

        // create berat text field
        beratTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        form.add(beratTextField, gbc);

        // create setrika checkbox
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / kg)");
        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(setrikaCheckBox, gbc);

        // create antar checkbox
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4kg pertama, kemudian 500 / kg)");
        gbc.gridx = 0;
        gbc.gridy = 3;
        form.add(antarCheckBox, gbc);

        // create "create nota" button
        createNotaButton = new JButton("Buat Nota");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        createNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNota();
            }
        });
        form.add(createNotaButton, gbc);
        
        // create back button
        backButton = new JButton("Kembali");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        form.add(backButton, gbc);
        
        mainPanel.add(form, BorderLayout.CENTER);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        int idxPaket = paketComboBox.getSelectedIndex();

        String paket = jenisPaket[idxPaket];
        Member loginMember = memberSystemGUI.getLoggedInMember();

        int berat = 0;
        // memeriksa apakah berat merupakan bilangan bulat positif
        boolean valid = true;
        String tmp = beratTextField.getText();
        for(int i = 0; i < tmp.length(); i++){
            if(tmp.charAt(i)<'0' || '9'<tmp.charAt(i)){
                valid = false;
                break;
            }
        }
        try {
            berat = Integer.parseInt(tmp);
        } catch (Exception e) {
            valid = false;
        }
        if(berat < 1){
            valid = false;
        }
        if(!valid){
            JOptionPane.showMessageDialog(mainPanel, "Harap masukkan berat cucian Anda dalam bentuk bilangan positif.", "Info", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
            return;
        }
        if (berat < 2) {
            JOptionPane.showMessageDialog(mainPanel, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", JOptionPane.INFORMATION_MESSAGE);
            berat = 2;
        }
        String tanggalMasuk = getDate();
        
        boolean setrika = setrikaCheckBox.isSelected();
        boolean antar = antarCheckBox.isSelected();

        Nota newNota = new Nota(loginMember, berat, paket, tanggalMasuk);
        if (setrika) {
            newNota.addService(new SetrikaService());
        }
        if (antar) {
            newNota.addService(new AntarService());
        }
        
        loginMember.addNota(newNota);
        JOptionPane.showMessageDialog(mainPanel, "Nota berhasil dibuat", "Success", JOptionPane.INFORMATION_MESSAGE);
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }
}
