package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Set;
import java.util.Objects;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static Nota[] notaList;
    private static HashMap<String, String> memberList = new HashMap<String, String>();

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
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!", nama, noHp);
        } else {
            memberList.put(newMember.getId(), nama);
            System.out.printf("Berhasil membuat member dengan ID %s!", newMember.getId());
        }
    }
    
    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukan ID member:");
        String id = input.nextLine();
        if(!memberList.containsKey(id)){
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", id);
            return;
        }
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
    }
    
    private static void handleListUser() {
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for(String i:memberList.keySet()) {
            System.out.printf("- %s : %s\n", i, memberList.get(i));
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
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

}
