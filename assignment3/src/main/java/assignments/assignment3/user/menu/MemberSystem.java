package assignments.assignment3.user.menu;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment1.NotaGenerator;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MemberSystem extends SystemCLI {

    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        if (choice == 1) {
            boolean valid = false;
            String paket = "";
            while (!valid) {
                // memeriksa apakah pengguna telah memilih paket yang valid
                System.out.println("Masukan paket laundry:");
                NotaGenerator.showPaket();
                paket = in.nextLine();
                String tmp = paket.toLowerCase();
                if (tmp.equals("express") || tmp.equals("fast") || tmp.equals("reguler")) {
                    valid = true;
                } else if (tmp.equals("?")) {
                    NotaGenerator.showPaket();
                } else {
                    System.out.printf("Paket %s tidak diketahui\n", paket);
                    System.out.printf("[ketik ? untuk mencari tahu jenis paket]\n");
                }
            }
            System.out.println("Masukkan berat cucian Anda [Kg]:");
            valid = false;
            int berat = 0;
            while (!valid) {
                // memeriksa apakah berat merupakan bilangan bulat positif
                valid = true;
                String tmp = in.nextLine();
                for(int i = 0; i < tmp.length(); i++){
                    if(tmp.charAt(i)<'0' || '9'<tmp.charAt(i)){
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        valid = false;
                        break;
                    }
                }
                if(!valid) continue;
                try {
                    berat = Integer.parseInt(tmp);
                } catch (Exception e) {
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    valid = false;
                }
                if(!valid) continue;
                if(berat < 1){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    valid = false;
                }
            }
            if (berat < 2) {
                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                berat = 2;
            }
            System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
            System.out.println("Hanya tambah 1000 / kg :0");
            System.out.print("[Ketik x untuk tidak mau]: ");
            boolean setrika = !in.nextLine().equals("x");
            System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
            System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
            System.out.print("[Ketik x untuk tidak mau]: ");
            boolean antar = !in.nextLine().equals("x");
            String tanggalMasuk = fmt.format(cal.getTime());

            Nota newNota = new Nota(loginMember, berat, paket, tanggalMasuk);
            if (setrika) {
                newNota.addService(new SetrikaService());
            }
            if (antar) {
                newNota.addService(new AntarService());
            }

            loginMember.addNota(newNota);

            System.out.println("Nota berhasil dibuat!\n");
        } else if (choice == 2) {
            for (Nota nota:loginMember.getNotaList()) {
                System.out.println(nota.toString());
            }
        } else if (choice == 3) {
            return true;
        }
        return false;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] newMemberList = new Member[memberList.length + 1];
        for (int i = 0; i < memberList.length; i++) {
            newMemberList[i] = memberList[i];
        }
        newMemberList[memberList.length] = member;
        memberList = newMemberList;
    }
}