package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    private int idNota;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private static int banyakNota = 0;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        idNota = banyakNota;
        this.paket = paket;
        this.member = member;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        if (paket.toLowerCase().equals("express")) {
            sisaHariPengerjaan = 1;
        } else if (paket.toLowerCase().equals("fast")) {
            sisaHariPengerjaan = 2;
        } else if (paket.toLowerCase().equals("reguler")) {
            sisaHariPengerjaan = 3;
        }
        isReady = false;
        banyakNota++;
    }

    public static int getBanyakNota(){
        return banyakNota;
    }
    
    public boolean getIsReady() {
        return isReady;
    }

    public String getStatus() {
        if (isReady) {
            return "Sudah dapat diambil!";
        } else {
            return "Belum bisa diambil :(";
        }
    }

    public int getIdNota() {
        return idNota;
    }

    public void nextDay() {
        sisaHariPengerjaan = Math.max(0, sisaHariPengerjaan - 1);
        if (sisaHariPengerjaan == 0) {
            isReady = true;
        }
    }
}
