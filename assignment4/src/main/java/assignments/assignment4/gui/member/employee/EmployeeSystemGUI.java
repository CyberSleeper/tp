package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
            new JButton("It's nyuci time"),
            new JButton("Display List Nota")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        String messageInfo = "";
        Nota[] notaList = NotaManager.notaList;
        for (Nota nota:notaList) {
            messageInfo += nota.getNotaStatus() + "\n";
        }
        if(messageInfo.length() == 0){
            messageInfo = "Belum ada nota";
            JOptionPane.showMessageDialog(this, messageInfo, "List Nota", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, messageInfo, "List Nota", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        Nota[] notaList = NotaManager.notaList;
        if(notaList.length == 0){
            JOptionPane.showMessageDialog(this, "Tidak ada cucian", "Nyuci Results", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String messageInfo = "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!";
        JOptionPane.showMessageDialog(this, messageInfo, "Nyuci News", JOptionPane.INFORMATION_MESSAGE);
        messageInfo = "";
        for (Nota nota:notaList) {
            messageInfo = messageInfo + nota.kerjakan() + "\n";
        }
        JOptionPane.showMessageDialog(this, messageInfo, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
