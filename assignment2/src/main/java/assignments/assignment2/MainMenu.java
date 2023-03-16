package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;

import assignments.assignment1.NotaGenerator;

import java.util.Objects;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static HashMap<String, Member> memberList = new HashMap<String, Member>();

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        System.out.println("Masukan nama Anda:");
        String nama = input.nextLine();
        System.out.println("Masukan nomor handphone Anda:");
        String noHp = input.nextLine();
        while (!isDigit(noHp)) {
            System.out.println("Field nomor hp hanya menerima digit.");
            noHp = input.nextLine();
        }
        Member newMember = new Member(nama, noHp);
        if(memberList.containsKey(newMember.getId())) {
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, noHp);
        } else {
            memberList.put(newMember.getId(), newMember);
            System.out.printf("Berhasil membuat member dengan ID %s!\n", newMember.getId());
        }
    }
    
    private static void handleGenerateNota() {
        System.out.println("Masukan ID member:");
        String id = input.nextLine();
        if(!memberList.containsKey(id)){
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", id);
            return;
        }
        boolean valid = false;
        String paket = "";
        while (!valid) {
            // memeriksa apakah pengguna telah memilih paket yang valid
            System.out.println("Masukan paket laundry:");
            paket = input.nextLine();
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
            // memeriksa apakah berat merupakan bilangan
            valid = true;
            String tmp = input.nextLine();
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
        Member member = memberList.get(id);
        System.out.println("Berhasil menambahkan nota!");
        int idNota = Nota.getBanyakNota();
        System.out.printf("[ID Nota = %d]\n", idNota);
        String tanggalMasuk = fmt.format(cal.getTime());
        member.setBonusCounter(member.getBonusCounter() + 1);
        System.out.println(generateNota(id, paket, berat, tanggalMasuk, (member.getBonusCounter() == 0)));
        Nota newNota = new Nota(member, paket, berat, tanggalMasuk);
        System.out.printf("Status\t\t: %s\n", newNota.getStatus());
        notaList.add(newNota);
    }

    private static void handleListNota() {
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
        for(Nota i:notaList) {
            System.out.printf("- [%d] Status\t\t: %s\n", i.getIdNota(), i.getStatus());
        }
    }
    
    private static void handleListUser() {
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for(String i:memberList.keySet()) {
            System.out.printf("- %s : %s\n", i, memberList.get(i).getNama());
        }
    }
    
    private static void handleAmbilCucian() {
        System.out.println("Masukan ID nota yang akan diambil:");
        boolean valid = false;
        int id = 0;
        while (!valid) {
            // memeriksa apakah berat merupakan bilangan
            valid = true;
            String tmp = input.nextLine();
            for(int i = 0; i < tmp.length(); i++){
                if(tmp.charAt(i)<'0' || '9'<tmp.charAt(i)){
                    System.out.println("ID nota berbentuk angka!");
                    valid = false;
                    break;
                }
            }
            if(!valid) continue;
            try {
                id = Integer.parseInt(tmp);
            } catch (Exception e) {
                System.out.println("ID nota berbentuk angka!");
                valid = false;
            }
            if(!valid) continue;
        }
        Nota nota = cariNota(id);
        if (nota == null) {
            System.out.printf("Nota dengan ID %d tidak ditemukan!\n", id);
        } else if (nota.getIsReady() == false) {
            System.out.printf("Nota dengan ID %d gagal diambil!\n", id);
        } else {
            for (int i = 0; i < notaList.size(); i++){
                if(notaList.get(i).getIdNota() == id) {
                    notaList.remove(i);
                    break;
                }
            }
            System.out.printf("Nota dengan ID %d berhasil diambil!\n", id);
        }
    }

    private static void handleNextDay() {
        cal.add(Calendar.DATE, 1);
        System.out.printf("Dek Depe tidur hari ini... zzz...\n");
        for(int i = 0; i < notaList.size(); i++) {
            notaList.get(i).nextDay();
            if(notaList.get(i).getIsReady())
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", notaList.get(i).getIdNota());
        }
        System.out.printf("Selamat pagi dunia!\n");
        System.out.printf("Dek Depe: It's CuciCuci Time.\n");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    private static Nota cariNota(int cariId) {
        for (Nota i: notaList){
            if (i.getIdNota() == cariId) {
                return i;
            }
        }
        return null;
    }
}
