package assignments.assignment1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class NotaGenerator {
    // Nota
    private static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        // program akan terus mengulang hingga exit(0)
        while (true) {
            printMenu();
        }
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        // main menu
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
        System.out.printf("Pilihan : ");
        String pilihan = input.next();
        input.nextLine();
        Boolean valid = true;
        System.out.println("================================");
        if (pilihan.equals("0")) {
            // menghentikan program
            System.out.println("Terima kasih telah menggunakan NotaGenerator!");
            System.exit(0);
        } else if (pilihan.equals("1")) {
            // generate nota
            System.out.println("Masukkan nama Anda:");
            String nama = input.nextLine();
            System.out.println("Masukkan nomor handphone Anda:");
			String nomorHP = input.nextLine();
            while (!isDigit(nomorHP)) {
				// memeriksa apakah nomor HP hanya terdiri dari angka
				System.out.println("Nomor hp hanya menerima digit");
				nomorHP = input.nextLine();
            }
            System.out.printf("ID Anda : %s\n", generateId(nama, nomorHP));
        } else if (pilihan.equals("2")) {
            System.out.println("Masukkan nama Anda:");
            String nama = input.nextLine();
            String nomorHP = "";
            System.out.println("Masukkan nomor handphone Anda:");
			nomorHP = input.nextLine();
            while (!isDigit(nomorHP)) {
				// memeriksa apakah nomor HP hanya terdiri dari angka
				System.out.println("Nomor hp hanya menerima digit");
				nomorHP = input.nextLine();
            }
            String id = generateId(nama, nomorHP);
            System.out.println("Masukkan tanggal terima:");
            String tanggalTerima = input.nextLine();
            String paket = "";
            valid = false;
            while (!valid) {
                // memeriksa apakah pengguna telah memilih paket yang valid
                System.out.println("Masukkan paket laundry:");
                paket = input.nextLine();
                String tmp = paket.toLowerCase();
                if (tmp.equals("express") || tmp.equals("fast") || tmp.equals("reguler")) {
                    valid = true;
                } else if (tmp.equals("?")) {
                    showPaket();
                } else {
                    System.out.printf("Paket %s tidak diketahui\n", paket);
                    System.out.printf("[ketik ? untuk mencari tahu jenis paket]\n");
                }
            }
            System.out.println("Masukkan berat cucian Anda [Kg]:");
            valid = false;
            int berat = 0;
            while (!valid) {
                // memeriksa apakah berat merupakan bilangan
                valid = true;
                String tmp = input.nextLine();
                for(int i = 0; i < tmp.length(); i++){
                    if(tmp.charAt(i)<'0' || '9'<tmp.charAt(i)){
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        valid = false;
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
            System.out.println("Nota Laundry");
            System.out.println(generateNota(id, paket, berat, tanggalTerima, false));
        } else {
            System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
        }
    }

    /**
     * Method untuk menampilkan paket.
     */
    public static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    
    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        // generate id menggunakan method checksum
        nama = nama.split(" ", 2)[0];
        String id = nama.toUpperCase() + "-" + nomorHP;
        id = id + "-" + checkSum(id);
        return id;
    }
    
    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */
    
    public static String generateNota(String id, String paket, int berat, String tanggalTerima, boolean isDiskon){
        int hargaPaketPerKg = 0, totalHarga, durasi=0;
        if (paket.toLowerCase().equals("express")) {
            hargaPaketPerKg = 12000;
            durasi = 1;
        } else if (paket.toLowerCase().equals("fast")) {
            hargaPaketPerKg = 10000;
            durasi = 2;
        } else if (paket.toLowerCase().equals("reguler")) {
            hargaPaketPerKg = 7000;
            durasi = 3;
        }
        // penentuan tanggal selesai menggunakan date dari Java
        String tanggalSelesai = tanggalTerima;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(tanggalSelesai));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, durasi);
        tanggalSelesai = sdf.format(c.getTime());
        totalHarga = berat * hargaPaketPerKg;
        String diskon = "";
        if (isDiskon) {
            diskon = String.format(" = %d (Discount member 50%%!!!)", totalHarga/2);
        }
        String ret = String.format("""
            ID    : %s
            Paket : %s
            Harga :
            %d kg x %d = %d %s
            Tanggal Terima  : %s
            Tanggal Selesai : %s""", 
            id, paket, berat, hargaPaketPerKg, totalHarga, diskon, tanggalTerima, tanggalSelesai);
        return ret;
    }

    public static String checkSum(String s) {
        // method untuk generate kode unik dari id
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if ('A' <= s.charAt(i) && s.charAt(i) <= 'Z') {
                sum += s.charAt(i) - 'A';
                sum++;
            } else if ('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                sum += s.charAt(i) - '0';
            } else {
                sum += 7;
            }
        }
        sum %= 100;
        String ret = Integer.toString(sum);
        while (ret.length() < 2) {
            ret = "0" + ret;
        }
        return ret;
      }

      public static boolean isDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || '9' < s.charAt(i)) {
                return false;
            }
        }
        return true;
      }
}